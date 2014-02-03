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

package bq.jpa.demo.map.service;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.map.domain.Department;
import bq.jpa.demo.map.domain.Employee;
import bq.jpa.demo.map.domain.PhoneType;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 31, 2014 10:07:10 PM
 *
 */
@Service
public class MapService {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void createEmploy(){
		Employee employee = new Employee();
		employee.setName("employee");
		employee.setCubicle("cube1");
		
		Map<String, String> phones = new HashMap<String, String>();
		phones.put("phone1", "3478888888");
		phones.put("phone2", "7188888888");
		employee.setPhones(phones);
		
		Map<PhoneType, String> phones2 = new HashMap<PhoneType, String>();
		phones2.put(PhoneType.Home, "9178888888");
		phones2.put(PhoneType.Mobile, "6288888888");
		employee.setPhones2(phones2);
		
		Department department = new Department();
		department.setName("department1");
		employee.setDepartment(department);
		
		em.persist(department);
		em.persist(employee);
	}
	
	public Department findDepartment(){
		TypedQuery<Department> query = em.createQuery("from jpa_map_department", Department.class);
		return query.getSingleResult();
	}
	
}
