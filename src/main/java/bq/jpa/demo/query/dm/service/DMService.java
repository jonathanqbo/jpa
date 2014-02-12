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

package bq.jpa.demo.query.dm.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.query.service.ResultViewer;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 11, 2014 9:18:29 PM
 *
 */
@Service("dmService")
public class DMService {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void doUpdate(){
		// update
		String dmJpql =  "UPDATE jpa_query_employee e SET e.salary = e.salary + 1000";
		int rowcount = em.createQuery(dmJpql).executeUpdate();
		System.out.println("----" + dmJpql + "----");
		System.out.println("update " + rowcount + " rows");
		
		// function concat / substring / locate
		dmJpql = "UPDATE jpa_query_phone p SET p.number = SUBSTRING(p.number, 5, 8) WHERE p.type = bq.jpa.demo.query.domain.PhoneType.Home";
		rowcount = em.createQuery(dmJpql).executeUpdate();
		System.out.println("----" + dmJpql + "----");
		System.out.println("update " + rowcount + " rows");
		
		dmJpql = "SELECT p FROM jpa_query_phone p WHERE p.type = bq.jpa.demo.query.domain.PhoneType.Home";
		List result = em.createQuery(dmJpql).getResultList();
		ResultViewer.showResult(result, dmJpql);
		
		// multiple join update
		// NOTE:not support two level object path (p.employee.address.city='NY'), only support one level
		// dmJpql = "UPDATE jpa_query_phone p SET p.number = CONCAT('888', SUBSTRING(p.number, LOCATE(p.number, '-'), 4)) WHERE p.employee.address.city='NY'";
		// Equivalent to above
		dmJpql = "UPDATE jpa_query_phone p SET p.number = CONCAT('888', SUBSTRING(p.number, 4, 8)) WHERE EXISTS (SELECT 1 FROM jpa_query_employee e WHERE p.employee=e AND e.address.state='NY')";
		dmJpql = "UPDATE jpa_query_phone p SET p.number = CONCAT('888', SUBSTRING(p.number, 4, 8))";
		rowcount = em.createQuery(dmJpql).executeUpdate();
		System.out.println("----" + dmJpql + "----");
		System.out.println("update " + rowcount + " rows");
		
		dmJpql = "SELECT p FROM jpa_query_phone p";
		result = em.createQuery(dmJpql).getResultList();
		ResultViewer.showResult(result, dmJpql);
	}
	
	/**
	 * NOTE: in one transaction, if same instances are executed twice, then the result are same in the transaction scope.
	 * this means first query entity from persistance cache
	 */
	@Transactional
	public void doSelectInUpdate(){
		// first update, will be flushed to database
		String dmJpql = "UPDATE jpa_query_phone p SET p.number = '666' WHERE p.type = bq.jpa.demo.query.domain.PhoneType.Home";
		
		// persistance context has cache some phone instance, these instance will not change in next query
		dmJpql = "SELECT p FROM jpa_query_phone p WHERE p.type = bq.jpa.demo.query.domain.PhoneType.Home";
		List result = em.createQuery(dmJpql).getResultList();
		ResultViewer.showResult(result, dmJpql);
		
		// second update
		dmJpql = "UPDATE jpa_query_phone p SET p.number = '888'";
		
		// the phone instances from the first query will not change, even actually value in database has changed
		dmJpql = "SELECT p FROM jpa_query_phone p";
		result = em.createQuery(dmJpql).getResultList();
		ResultViewer.showResult(result, dmJpql);
	}

	@Transactional
	public void doDelete(){
		String dmJpql = "DELETE FROM jpa_query_employee e WHERE e.salary > 5000";
		int rowcount = em.createQuery(dmJpql).executeUpdate();
		System.out.println("----" + dmJpql + "----");
		System.out.println("update " + rowcount + " rows");
		
		// bulk delete and relationships:
		// employees have the foreign key of deleted department, 
		// direct delete department will error,so must remove fk first
		dmJpql = "UPDATE jpa_query_employee e SET e.department = null WHERE EXISTS (SELECT 1 FROM e.department d WHERE d.name IN ('department_1','department_2'))";
		rowcount = em.createQuery(dmJpql).executeUpdate();
		dmJpql = "DELETE FROM jpa_query_department d WHERE d.name IN ('department_1','department_2')";
		rowcount = em.createQuery(dmJpql).executeUpdate();
		System.out.println("----" + dmJpql + "----");
		System.out.println("delete " + rowcount + " rows");
		
		dmJpql = "SELECT e FROM jpa_query_employee e";
		List result = em.createQuery(dmJpql).getResultList();
		ResultViewer.showResult(result, dmJpql);
		
		dmJpql = "SELECT d FROM jpa_query_department d";
		result = em.createQuery(dmJpql).getResultList();
		ResultViewer.showResult(result, dmJpql);
	}
	
}
