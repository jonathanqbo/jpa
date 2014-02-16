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

package bq.jpa.demo.query.criteria.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.query.domain.Project;
import bq.jpa.demo.query.domain.DesignProject;
import bq.jpa.demo.query.domain.Employee;
import bq.jpa.demo.query.domain.EmployeeBasicInfo;
import bq.jpa.demo.query.domain.QualityProject;
import bq.jpa.demo.query.domain.ResultViewer;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 13, 2014 3:53:41 PM
 *
 */
@Service
public class CriteriaService {

	@PersistenceContext
	private EntityManager em;
	
	/**
	 * SELECT e FROM jpa_query_employee
	 */
	@Transactional
	public void doSimple(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Employee> c = cb.createQuery(Employee.class);
		Root<Employee> e = c.from(Employee.class);
		c.select(e);
		
		showResult(c);
	}
	
	/**
	 * SELECT e FROM jpa_query_employee e WHERE e.name='employee_1'
	 */
	@Transactional
	public void doSimple2(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Employee> c = cb.createQuery(Employee.class);
		Root<Employee> e = c.from(Employee.class);
		c.select(e)
			.where(cb.equal(e.get("name"), "employee_1"));
		
		showResult(c);
	}
	
	/**
	 * SELECT e FROM jpa_query_employee e WHERE e.name= :name
	 */
	@Transactional
	public void doParameterQuery(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Employee> c = cb.createQuery(Employee.class);
		Root<Employee> e = c.from(Employee.class);
		
		// parameter, equal to ":name"
		ParameterExpression<String> paraName = cb.parameter(String.class, "name");
		
		// e.name = ":name"
		c.select(e)
			.where(cb.equal(e.get("name"), paraName));
		
		// set param value
		TypedQuery<Employee> query = em.createQuery(c);
		query.setParameter("name", "employee_1");
		List<Employee> result = query.getResultList();
		ResultViewer.showResult(result, "criteria query : parameter query");
	}
	
	/**
	 * SELECT e.name FROM jpa_query_employee'
	 */
	@Transactional
	public void doSelect1(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<String> c = cb.createQuery(String.class);
		Root<Employee> e = c.from(Employee.class);
		c.select(e.<String>get("name"));
		
		showResult(c);
	}
	
	/**
	 * multiple select
	 */
	public void doSelect2(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		// method1: tuple
		CriteriaQuery<Tuple> tupleQuery = cb.createTupleQuery();
		Root<Employee> e1 = tupleQuery.from(Employee.class);
		tupleQuery.select(cb.tuple(e1.get("id"),e1.get("name")));
		showResult(tupleQuery);
		
		// method2: object[] and criteriaquery.multiselec
		CriteriaQuery<Object[]> multiQuery = cb.createQuery(Object[].class);
		Root<Employee> e2 = multiQuery.from(Employee.class);
		multiQuery.multiselect(e2.get("id"), e2.get("name"));
		showResult(multiQuery);
	}
	
	/**
	 * use customize object in select
	 */
	public void doSelect3(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		// return type is a customized object
		CriteriaQuery<EmployeeBasicInfo> c = cb.createQuery(EmployeeBasicInfo.class);
		Root<Employee> e = c.from(Employee.class);
		
		// method 1 : must use multiple select, cannot use select method, because of strong type check
		c.multiselect(e.get("name"));
		showResult(c);
		
		// method 2 : use select and cb.construct
		c.select(cb.construct(EmployeeBasicInfo.class, e.get("name")));
		showResult(c);
		
		// method 3 : use multiple select and cb.construct
		// NOTE : UNSUPPORT!
//		CriteriaQuery<EmployeeBasicInfo> c2 = cb.createQuery(EmployeeBasicInfo.class);
//		Root<Employee> e2 = c2.from(Employee.class);
//		c2.multiselect(cb.construct(EmployeeBasicInfo.class, e2.get("name")));
//		showResult(c2);
	}
	
	/**
	 * use aliases
	 */
	@Transactional
	public void doSelect4(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<String> c = cb.createQuery(String.class);
		Root<Employee> e = c.from(Employee.class);
		c.select(e.<String>get("name").alias("fullname"));
		showResult(c);
	}
	
	/**
	 * inner join
	 */
	@Transactional
	public void doFrom1(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> c = cb.createQuery(Object[].class);
		Root<Employee> e = c.from(Employee.class);
		Join<Object, Object> address = e.join("address");
		Join<Object, Object> department = e.join("department");
		c.multiselect(e,address,department);
//			.where(cb.and(
//					cb.equal(address.get("state"), "NY")),
//					cb.greaterThan(e.<Long>get("salary"), Long.valueOf(1500)), 
//					cb.like(department.<String>get("name"), "department%"
//				));
		showResult(c);
	}
	
	/**
	 * out join
	 */
	@Transactional
	public void doFrom2(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> c = cb.createQuery(Object[].class);
		Root<Employee> e = c.from(Employee.class);
		Join<Object, Object> address = e.join("address", JoinType.LEFT);
		Join<Object, Object> department = e.join("department", JoinType.LEFT);
		c.multiselect(e,address,department);
		
		showResult(c);
	}
	
	/**
	 * fetch join:
	 * SELECT e FROM jpa_query_employee e JOIN FETCH e.address
	 */
//	@Transactional
	public void doFrom3(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Employee> c = cb.createQuery(Employee.class);
		Root<Employee> e = c.from(Employee.class);
		e.fetch("address");
		e.fetch("department");
		e.fetch("projects",JoinType.INNER);
//		e.fetch("phones");
//		e.fetch("directs");
//		e.fetch("manager");
		c.select(e);
		
		// only show the fetched data
		TypedQuery<Employee> query = em.createQuery(c);
		List<Employee> result = query.getResultList();
		for(Employee emp : result){
			System.out.println(emp.getId() + " | " + emp.getName() + " | " + emp.getAddress() + " | " + emp.getDepartment() + " | " + emp.getProjects());
		}
	}
	
