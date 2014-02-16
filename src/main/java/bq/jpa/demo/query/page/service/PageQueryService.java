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

package bq.jpa.demo.query.page.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bq.jpa.demo.query.domain.ResultViewer;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 11, 2014 10:15:16 PM
 *
 */
@Service
public class PageQueryService {

	@PersistenceContext
	private EntityManager em;
	
	private String jpql;
	
	private int currentPage;
	private long maxResults;
	private int pageSize;
	
	@Transactional
	public void init(int pageSize, String countJpql, String jpql){
		this.pageSize = pageSize;
		this.jpql = jpql;
		
		// NOTE: select count() return value is Long, so maxResults must be long
		maxResults = em.createQuery(countJpql, Long.class).getSingleResult();
		currentPage = 0;
	}
	
	@Transactional
	public void doPageQuery(){
		long pages = getMaxPages();
		
		for(int i = 0; i < getMaxPages(); i++){
			System.out.println("---- page : " + i + "----");
			List pageResult = em.createQuery(jpql)
				.setFirstResult(currentPage*pageSize)
				.setMaxResults(pageSize)
				.getResultList();
			ResultViewer.showResult(pageResult, jpql);
			
			currentPage++;
		}
	}
	
	public long getMaxPages(){
		return maxResults/pageSize;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}
