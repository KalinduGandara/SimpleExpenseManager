package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.DataBaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO {
    private DataBaseHelper dataBaseHelper;

    public PersistentTransactionDAO(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("accountNo",accountNo);
        cv.put("expenseType",expenseType == ExpenseType.EXPENSE ? 0:1);
        cv.put("amount",amount);
        cv.put("date",date.toString());
        long r = db.insert("Transaction_Table", null, cv);
        if (r == -1)
            System.out.println("error");
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Transaction_Table", null);
        if (cursor.moveToFirst())
            do {
                transactionList.add(new Transaction(new Date(cursor.getString(3)),
                        cursor.getString(0),
                        cursor.getInt(1) == 0?ExpenseType.EXPENSE:ExpenseType.INCOME,
                        cursor.getDouble(2)));
            }while (cursor.moveToNext());
        cursor.close();
        return transactionList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Transaction_Table LIMIT ?", new String[]{Integer.toString(limit)});
        if (cursor.moveToFirst())
            do {
                transactionList.add(new Transaction(new Date(cursor.getString(3)),
                        cursor.getString(0),
                        cursor.getInt(1) == 0?ExpenseType.EXPENSE:ExpenseType.INCOME,
                        cursor.getDouble(2)));
            }while (cursor.moveToNext());
        cursor.close();
        return transactionList;
    }
}
