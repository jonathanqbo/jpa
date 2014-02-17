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

package bq.jpa.demo.basic2;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bq.jpa.demo.basic1.domain.Account;
import bq.jpa.demo.basic2.service.AccountService;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 27, 2014 5:23:50 PM
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/application.xml")
public class Basic2Tester {

	@Resource(name="jpaaccountservice")
	private AccountService accountService;
	
	@Test
	public void testAccountService(){
		// because AccountServiceImpl implements AccountService interface
		// so return proxy instance, so return instance cannot be casted to AccountServiceImpl
		// ERROR: AccountServiceImpl accountService = context.getBean("jpaaccountservice",AccountServiceImpl.class);
		// RIGHT:AccountService accountService = context.getBean("jpaaccountservice",AccountService.class);
		
		//
		Account newAccount = accountService.createNewAccount();
		System.out.println(newAccount.getAccountID() + "/" + newAccount.getAccountBalance());
		
		//
		newAccount = accountService.createNewAccountInBeanTransaction();
		System.out.println(newAccount.getAccountID() + "/" + newAccount.getAccountBalance());
	}
	
}
