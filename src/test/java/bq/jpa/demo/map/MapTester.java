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

package bq.jpa.demo.map;

import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bq.jpa.demo.map.domain.Department;
import bq.jpa.demo.map.domain.Employee;
import bq.jpa.demo.map.domain.PhoneType;
import bq.jpa.demo.map.service.MapService;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 31, 2014 11:41:32 PM
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/application.xml")
public class MapTester {
	
	@Autowired
	private MapService service;

	@Test
	public void testMap(){
		service.createEmploy();
		
		Department department = service.findDepartment();
		for(Entry<String, Employee> entry:department.getEmployeesByCubicle().entrySet()){
			Employee employee = entry.getValue();
			System.out.println("cube " + entry.getKey() + ":" + employee.getName());
			for(Entry<String, String> phoneEntry : employee.getPhones().entrySet()){
				System.out.println(" phone group1  " + phoneEntry.getKey() + ":" + phoneEntry.getValue());
			}
			for(Entry<PhoneType, String> phoneEntry : employee.getPhones2().entrySet()){
				System.out.println(" phone group2  " + phoneEntry.getKey() + ":" + phoneEntry.getValue());
			}
		}
	}
	
}
