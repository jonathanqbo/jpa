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

package bq.jpa.demo.query.domain;

import java.util.Collection;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * <b>  </b>
 *
 * <p> NOTE:namedquery's name is scoped to the entire persistence unit and must be unique within the scope </p>
 * 
 * <p> NOTE:namedquery must be placed on the entity class</p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 6, 2014 3:05:58 PM
 *
 */
@NamedNativeQueries({
	@NamedNativeQuery(name="jpa_query_namednative_employee",
			query="SELECT * FROM jpa_query_employee e WHERE e.salary > ?",
			resultClass=Employee.class
			)	
})
@NamedQueries({
	@NamedQuery(name="jpa_query_named_employee",
		query="SELECT e FROM jpa_query_employee e WHERE e.salary > ?1"	
	)
})
@Entity(name="jpa_query_employee")
public class Employee {

	@Id
	@Column(name="pk_employee")
	@GeneratedValue
	private int id;
	
	private String name;
	
	private int salary;
	
	@OneToMany(mappedBy="employee")
	@MapKeyColumn(name="type")
	@MapKeyEnumerated(EnumType.STRING)
	private Map<PhoneType, Phone> phones;

	@ManyToOne
	@JoinColumn(name="pk_department")
	private Department department;
	
	@OneToOne
	@JoinColumn(name="pk_address")
	private Address address;
	
	@ManyToMany
	@JoinTable(name="jpa_query_emp_prj",
			joinColumns=@JoinColumn(name="pk_employee"),
			inverseJoinColumns=@JoinColumn(name="pk_project")
			)
	private Collection<Project> projects; 
	
	/** bidirectional self join */
	@OneToMany(mappedBy="manager")
	private Collection<Employee> directs;
	
	/** bidirectional self join */
	@ManyToOne
	@JoinColumn(name="pk_manager")
	private Employee manager;
	
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

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Map<PhoneType, Phone> getPhones() {
		return phones;
	}

	public void setPhones(Map<PhoneType, Phone> phones) {
		this.phones = phones;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Collection<Project> getProjects() {
		return projects;
	}

	public void setProjects(Collection<Project> projects) {
		this.projects = projects;
	}

	public Collection<Employee> getDirects() {
		return directs;
	}

	public void setDirects(Collection<Employee> directs) {
		this.directs = directs;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}
	
}
