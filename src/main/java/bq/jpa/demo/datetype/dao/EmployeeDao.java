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

package bq.jpa.demo.datetype.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import bq.jpa.demo.datetype.domain.Employee;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 28, 2014 4:01:55 PM
 *
 */
@Repository
public class EmployeeDao {

	@PersistenceContext
	private EntityManager em;
	
	public Employee find(int employeeId){
		return em.find(Employee.class, employeeId);
	}

	public void add(Employee employee){
		em.persist(employee);
	}
	
	public void removeEmployee(int employeeId){
		Employee employee = find(employeeId);
		em.remove(employee);
	}
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}
