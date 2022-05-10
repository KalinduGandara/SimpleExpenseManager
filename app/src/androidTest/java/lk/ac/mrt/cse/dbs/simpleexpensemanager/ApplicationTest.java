/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;
import android.test.mock.MockContext;

import androidx.test.core.app.ApplicationProvider;

import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest {
    PersistentExpenseManager expenseManager;

    @Before
    public void createExpenseManager(){
        Context context = ApplicationProvider.getApplicationContext();
        expenseManager = new PersistentExpenseManager(context);
    }

    @Test
    public void testApp() {
        expenseManager.addAccount("12345A","TestBank","TestHolder",1000);
//        Account account = expenseManager.getAccountsDAO().getAccount("12345A");
        List<String> accountNos = expenseManager.getAccountNumbersList();
        assertTrue(accountNos.contains("12345A"));
    }
}