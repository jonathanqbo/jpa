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

package bq.jpa.demo.query.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.query.domain.Address;
import bq.jpa.demo.query.domain.Department;
import bq.jpa.demo.query.domain.DesignProject;
import bq.jpa.demo.query.domain.Employee;
import bq.jpa.demo.query.domain.Phone;
import bq.jpa.demo.query.domain.PhoneType;
import bq.jpa.demo.query.domain.Project;
import bq.jpa.demo.query.domain.QualityProject;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 6, 2014 3:06:36 PM
 *
 */
@Service
public class QueryService {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void prepareData(){
		int size = 10;
		List<Address> addresses = new ArrayList<>();
		for(int i = 0; i < size; i++){
			Address address = new Address();
			address.setCity("New York_" + i);
			address.setState("NY");
			address.setStreet("Main St_" + i);
			address.setCountry("country_" + i);
			
			em.persist(address);
			addresses.add(address);
		}
		for(int i = 0; i < size; i++){
			Address address = new Address();
			address.setCity("Lansing_" + i);
			address.setState("MI");
			address.setStreet("Main St_" + i);
			address.setCountry("country_" + i);
			
			em.persist(address);
			addresses.add(address);
		}
		
		List<Department> departments = new ArrayList<>();
		for(int i = 0; i < size; i++){
			Department department = new Department();
			department.setName("department_" + i);
			
			em.persist(department);
			departments.add(department);
		}
		
		List<Phone> phones = new ArrayList<>();
		for(int i = 0; i < size; i++){
			Phone phone = new Phone();
			phone.setName("phone_" + i);
			phone.setNumber("347-1234567" + i);
			if(i%3==0)
				phone.setType(PhoneType.Home);
			else if(i%3==1)
				phone.setType(PhoneType.Mobile);
			else
				phone.setType(PhoneType.Work);
			
			em.persist(phone);
			phones.add(phone);
		}
		
		List<Project> projects = new ArrayList<>();
		for(int i = 0; i < size; i++){
			DesignProject project = new DesignProject();
			project.setName("designproject_" + i);
			project.setArchname("architect_" + i);
			
			em.persist(project);
			projects.add(project);
		}
		for(int i = 0; i < size; i++){
			QualityProject project = new QualityProject();
			project.setName("qualityproject_" + i);
			project.setTestmngr("manager_" + i);
			
			em.persist(project);
			projects.add(project);
		}
		
		List<Employee> employees = new ArrayList<>();
		for(int i = 0; i < size; i++){
			Employee employee = new Employee();
			employee.setName("employee_" + i);
			employee.setSalary(100*i);
			
			em.persist(employee);
			employees.add(employee);
		}
		for(int i = size; i < size*10; i++){
			Employee employee = new Employee();
			employee.setName("employee_" + i);
			employee.setSalary(100*i);
			
			em.persist(employee);
		}
		
		// relations
		for(int i = 0; i < size; i++)
			phones.get(i).setEmployee(employees.get(i));
		
		for(int i = 0; i < size; i++)
			employees.get(i).setDepartment(departments.get(i));
		
		for(int i = 0; i < size; i++)
			employees.get(i).setAddress(addresses.get(i));
		
		for(int i = 0; i < size; i++)
			employees.get(i).setProjects(projects);
		
		for(int i = 0; i < (size -1); i++)
			employees.get(i).setManager(employees.get(i+1));
	}
	
	@Transactional
	public void doSelects(){
		String jpql = "SELECT e FROM jpa_query_employee e";
		showResult(jpql);
		
		jpql = "SELECT id, name, e.department.id FROM jpa_query_employee e";
		showResult(jpql);
		
		jpql = "SELECT e.department FROM jpa_query_employee e";
		showResult(jpql);
		
		// illegal to return collection type
		jpql = "SELECT e.phones FROM jpa_query_employee e";
		
		// embeddable can be return, but cannot be managed under persist context
		
		// return customized object
		jpql = "SELECT NEW bq.jpa.demo.query.domain.EmployeeBasicInfo(e.name) FROM jpa_query_employee e";
		showResult(jpql);
	}
	
	@Transactional
	public void doFroms(){
		// inner join, onetomany, cannot use path 'e.phones'
		String jpql = "SELECT e.name, p FROM jpa_query_employee e JOIN e.phones p";
		showResult(jpql);
		// same result, but longer
		jpql = "SELECT e.name, p FROM jpa_query_employee e, jpa_query_phone p WHERE e.id=p.employee";
		showResult(jpql);
		
		// inner join, manytoone, can use path
		jpql = "SELECT e.name,e.department FROM jpa_query_employee e";
		showResult(jpql);
		jpql = "SELECT e.name,d FROM jpa_query_employee e JOIN e.department d";
		showResult(jpql);
		jpql = "SELECT e.name,d FROM jpa_query_employee e, jpa_query_department d where e.department=d.id";
		showResult(jpql);
		
		// inner join, manytomany
		jpql = "SELECT e.name, p FROM jpa_query_employee e JOIN e.projects p";
		showResult(jpql);
		// error, cannot use where clause
		jpql = "SELECT e.name, p FROM jpa_query_employee e, jpa_query_project p, jpa_query_emp_prj ep where e.id = ep.pk_employee AND p.id = ep.pk_project";
		
		// multiple joins
		jpql = "SELECT e.name, p, d FROM jpa_query_employee e JOIN e.projects p JOIN e.department d";
		showResult(jpql);
		
		// outer join
		jpql = "SELECT e, d FROM jpa_query_employee e LEFT JOIN e.department d";
		showResult(jpql);
		
		// fetch inner join
		jpql = "SELECT e FROM jpa_query_employee e JOIN FETCH e.department JOIN FETCH e.address JOIN FETCH e.phones";
		showResult(jpql);
		// fetch left outer join
		jpql = "SELECT d FROM jpa_query_department d LEFT JOIN FETCH d.employees";
		showResult(jpql);
	}
	
