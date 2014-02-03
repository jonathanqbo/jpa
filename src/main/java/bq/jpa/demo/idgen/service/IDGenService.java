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

package bq.jpa.demo.idgen.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.idgen.domain.Employee1;
import bq.jpa.demo.idgen.domain.Employee2;
import bq.jpa.demo.idgen.domain.Employee3;
import bq.jpa.demo.idgen.domain.Employee4;
import bq.jpa.demo.idgen.domain.Employee5;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 30, 2014 2:58:15 PM
 *
 */
@Service
public class IDGenService {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Employee1 createEmployee1(){
		Employee1 employee = new Employee1();
		employee.setName("employee1");
		em.persist(employee);
		return employee;
	}
	
	@Transactional
	public Employee2 createEmployee2(){
		Employee2 employee = new Employee2();
		employee.setName("employee2");
		em.persist(employee);
		return employee;
	}
	
	@Transactional
	public Employee3 createEmployee3(){
		Employee3 employee = new Employee3();
		employee.setName("employee3");
		em.persist(employee);
		return employee;
	}
	
	@Transactional
	public Employee4 createEmployee4(){
		Employee4 employee = new Employee4();
		employee.setName("employee4");
		em.persist(employee);
		return employee;
	}
	
	@Transactional
	public Employee5 createEmployee5(){
		Employee5 employee = new Employee5();
		employee.setName("employee5");
		em.persist(employee);
		return employee;
	}
}
