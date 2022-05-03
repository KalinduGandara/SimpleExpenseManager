package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;

public class PersistentExpenseManager extends ExpenseManager{
    private DataBaseHelper dataBaseHelper;
    public PersistentExpenseManager(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
        setup();
    }

    @Override
    public void setup() {
        AccountDAO accountDAO = new PersistentAccountDAO(dataBaseHelper);
        TransactionDAO transactionDAO = new PersistentTransactionDAO(dataBaseHelper);
        setAccountsDAO(accountDAO);
        setTransactionsDAO(transactionDAO);
    }
}