	@Transactional
	public void doWheres(){
		// use input paramerter by index
		String jpql = "SELECT e FROM jpa_query_employee e WHERE e.salary > ?1";
		showResult(jpql, 1, 500);
		// use input parameter by name
		jpql = "SELECT e FROM jpa_query_employee e WHERE e.salary > :salary";
		showResult(jpql, "salary", 500);
		
		// between
		jpql = "SELECT e FROM jpa_query_employee e WHERE e.salary BETWEEN 1000 AND 5000";
		showResult(jpql);
		
		// like: _:one char / % :multi char
		jpql = "SELECT d FROM jpa_query_department d WHERE d.name LIKE 'depar_ment%'";
		showResult(jpql);
		// ESCAPE
//		jpql = "SELECT d FROM jpa_query_department d WHERE d.name LIKE 'depar\\_ment%' ESCAPE '\\'";
//		System.out.println(jpql);
//		showResult(jpql);
		
		// subquery
		jpql = "SELECT e FROM jpa_query_employee e WHERE e.salary = (SELECT MAX(emp.salary) FROM jpa_query_employee emp)";
		showResult(jpql);
		
		// exists
		// use main query variable in subquery and enum in query
		jpql = "SELECT e FROM jpa_query_employee e WHERE EXISTS (SELECT 1 FROM jpa_query_phone p WHERE p.employee = e AND p.type = bq.jpa.demo.query.domain.PhoneType.Mobile)";
		showResult(jpql);
		
		// in
		jpql = "SELECT e FROM jpa_query_employee e WHERE e.address.state IN ('NY','MI')";
		showResult(jpql);
		jpql = "SELECT e FROM jpa_query_employee e WHERE e.department IN (SELECT DISTINCT d FROM jpa_query_department d JOIN d.employees de JOIN de.projects p WHERE p.name LIKE 'project%')";
		showResult(jpql);
		
		// not in
		jpql = "SELECT p FROM jpa_query_phone p WHERE p.type NOT IN ('home','work')";
		showResult(jpql);
		
		// collection related expressions
		
		// is not empty
		jpql = "SELECT e FROM jpa_query_employee e WHERE e.directs IS NOT EMPTY";
		showResult(jpql);
		// equivalent to 		
		jpql = "SELECT e FROM jpa_query_employee e WHERE (SELECT COUNT(emp) FROM jpa_query_employee emp WHERE emp.manager = e) > 0";
		showResult(jpql);
		
		// "member of" and "not member of"
		jpql = "SELECT e FROM jpa_query_employee e WHERE :project MEMBER OF e.projects";
		Project project = em.find(Project.class, 1);
		showResult(jpql,"project",project);
		// equivalant to
		jpql = "SELECT e FROM jpa_query_employee e WHERE :project IN (SELECT p FROM e.projects p)";
		showResult(jpql,"project", project);
		
		// exists
		jpql = "SELECT e FROM jpa_query_employee e WHERE NOT EXISTS (SELECT p FROM e.phones p WHERE p.type='home')";
		showResult(jpql);
		
		// all, any, some
		jpql = "SELECT e FROM jpa_query_employee e WHERE e.directs IS NOT EMPTY and e.salary < ALL (SELECT d.salary FROM e.directs d)";
		showResult(jpql);
		jpql = "SELECT e FROM jpa_query_employee e WHERE e.department = ANY (SELECT DISTINCT d FROM jpa_query_department d JOIN d.employees de JOIN de.projects p WHERE p.name LIKE 'project%')";
		showResult(jpql);
		
		// literals
		// KEY of map, and enum
		jpql = "SELECT e FROM jpa_query_employee e JOIN e.phones p WHERE KEY(p) = bq.jpa.demo.query.domain.PhoneType.Home";
		showResult(jpql);
		
		// functions
		// size
		jpql = "SELECT d FROM jpa_query_department d WHERE SIZE(d.employees) = 2";
		showResult(jpql);
		// count
		jpql = "SELECT d FROM jpa_query_department d WHERE (SELECT COUNT(e) FROM d.employees e) = 2";
		showResult(jpql);
		// index
		jpql = "SELECT e.name, p.number FROM jpa_query_employee e JOIN e.phones p WHERE INDEX(p) = 0";
		showResult(jpql);
		
		// case
		// form 1
		jpql = "SELECT p.name, CASE WHEN TYPE(p)=bq.jpa.demo.query.domain.DesignProject THEN 'Development' WHEN TYPE(p)=bq.jpa.demo.query.domain.QualityProject THEN 'QA' ELSE 'Unknow' END FROM jpa_query_project p";
		showResult(jpql);
		// form 2
		jpql = "SELECT p.name, CASE TYPE(p) WHEN bq.jpa.demo.query.domain.DesignProject THEN 'Development' WHEN bq.jpa.demo.query.domain.QualityProject THEN 'QA' ELSE 'Unknow' END FROM jpa_query_project p";
		showResult(jpql);
		
		// coalescle
		// return d.name if d.name isn't null, return d.id if d.name is null
		jpql = "SELECT COALESCE(d.name, d.id) FROM jpa_query_department d";
		showResult(jpql);
		
		// nullif
		// if two expressions are equal, then the result is null
		// below returns a count of all departments and all departments not named 'QA'
		jpql = "SELECT COUNT(*), NULLIF(d.name, 'department1') FROM jpa_query_department d";
		showResult(jpql);
		
		// order by, default ASC
		jpql = "SELECT e FROM jpa_query_employee e ORDER BY e.name DESC";
		showResult(jpql);
		jpql = "SELECT e, d FROM jpa_query_employee e JOIN e.department d ORDER BY d.name, e.name DESC";
		showResult(jpql);
		jpql = "SELECT e.name, e.salary*0.05 as bonus, d.name AS departmentName FROM jpa_query_employee e JOIN e.department d ORDER BY departmentName, bonus DESC";
		showResult(jpql);
		// if the SELECT clause uses path expression, the ORDER BY clause is limited to the same path expression
		jpql = "SELECT e.name FROM jpa_query_employee e ORDER BY e.salary DESC";
		showResult(jpql);
	}
	
