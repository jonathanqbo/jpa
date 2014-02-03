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

package bq.jpa.demo.One2OneBi.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.One2OneBi.domain.Employee;
import bq.jpa.demo.One2OneBi.domain.ParkSpace;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 30, 2014 4:13:40 PM
 *
 */
@Service
public class One2OneBiService {

	@PersistenceContext
	private EntityManager em;
	
	/**
	 * two sql, ok!
	 */
	@Transactional
	public void createEmployeeWithParkspace(){
		ParkSpace parkspace = new ParkSpace();
		parkspace.setName("parkspace1");
		
		Employee employee = new Employee();
		employee.setName("employee1");
		employee.setParkspace(parkspace);
		
		em.persist(parkspace);
		em.persist(employee);
	}
	
	/**
	 * two sql
	 * error! the employee's pk_parkspace will be null!
	 */
	@Transactional
	public void createParkspaceOfEmployee(){
		Employee employee = new Employee();
		employee.setName("employee2");
		
		ParkSpace parkspace = new ParkSpace();
		parkspace.setName("parkspace2");
		parkspace.setEmployee(employee);
		
		em.persist(parkspace);
		em.persist(employee);
	}
	
	/**
	 * two sql
	 * error!, the employee's pk_parkspace will be null!
	 */
	@Transactional
	public void createParkspaceOfEmployee2(){
		Employee employee = new Employee();
		employee.setName("employee3");
		
		ParkSpace parkspace = new ParkSpace();
		parkspace.setName("parkspace3");
		parkspace.setEmployee(employee);
		
		em.persist(employee);
		em.persist(parkspace);
	}
	
	/**
	 * ok!
	 */
	@Transactional
	public void createParkspaceOfEmployee3(){
		Employee employee = new Employee();
		employee.setName("employee4");
		
		ParkSpace parkspace = new ParkSpace();
		parkspace.setName("parkspace4");
		parkspace.setEmployee(employee);
		
		// must!
		employee.setParkspace(parkspace);
		
		// doesn't matter
		em.persist(parkspace);
		em.persist(employee);
	}
	
	/**
	 * ok!
	 */
	@Transactional
	public void createParkspaceOfEmployee5(){
		Employee employee = new Employee();
		employee.setName("employee5");
		
		ParkSpace parkspace = new ParkSpace();
		parkspace.setName("parkspace5");
		parkspace.setEmployee(employee);
		
		// must!
		employee.setParkspace(parkspace);
		
		// doesn't matter
		em.persist(employee);
		em.persist(parkspace);
	}
	
}
	
