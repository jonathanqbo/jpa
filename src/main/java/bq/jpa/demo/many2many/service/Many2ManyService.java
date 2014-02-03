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

package bq.jpa.demo.many2many.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.many2many.domain.Employee;
import bq.jpa.demo.many2many.domain.Project;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 31, 2014 11:19:35 AM
 *
 */
@Service
public class Many2ManyService {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void createEmployeeWithProjects(){
		Employee employee1 = new Employee();
		employee1.setName("employee1");
		
		Employee employee2 = new Employee();
		employee2.setName("employee2");
		
		Project project1 = new Project();
		project1.setName("project1");
		
		Project project2 = new Project();
		project2.setName("project2");
		
		Collection<Employee> employees = new ArrayList<Employee>();
		employees.add(employee1);
		employees.add(employee2);
		
		Collection<Project> projects = new ArrayList<Project>();
		projects.add(project1);
		projects.add(project2);
		
		project1.setEmployees(employees);
		project2.setEmployees(employees);
		
		employee1.setProjects(projects);
		employee2.setProjects(projects);
		
		// the sequence(project first or employee first) doesn't matter
		em.persist(project1);
		em.persist(employee1);

		em.persist(employee2);
		em.persist(project2);
		
	}
	
	/**
	 * default is lazy-load, so we cannot get project information from the result employees
	 * return from this method, because it's out of the session after the method is over.
	 * to solve this problem, set fetch=FetchType.EAGER.
	 * @return
	 */
	@Transactional
	public List<Employee> findEmployees(){
		TypedQuery<Employee> query = em.createQuery("FROM jpa_many2many_employee", Employee.class);
		return query.getResultList();
	}
	
	@Transactional
	public List<Project> findProjects(){
		TypedQuery<Project> query = em.createQuery("from jpa_many2many_project", Project.class);
		return query.getResultList();
	}
	
}
