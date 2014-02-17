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

package bq.jpa.demo.version.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.query.domain.ResultViewer;
import bq.jpa.demo.version.domain.Address;
import bq.jpa.demo.version.domain.Employee;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 16, 2014 4:35:14 PM
 *
 */
@Service
public class VersionService {

	@PersistenceContext
	private EntityManager em;
	
	private Lock lock = null;
	private boolean isStatisticStarted = false;
	private Condition dostatisticThread = null;
	private Condition updateSalaryThread = null;
	
	public VersionService(){
		this.lock = new ReentrantLock();
		dostatisticThread = lock.newCondition();
		updateSalaryThread = lock.newCondition();
	}
	
	@Transactional
	public void doCreate(){
		List<Employee> employees = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			Employee employee = new Employee();
			employee.setName("employee_" + i);
			employee.setSalary(100f * i);
			em.persist(employee);
			employees.add(employee);
		}
		
		List<Address> addresses = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			Address address = new Address();
			address.setState("NY");
			address.setCity("New York");
			address.setStreet("street_" + i);
			em.persist(address);
			addresses.add(address);
		}
		
		for(int i = 0; i < 10; i++){
			addresses.get(i).setEmployee(employees.get(i));
		}
	}
	
	/**
	 * can change version automatically
	 */
	@Transactional
	public void doUpdate(){
		TypedQuery<Employee> query = em.createQuery("SELECT e FROM jpa_version_employee e", Employee.class);
		List<Employee> results = query.getResultList();
		for(Employee e : results)
			e.setName("new_employee");
	}
	
	/**
	 * batch way by query language cannot change version
	 */
	@Transactional
	public void doUpdateByQL(){
		Query update = em.createQuery("UPDATE jpa_version_employee e SET e.name= CONCAT('new',e.name)");
		update.executeUpdate();
	}
	
	/**
	 * batch way need change version by itself
	 */
	@Transactional
	public void doUpdateVersionByQL(){
		Query update = em.createQuery("UPDATE jpa_version_employee e SET e.name= CONCAT('new',e.name), e.version=e.version+1");
		update.executeUpdate();
	}
	
	/**
	 * modify related address will not change employee's version automatically
	 */
	@Transactional
	public void doUpdateAddress(){
		TypedQuery<Address> query = em.createQuery("SELECT a FROM jpa_version_address a", Address.class);
		List<Address> results = query.getResultList();
		for(int i = 0; i < results.size(); i++){
			Address address = results.get(i);
			address.setState("MI");
			address.setCity("Lansing");
		}
	}
	
	/**
	 * Statistic
	 */
	@Transactional
	public void doStatisticSalary(){
		try{
			lock.lock();
			isStatisticStarted = true;
	
			TypedQuery<Employee> query = em.createQuery("SELECT e FROM jpa_version_employee e", Employee.class);
			List<Employee> results = query.getResultList();
			
			float totalSalary = 0f;
			int i = 0;
			for(Employee employee : results){
				
				// In the process of statistic, modify salary
				if(i == results.size()/2){
					dostatisticThread.await();
					updateSalaryThread.signal();
				}
				
				
				totalSalary += employee.getSalary();
				i++;

				System.out.println("do total : " + employee.getName() + " | " + employee.getSalary());
				System.out.println("now total : " + totalSalary);
			}
			
			System.out.println("total salary is :" + totalSalary);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}
	
	/**
	 * modify employees salary
	 */
	@Transactional
	public void doUpdateSalary(){
		try{
			lock.lock();
			if(!isStatisticStarted){
				updateSalaryThread.await();
				dostatisticThread.signal();
			}
			
			Query update = em.createQuery("UPDATE jpa_version_employee e SET e.salary=e.salary+100");
			update.executeUpdate();
			
			System.out.println("modify employee salary!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			dostatisticThread.signal();
			lock.unlock();
		}
	}
	
	/**
	 * modify salary while do statistic, lead to error statistic result.
	 * resolve this problem: add lock
	 */
	public void doCurrentReadAndWrite(){
		Thread thread1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				doStatisticSalary();
			}
		});
		Thread thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				doUpdateSalary();
			}
		});

		try {
			thread2.start();
			Thread.sleep(1000);
			thread1.start();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			thread1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		threadpool.submit(new Runnable() {
//			
//			@Override
//			public void run() {
//				doStatisticSalary();
//			}
//		});
//		threadpool.submit(new Runnable() {
//			
//			@Override
//			public void run() {
//				doUpdateSalary();
//			}
//		});
		
	}
	
	@Transactional
	public void showResult(){
		TypedQuery<Employee> query = em.createQuery("SELECT e FROM jpa_version_employee e", Employee.class);
		List<Employee> results = query.getResultList();
		ResultViewer.showResult(results, "show all employees");
	}
	
}