	@Transactional
	public void doAggregates(){
		// avg
		String jpql = "SELECT AVG(e.salary) FROM jpa_query_employee e";
		showResult(jpql);

		// group by
		jpql = "SELECT d.name, AVG(e.salary) FROM jpa_query_department d JOIN d.employees e GROUP BY d.name";
		showResult(jpql);
		// multiple group by
		jpql = "SELECT d.name, SUM(e.salary), COUNT(p) FROM jpa_query_department d JOIN d.employees e JOIN e.projects p GROUP BY d.name, e.salary";
		showResult(jpql);
		
		// having
		jpql = "SELECT d.name, AVG(e.salary) FROM jpa_query_department d JOIN d.employees e GROUP BY d.name HAVING AVG(e.salary) > 500";
		showResult(jpql);
		
		// count
		jpql = "SELECT e, COUNT(p), COUNT(DISTINCT p.type) FROM jpa_query_employee e JOIN e.phones p GROUP BY e";
		showResult(jpql);
	}
	
	@Transactional
	public void doUpdateAndDelete(){
		// update
		String dmJpql =  "UPDATE jpa_query_employee e SET e.salary = e.salary + 1000";
		em.createQuery(dmJpql).executeUpdate();
		
		// function concat / substring / locate
		dmJpql = "UPDATE jpa_query_phone p SET p.number = SUBSTRING(p.number, 5, 8) WHERE p.type = bq.jpa.demo.query.domain.PhoneType.Home";
		em.createQuery(dmJpql).executeUpdate();
		
		// multiple join update
		// NOTE:not support two level object path (p.employee.address.city='NY'), only support one level
		// dmJpql = "UPDATE jpa_query_phone p SET p.number = CONCAT('888', SUBSTRING(p.number, LOCATE(p.number, '-'), 4)) WHERE p.employee.address.city='NY'";
		// Equivalent to above
		dmJpql = "UPDATE jpa_query_phone p SET p.number = CONCAT('888', SUBSTRING(p.number, 4, 8)) WHERE EXISTS (SELECT 1 FROM jpa_query_employee e WHERE p.employee=e AND e.address.state='NY')";
		em.createQuery(dmJpql).executeUpdate();
		
		// delete
		dmJpql = "DELETE FROM jpa_query_employee e WHERE e.salary > 5000";
		em.createQuery(dmJpql).executeUpdate();
	}
	
	private void showResult(String jpql){
		ResultViewer.showResult(getResult(jpql),jpql);
	}
	
	private void showResult(String jpql, String paraName, Object value){
		ResultViewer.showResult(getResult(jpql, paraName, value),jpql);
	}
	
	private void showResult(String jpql, int position, Object value){
		ResultViewer.showResult(getResult(jpql, position, value),jpql);
	}
	
	private List getResult(String jpql){
		return em.createQuery(jpql).getResultList();
	}

	private List getResult(String jpql, String string, Object j) {
		return em.createQuery(jpql)
				.setParameter(string, j)
				.getResultList();
	}

	private List getResult(String jpql, int i, Object j) {
		return em.createQuery(jpql)
				.setParameter(i, j)
				.getResultList();
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}
