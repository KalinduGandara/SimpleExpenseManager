package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.DataBaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO implements AccountDAO {
    private DataBaseHelper dataBaseHelper;


    public PersistentAccountDAO(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> numbersList = new ArrayList<>();
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT accountNo FROM Account", null);
        if (cursor.moveToFirst())
            do {
                numbersList.add(cursor.getString(0));
            }while (cursor.moveToNext());
        cursor.close();
        return numbersList;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> accountList = new ArrayList<>();
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Account", null);
        if (cursor.moveToFirst())
            do {
                accountList.add(new Account(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getDouble(3)));
            }while (cursor.moveToNext());
        cursor.close();
        return accountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Account WHERE accountNo=?", new String[]{accountNo});
//        Cursor cursor = db.rawQuery("SELECT * FROM Account", null);
        if (cursor.moveToFirst()) {
            Account account = new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3));
            cursor.close();
            return account;
        }
        cursor.close();
        throw new InvalidAccountException("Account " + accountNo + " is invalid.");
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("accountNo",account.getAccountNo());
        cv.put("bankName",account.getBankName());
        cv.put("accountHolderName",account.getAccountHolderName());
        cv.put("balance",account.getBalance());
        long r = db.insert("Account", null, cv);
        if (r == -1)
            System.out.println("error");

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        int r = db.delete("Account","accountNo=?",new String[]{accountNo});
        if (r == -1)
            throw new InvalidAccountException("Account " + accountNo + " is invalid.");
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Account account = getAccount(accountNo);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("accountNo",accountNo);
        if (expenseType == ExpenseType.EXPENSE)
            cv.put("balance",account.getBalance()-amount);
        else
            cv.put("balance",account.getBalance()+amount);
        int r = db.update("Account", cv, "accountNo=?", new String[]{accountNo});
        if (r == -1)
            System.out.println("error");

    }
}
