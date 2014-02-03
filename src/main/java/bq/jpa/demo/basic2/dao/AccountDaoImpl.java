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

package bq.jpa.demo.basic2.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bq.jpa.demo.basic2.dao.AccountDao;
import bq.jpa.demo.basic1.domain.Account;

/**
 * <b> show how to use container entitymanger usage in spring </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 27, 2014 5:15:53 PM
 *
 */
@Repository("jpaaccountdao")
public class AccountDaoImpl implements AccountDao{

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private EntityManagerFactory emf;
	
	/**
	 * share entitymanager model, not support bean-transaction
	 * only support container-transaction 
	 * 
	 * @param account
	 * @return
	 */
	@Override
	public Account add(Account account) {
		em.persist(account); 
		return account; 
	}
	
	/**
	 * inject entitymanagerfactory, can support bean-transaction
	 */
	@Override
	public Account addWithBeanTransaction(Account account){
		EntityManager localEM = emf.createEntityManager();
		localEM.getTransaction().begin();
		localEM.persist(account);
		localEM.getTransaction().commit();
		localEM.close();
		return account;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
}