	/**
	 * subquery: in
	 * SELECT e FROM jpa_query_employee e WHERE e IN (SELECT emp FROM jpa_query_project p JOIN p.employees pe WHERE p.name = :projectname)
	 */
	@Transactional
	public void doWhere1(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Employee> c = cb.createQuery(Employee.class);
		Root<Employee> e = c.from(Employee.class);
		
		// subquery
		Subquery<Employee> sq = c.subquery(Employee.class);
		Root<Project> p = sq.from(Project.class);
		Join<Project, Employee> pe = p.join("employees");
		sq.select(pe)
		.where(cb.equal(p.get("name"), cb.parameter(String.class, "projectname")));
		
		//
		c.select(e)
			.where(cb.in(e).value(sq));
	}
	
	/**
	 * subquery(equivalent to dowhere1) : exists
	 * SELECT e FROM jpa_query_employee e WHERE EXISTS (SELECT p FROM e.projects p WHERE p.name = :projectname)
	 */
	@Transactional
	public void doWhere2(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Employee> c = cb.createQuery(Employee.class);
		Root<Employee> e = c.from(Employee.class);
		
		// subquery
		Subquery<Project> sq = c.subquery(Project.class);
		// Root<Project> p = sq.from(Project.class);
		Root<Employee> se = sq.correlate(e);
		Join<Employee, Project> p = se.join("projects");
		sq.select(p)
			.where(cb.equal(p.get("name"), cb.parameter(String.class, "projectname")));
		
		//
		c.select(e)
			.where(cb.exists(sq));
	}
	
	/**
	 * in and path:
	 * SELECT e FROM jpa_query_employee e WHERE e.address.state IN ('NY','MI')
	 */
	@Transactional
	public void doWhere3(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Employee> c = cb.createQuery(Employee.class);
		Root<Employee> e = c.from(Employee.class);
		// method 1 
		c.select(e)
			.where(e.get("address").get("state").in("NY","MI"));
		showResult(c);
		
		// method 2
		c.select(e)
			.where(cb.in(e.get("address").get("state")).value("NY").value("MI"));
		showResult(c);
	}
	
	/**
	 * NOTE : Hibernate cannot support case in criteria way
	 * 
	 * case 1:
	 * SELECT p.name, 
	 * 			CASE WHEN TYPE(p)=bq.jpa.demo.query.domain.DesignProject  THEN 'dev' 
	 * 				 WHEN TYPE(p)=bq.jpa.demo.query.domain.QualityProject THEN 'QA' 
	 * 				 ELSE 'unknown' 
	 * 			END 
	 * FROM jpa_query_project p
	 * 
	 * case 2:
	 * SELECT p.name, 
	 * 			CASE TYPE(p)
	 * 				 WHEN bq.jpa.demo.query.domain.DesignProject  THEN 'dev' 
	 * 				 WHEN bq.jpa.demo.query.domain.QualityProject THEN 'QA' 
	 * 				 ELSE 'unknown' 
	 * 			END 
	 * FROM jpa_query_project p
	 */
	@Transactional
	public void doWhere4(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> c = cb.createQuery(Object[].class);
		Root<Project> p = c.from(Project.class);
		
		// method 1
		c.multiselect(p.get("name"),
				cb.selectCase()
					.when(cb.equal(p.type(), DesignProject.class), "dev")
					.when(cb.equal(p.type(), QualityProject.class), "QA")
					.otherwise("unknown"));
		
//		showResult(c);
		
		// method 2:
		CriteriaQuery<Object[]> c2 = cb.createQuery(Object[].class);
		Root<Project> p2 = c2.from(Project.class);
		c2.multiselect(p2.get("name"),
				cb.selectCase(p2.type())
					.when(DesignProject.class, "dev")
					.when(QualityProject.class, "QA")
					.otherwise("unknown")
				);
//		showResult(c2);
	}
	
	/**
	 * order by:
	 * SELECT e.name, e.salary FROM jpa_query_employee e ORDER BY e.name DESC, e.salary DESC
	 */
	public void doOrderby(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> c = cb.createQuery(Object[].class);
		Root<Employee> e = c.from(Employee.class);
		c.multiselect(e.get("name"), e.get("salary"))
			.orderBy(cb.desc(e.get("name")),cb.desc(e.get("salary")));
		
		showResult(c);
	}
	
	/**
	 * group by and having :
	 * SELECT e, COUNT(p) FROM jpa_query_employee e JOIN e.projects p GROUP BY e HAVING COUNT(p) >= 2
	 */
	@Transactional
	public void doGroupby(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> c = cb.createQuery(Object[].class);
		Root<Employee> e = c.from(Employee.class);
		Join<Employee, Project> p = e.join("projects");
		c.multiselect(e, cb.count(p))
			.groupBy(e)
			.having(cb.ge(cb.count(p),2));
		
		showResult(c);
	}
	
	private void showResult(CriteriaQuery c){
		TypedQuery query = em.createQuery(c);
		
		// result
		List result = query.getResultList();
		
		// sql string
		String sql = query.unwrap(org.hibernate.Query.class).getQueryString();
		
		ResultViewer.showResult(result, sql);
	}
	
}
