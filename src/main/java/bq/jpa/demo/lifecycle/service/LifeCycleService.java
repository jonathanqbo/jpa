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

package bq.jpa.demo.lifecycle.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.lifecycle.domain.ContractEmployee;
import bq.jpa.demo.lifecycle.domain.PartTimeEmployee;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 17, 2014 8:23:53 PM
 *
 */
@Service
public class LifeCycleService {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void doPersistParttimeEmployee(){
		PartTimeEmployee emp = new PartTimeEmployee();
		emp.setName("parttimeemployee_88");
		emp.setVacation(100);
		emp.setHourlyRate(88f);
		em.persist(emp);
	}
	
	@Transactional
	public void doPersistContractEmployee(){
		ContractEmployee emp = new ContractEmployee();
		emp.setName("contractemployee_88");
		emp.setTerm(100);
		emp.setDailyRate(88);
		em.persist(emp);
	}
}
