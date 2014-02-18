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

package bq.jpa.demo.lock.service;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.lock.domain.Employee;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 16, 2014 9:14:39 PM
 *
 */
@Service
public class LockService {

	@PersistenceContext
	private EntityManager em;
	
	private Lock lock = null;
	private boolean isStatisticStarted = false;
	private Condition readCondition = null;
	private Condition writeCondition = null;
	private Condition write1Condition = null;
	private Condition write2Condition = null;
	
	public LockService(){
		this.lock = new ReentrantLock();
		readCondition = lock.newCondition();
		writeCondition = lock.newCondition();
		write1Condition = lock.newCondition();
		write2Condition = lock.newCondition();
	}
	
	@Transactional
	public void doCreate(List<Employee> employees){
		for(Employee employee : employees){
			em.persist(employee);
		}
	}
	
	/**
	 * Thread1: currently modify employee will lead to OptimisticLockException.
	 * Do not need lock it explicitly.
	 */
	@Transactional
	public void doModify1(){
		
		Employee employee = null;
		try{
			lock.lock();
			
			employee = em.find(Employee.class, 1);
			System.out.println(" ### thread 1 query employee");
			write2Condition.signal();
			write1Condition.await();
			Thread.sleep(1000);
			
			employee.setName("new1_employee");
			employee.setSalary(employee.getSalary()+33);
			System.out.println(" ### thread 1 modify employee");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (OptimisticLockException lockException){
			em.persist(employee);
		} finally{
			lock.unlock();
		}
	}
	
	/**
	 * Thread2: currently modify employee will lead to OptimisticLockException.
	 * Do not need lock it explicitly
	 * 
	 */
	@Transactional
	public void doModify2(){
		
		try{
			lock.lock();
			
			write1Condition.signal();
			write2Condition.await();
			Employee employee = em.find(Employee.class, 1);
			System.out.println(" ### thread 2 query employee");
			
			employee.setName("new2_employee");
			employee.setSalary(employee.getSalary()+33);
			write1Condition.signal();
			System.out.println(" ### thread 2 modify employee");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
	}
	
	/**
	 * Statistic
	 */
	@Transactional
	public void doStatisticSalary(){
		try{
			lock.lock();
	
			TypedQuery<Employee> query = em.createQuery("SELECT e FROM jpa_lock_employee e", Employee.class);
			List<Employee> results = query.getResultList();
			System.out.println("[thread-reading] query employees");
			
			for(Employee employee : results){
				em.lock(employee, LockModeType.OPTIMISTIC);
				em.flush();
			}
			System.out.println("[thread-reading] lock employees");
			writeCondition.signal();
			readCondition.await();
			Thread.sleep(1000);
			
			float totalSalary = 0f;
			for(Employee employee : results){
				totalSalary += employee.getSalary();
			}
			
			System.out.println("[thread-reading] total salary is :" + totalSalary);
		} catch (Exception e) {
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
			// let read thread run first, run after employees has been locked in reading thread
			writeCondition.await();
			
			TypedQuery<Employee> query = em.createQuery("SELECT e FROM jpa_lock_employee e",Employee.class);
			List<Employee> results = query.getResultList();
			for(Employee employee : results){
				employee.setSalary(employee.getSalary() + 33);
			}
			// let read thread continue
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			readCondition.signal();
			System.out.println("[Thread writing] modify employee salary!");
			lock.unlock();
		}
	}
	
	/**
	 * Statistic
	 */
	@Transactional
	public void doReadWhileModify(){
		try{
			lock.lock();
			readCondition.await();
	
			TypedQuery<Employee> query = em.createQuery("SELECT e FROM jpa_lock_employee e", Employee.class);
			List<Employee> results = query.getResultList();
			System.out.println("[thread-reading] query employees");
			writeCondition.signal();
			readCondition.await();
			
			float totalSalary = 0f;
			for(Employee employee : results){
				totalSalary += employee.getSalary();
			}
			
			System.out.println("[thread-reading] total salary is :" + totalSalary);
			writeCondition.signal();
		} catch (Exception e) {
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
	public void doModify(){
		try{
			lock.lock();
			
			TypedQuery<Employee> query = em.createQuery("SELECT e FROM jpa_lock_employee e",Employee.class);
			List<Employee> results = query.getResultList();
			
			for(Employee employee : results){
				em.lock(employee, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				employee.setSalary(employee.getSalary() + 33);
			}
			System.out.println("[Thread-writing] lock employee");
			
			// let read thread continue
			readCondition.signal();
			writeCondition.await();
			
			System.out.println("[Thread-writing] modify employee salary!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}
	
}
