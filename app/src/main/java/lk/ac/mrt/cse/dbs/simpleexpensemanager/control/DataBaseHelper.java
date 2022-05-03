package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(@Nullable Context context) {
        super(context, "ExpenseManager.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Account (accountNo TEXT NOT NULL, bankName TEXT NOT NULL, accountHolderName TEXT NOT NULL, balance NUMERIC, PRIMARY KEY(accountNo));");
        sqLiteDatabase.execSQL("CREATE TABLE Transaction_Table (accountNo TEXT NOT NULL, expenseType INTEGER NOT NULL, amount NUMERIC NOT NULL, date TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
