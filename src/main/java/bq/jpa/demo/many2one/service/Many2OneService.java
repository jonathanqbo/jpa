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

package bq.jpa.demo.many2one.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.many2one.domain.Department;
import bq.jpa.demo.many2one.domain.Employee;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 30, 2014 3:54:06 PM
 *
 */
@Service
public class Many2OneService {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void createEmployeeAndDepartment(){
		Department department = new Department();
		department.setName("department1");
		
		Employee employee = new Employee();
		employee.setName("employee1");
		employee.setDepartment(department);
		
		em.persist(department);
		em.persist(employee);
	}
}
