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

package bq.jpa.demo.query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bq.jpa.demo.query.criteria.service.CriteriaService;
import bq.jpa.demo.query.dm.service.DMService;
import bq.jpa.demo.query.namedquery.service.NamedQueryService;
import bq.jpa.demo.query.nativequery.service.NativeQueryService;
import bq.jpa.demo.query.page.service.PageQueryService;
import bq.jpa.demo.query.querylang.service.QueryService;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 6, 2014 9:59:15 PM
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/application.xml")
public class QueryTester {
	
	@Autowired
	private QueryService service;
	
	@Autowired
	private CriteriaService criteriaService;
	
	@Autowired
	private NativeQueryService nativeQueryService;
	
	@Autowired
	private NamedQueryService namedQueryService;
	
	@Autowired
	private PageQueryService pageQueryService;
	
	@Autowired
	private DMService dmService;

	@Test
	public void test(){
		service.prepareData();
		service.doSelects();
		service.doFroms();
		service.doWheres();
		service.doAggregates();
		
		criteriaService.doSimple();
		criteriaService.doSimple2();
		criteriaService.doParameterQuery();
		criteriaService.doSelect1();
		criteriaService.doSelect2();
		criteriaService.doSelect3();
		criteriaService.doSelect4();
		criteriaService.doFrom1();
		criteriaService.doFrom2();
		criteriaService.doFrom3();
		criteriaService.doWhere1();
		criteriaService.doWhere2();
		criteriaService.doWhere3();
		criteriaService.doWhere4();
		criteriaService.doGroupby();
		criteriaService.doOrderby();
		
		nativeQueryService.doJDBCQuery();
		nativeQueryService.doNativeQuery();
		
		namedQueryService.doNamedNativeQuery();
		namedQueryService.doNamedQuery();
		
		pageQueryService.init(10, "SELECT COUNT(e.id) FROM jpa_query_employee e", "SELECT e FROM jpa_query_employee e");
		pageQueryService.doPageQuery();
		
		dmService.doUpdate();
		dmService.doDelete();
	}
	
}
