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

package bq.jpa.demo.inherit.mix.service;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import bq.jpa.demo.inherit.mix.domain.ContractEmployee;
import bq.jpa.demo.inherit.mix.domain.FullTimeEmployee;
import bq.jpa.demo.inherit.mix.domain.PartTimeEmployee;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 10, 2014 10:06:49 PM
 *
 */
@Service
public class InheritMixService {

	@PersistenceContext
	private EntityManager em;
	
	public void prepareDate(){
		// contract
		for(int i = 0; i < 10; i++){
			ContractEmployee contractEmployee = new ContractEmployee();
			contractEmployee.setName("contractor_" + i);
			contractEmployee.setTerm(i);
			contractEmployee.setDailyRate(10*i);
			contractEmployee.setStartDate(new Date(System.currentTimeMillis()));
			em.persist(contractEmployee);
		}
		
		for(int i = 0; i < 10; i++){
			FullTimeEmployee fulltimeEmployee = new FullTimeEmployee();
			fulltimeEmployee.setName("fulltime_" + i);
			fulltimeEmployee.setPension(1000*i);
			fulltimeEmployee.setSalary(300*i);
			fulltimeEmployee.setVacation(2*i);
			fulltimeEmployee.setStartDate(new Date(System.currentTimeMillis()));
			em.persist(fulltimeEmployee);
		}
		
		for(int i = 0; i < 10; i++){
			PartTimeEmployee parttimeEmployee = new PartTimeEmployee();
			parttimeEmployee.setName("parttime_" + i);
			parttimeEmployee.setHourlyRate(10*i);
			parttimeEmployee.setVacation(3*i);
			parttimeEmployee.setStartDate(new Date(System.currentTimeMillis()));
			em.persist(parttimeEmployee);
		}
	}
	
}
