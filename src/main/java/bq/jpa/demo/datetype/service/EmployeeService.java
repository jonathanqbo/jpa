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

package bq.jpa.demo.datetype.service;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.swing.ImageIcon;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.datetype.domain.Address;
import bq.jpa.demo.datetype.domain.Employee;
import bq.jpa.demo.datetype.domain.EmployeeType;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 28, 2014 4:18:29 PM
 *
 */
@Service
public class EmployeeService {

	@PersistenceContext
	private EntityManager em;
	
	public Employee find(int employeeId){
		return em.find(Employee.class, employeeId);
	}

	@Transactional
	public Employee create(){
		Employee employee = new Employee();

		employee.setEmployeeName("bq");
		employee.setGender('m');
		employee.setFamilymember((short)5);
		employee.setOnline(false);
		employee.setRetired(false);
		employee.setSalary(10000);
		employee.setSaleAmount(new BigDecimal("10000000"));

		Calendar date = Calendar.getInstance();
		date.set(1980, 8, 18);
		employee.setBirthday(date.getTime());
		
		employee.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
		employee.setTimeFrom(Time.valueOf("08:30:00"));
		
		employee.setType(EmployeeType.FULL_TIME_EMPLOYEE);

		Address homeAddress = new Address();
		homeAddress.setNumber("88");
		homeAddress.setRoad("Rich");
		homeAddress.setCity("New York");
		homeAddress.setState("NY");
		homeAddress.setCountry("USA");
		employee.setHomeAddress(homeAddress);

		//read png file
		try {
			employee.setPicture(readImage("/datetype/picture.png"));
		} catch (IOException e) {
			e.printStackTrace();
			employee.setPicture(null);
		}
		
		// read txt file
		try {
			employee.setResume(readFile("/datetype/resume.txt"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			employee.setResume(null);
		}
		
		em.persist(employee);
		return employee;
	}
	
	public void removeEmployee(int employeeId){
		Employee employee = em.find(Employee.class, employeeId);
		em.remove(employee);
	}
	
	private byte[] readImage(String fileName) throws IOException{
		BufferedImage pngfile = ImageIO.read(getClass().getResourceAsStream(fileName));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(pngfile, "png", baos);
		baos.flush();
		return baos.toByteArray();
	}
	
	private String readFile(String fileName) throws IOException, URISyntaxException {
		URI fileuri = getClass().getResource(fileName).toURI();
		File file = new File(fileuri);
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}
