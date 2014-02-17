/*
Copyright (c) 2014 (Jonathan Q. Bo) 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package bq.jpa.demo.basic1;

import org.junit.Test;

import bq.jpa.demo.basic1.dao.AccountDao;
import bq.jpa.demo.basic1.dao.AccountDaoImpl;
import bq.jpa.demo.basic1.domain.Account;
import bq.jpa.demo.basic1.service.AccountService;
import bq.jpa.demo.basic1.service.AccountServiceImpl;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 26, 2014 5:18:59 PM
 *
 */
public class Basic1Tester {

	@Test
	public void testAccountService(){
		AccountDao accountDao = new AccountDaoImpl();

		AccountService accountService = new AccountServiceImpl();
		((AccountServiceImpl)accountService).setAccountDao(accountDao);
		Account newAccount = accountService.createNewAccount();
		
		System.out.println(newAccount.getAccountID() + "/" + newAccount.getAccountBalance());
	}
	
}
