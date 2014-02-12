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

package bq.jpa.demo.inherit.pertable.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.TableGenerator;

/**
 * <b>  </b>
 *
 * <p> Note: In Mysql, cannot combine InheritanceType.TABLE_PER_CLASS with Id GenerationType.AUTO and IDENTITY
 * only support strategy=GenerationType.TABLE
 * </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 10, 2014 9:17:41 PM
 *
 */
@Entity(name="jpa_inherit_pertable_employee")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Employee {

	@Id
	@TableGenerator(
			name="jpa_inherit_employee_idtable",
			table="jpa_inherit_employee_idtable",
			pkColumnName="pk_employee_idgen",
			valueColumnName="idvalue",
			initialValue=10000,
			allocationSize=100
			)
	@GeneratedValue(generator="jpa_inherit_employee_idtable",strategy=GenerationType.TABLE)
	@Column(name="pk_employee")
	private int Id;
	
	private String name;
	
	private Date startDate;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
}
