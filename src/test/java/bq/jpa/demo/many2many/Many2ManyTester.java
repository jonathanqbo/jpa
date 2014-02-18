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

package bq.jpa.demo.many2many;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bq.jpa.demo.many2many.domain.Employee;
import bq.jpa.demo.many2many.domain.Project;
import bq.jpa.demo.many2many.service.Many2ManyService;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 31, 2014 12:48:50 PM
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/application.xml")
public class Many2ManyTester {

	@Autowired
	private Many2ManyService service;
	
	@Test
	public void testAddEmployeeWithParkspace(){
		service.createEmployeeWithProjects();
		
		List<Employee> employees = service.findEmployees();
		for(Employee employee:employees){
			System.out.println("--- employee name:" + employee.getName() + "---");
			for(Project project : employee.getProjects()){
				System.out.println("  project name:" + project.getName());
			}
		}
//		
//		List<Project> projects = service.findProjects();
//		for(Project project: projects){
//			System.out.println("--- project name:" + project.getName() + "---");
//			for(Employee employee : project.getEmployees()){
//				System.out.println("  employee name:" + employee.getName());
//			}
//		}
	}
	
}