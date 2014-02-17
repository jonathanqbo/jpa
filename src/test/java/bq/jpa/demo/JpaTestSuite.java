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

package bq.jpa.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import bq.jpa.demo.basic1.Basic1Tester;
import bq.jpa.demo.basic2.Basic2Tester;
import bq.jpa.demo.datetype.DatetypeTester;
import bq.jpa.demo.embed.EmbedTester;
import bq.jpa.demo.idgen.IdgenTester;
import bq.jpa.demo.inherit.InheritTest;
import bq.jpa.demo.many2many.Many2ManyTester;
import bq.jpa.demo.many2one.Many2OneTester;
import bq.jpa.demo.map.MapTester;
import bq.jpa.demo.metadata.MetadataTester;
import bq.jpa.demo.one2many.One2ManyTester;
import bq.jpa.demo.one2one.One2OneTester;
import bq.jpa.demo.one2onebi.One2OneBiTester;
import bq.jpa.demo.query.QueryTester;
import bq.jpa.demo.version.VersionTester;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 16, 2014 8:52:25 PM
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	Basic1Tester.class,
	Basic2Tester.class,
	DatetypeTester.class,
	IdgenTester.class,
	EmbedTester.class,
	Many2OneTester.class,
	One2OneTester.class,
	One2OneBiTester.class,
	One2ManyTester.class,
	Many2ManyTester.class,
	MapTester.class,
	InheritTest.class,
	QueryTester.class,
	MetadataTester.class,
	VersionTester.class
})
public class JpaTestSuite {

}
