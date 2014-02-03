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

package bq.jpa.demo.embed.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.embed.domain.Address;
import bq.jpa.demo.embed.domain.Company;
import bq.jpa.demo.embed.domain.Employee;
import bq.jpa.demo.embed.domain.Vacation;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 31, 2014 1:47:12 PM
 *
 */
@Service
public class EmbedService {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void createEmployee(){
		Address address = new Address();
		address.setCity("NYC");
		address.setState("NY");
		address.setStreet("Main Street");
		address.setZipcode("88888");
		
		Vacation vacation1 = new Vacation();
		vacation1.setDays(1);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(2014, 1, 14);
		vacation1.setStartDate(calendar1);
		
		Vacation vacation2 = new Vacation();
		vacation2.setDays(5);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2014, 11, 26);
		vacation2.setStartDate(calendar2);
		
		Collection<Vacation> vacations = new ArrayList<Vacation>(2);
		vacations.add(vacation1);
		vacations.add(vacation2);
		
		Employee employee = new Employee();
		employee.setName("employee1");
		employee.setAddress(address);
		employee.setVocations(vacations);
		
		em.persist(employee);
	}
	
	@Transactional
	public void createCompany(){
		Address address = new Address();
		address.setCity("NYC");
		address.setState("NY");
		address.setStreet("Main Street");
		address.setZipcode("88888");
		
		Company company = new Company();
		company.setName("company1");
		company.setAddress(address);
		
		em.persist(company);
	}
	
}
