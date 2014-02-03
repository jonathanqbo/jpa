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

package bq.jpa.demo.datetype.domain;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <b> show usuage of all kinds of types </b>
 *
 * <p> primitive type, wrapper class of primitive types, large number, 
 * temporal type, enumerated type, serializable type etc.</p>
 * 
 * <p> simple mapping field to column, </p>
 * 
 * <p> remember to close eclipse jpa validation, or compile error </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 28, 2014 2:54:41 PM
 *
 */
@Entity
@Table(name="jpa_employee")
public class Employee {

	@Id
	@Column(name="pk_employee")
	private int employeeId;
	
	private String employeeName;
	
	private char gender;
	
	private boolean isRetired;
	
	private float salary;
	
	private Short familymember;
	
	private BigDecimal saleAmount;
	
	private String title;
	
	/** java.util.Date */
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	/** java.sql.Time , java.sql. don't need TemporalType, just act as other simple type*/
	private Time timeFrom;
	
	/** java.sql.Timestamp */
	private Timestamp lastLoginTime;
	
	@Basic(fetch = FetchType.LAZY)
	@Lob
	private byte[] picture;
	
	@Basic(fetch = FetchType.LAZY)
	@Lob
	private String resume;
	
	@Enumerated(EnumType.STRING)
	private EmployeeType type;
	
	/** don't need serialize to database*/
	private transient boolean isOnline;
	
	/** default is tinyclob type(mysql), here change to varchar */
	@Column(columnDefinition="longtext")
	private Address homeAddress;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public boolean isRetired() {
		return isRetired;
	}

	public void setRetired(boolean isRetired) {
		this.isRetired = isRetired;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public Short getFamilymember() {
		return familymember;
	}

	public void setFamilymember(Short familymember) {
		this.familymember = familymember;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Time getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Time timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public EmployeeType getType() {
		return type;
	}

	public void setType(EmployeeType type) {
		this.type = type;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}
	
}
