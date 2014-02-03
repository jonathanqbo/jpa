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

package bq.jpa.demo.map.domain;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;

/**
 * <b> show Map collection usage </b>
 *
 * <p> how to construct a map
 * <li> map relations are stored in single table
 * <li> map relations are onetomany relationship
 * <li> can use enum as key of map
 *  </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 31, 2014 4:22:31 PM
 *
 */
@Entity(name="jpa_map_employee")
public class Employee {

	@Id
	@GeneratedValue
	@Column(name="pk_employee")
	private int id;
	
	private String name;
	
	private String cubicle;
	
	@ManyToOne
	@JoinColumn(name="pk_department")
	private Department department;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="jpa_map_employee_phone")
	@MapKeyColumn(name="phonetype")
	@Column(name="phonenumber")
	private Map<String, String> phones;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="jpa_map_employee_phone2")
	@MapKeyColumn(name="phonetype2")
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name="phonenumber2")
	private Map<PhoneType, String> phones2;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getPhones() {
		return phones;
	}

	public void setPhones(Map<String, String> phones) {
		this.phones = phones;
	}

	public String getCubicle() {
		return cubicle;
	}

	public void setCubicle(String cubicle) {
		this.cubicle = cubicle;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Map<PhoneType, String> getPhones2() {
		return phones2;
	}

	public void setPhones2(Map<PhoneType, String> phones2) {
		this.phones2 = phones2;
	}
	
}
