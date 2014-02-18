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

package bq.jpa.demo.lock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bq.jpa.demo.lock.service.LockService;
import bq.jpa.demo.lock.domain.Employee;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 16, 2014 9:17:26 PM
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/application.xml")
public class LockTester {

	@Autowired
	private LockService service;
	
//	@Test
	public void test(){
		List<Employee> employees = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			Employee employee = new Employee();
			employee.setName("employee_" + i);
			employee.setSalary(100f * i);
			employees.add(employee);
		}
		service.doCreate(employees);
	}
	
//	@Test
	public void test2(){
		System.out.println("---- modify salary while do statistic ----");

		Thread thread1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				service.doStatisticSalary();
			}
		});
		Thread thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				service.doUpdateSalary();
			}
		});

		thread1.setName("dostatistic");
		thread2.setName("doupdatesalary");
		try {
			thread2.start();
			Thread.sleep(1000);
			thread1.start();
		} catch (InterruptedException e1) {	}
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) { }
	}
	
//	@Test
	public void test3(){
		System.out.println("---- modify salary while do statistic ----");

		Thread thread1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				service.doModify1();
			}
		});
		Thread thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				service.doModify2();
			}
		});

		thread1.setName("modifythread1");
		thread2.setName("modifythread2");
		try {
			thread2.start();
			Thread.sleep(1000);
			thread1.start();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test4(){
		test();
		System.out.println("---- do statistic during writing ----");

		Thread thread1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				service.doReadWhileModify();
			}
		});
		Thread thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				service.doModify();
			}
		});

		thread1.setName("readthread");
		thread2.setName("writethread");
		thread1.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {	}
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
