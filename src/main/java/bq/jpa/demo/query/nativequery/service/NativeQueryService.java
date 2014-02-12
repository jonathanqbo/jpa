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

package bq.jpa.demo.query.nativequery.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.query.domain.Employee;
import bq.jpa.demo.query.service.ResultViewer;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 11, 2014 9:39:29 PM
 *
 */
@Service
public class NativeQueryService {

	private static final String EMPLOYEE_QUERY=	"SELECT * FROM jpa_query_employee e WHERE e.salary > ?";
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource(name="dataSource")
	private DataSource ds;
	
	/**
	 * use jpa combined with native sql
	 */
	@Transactional
	public void doNativeQuery(){
		List results = em.createNativeQuery(EMPLOYEE_QUERY,Employee.class)
			.setParameter(1, 1500)
			.getResultList();
		
		ResultViewer.showResult(results, EMPLOYEE_QUERY);
	}
	
	/**
	 * use jdbc directly
	 */
	public void doJDBCQuery(){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement(EMPLOYEE_QUERY);
			ps.setLong(1, 1500);
			ResultSet rs = ps.executeQuery();
			
			List<Employee> employees = new ArrayList<>();
			while(rs.next()){
				Employee employee = new Employee();
				employee.setId(rs.getInt("pk_employee"));
				employee.setName(rs.getString("name"));
				employee.setSalary(rs.getInt("salary"));
				employees.add(employee);
			}
			
			ResultViewer.showResult(employees, EMPLOYEE_QUERY);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
