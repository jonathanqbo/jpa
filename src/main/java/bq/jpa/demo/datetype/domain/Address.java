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

import java.io.IOException;
import java.io.Serializable;

/**
 * <b> serializable object used in entity </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Jan 28, 2014 3:31:18 PM
 *
 */
public class Address implements Serializable{

	private static final long serialVersionUID = 8751175660129135731L;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private String road;
	
	private String number;
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		System.out.println("writeObject invoked");
		out.writeObject(toString());
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(number);
		sb.append("-");
		sb.append(road);
		sb.append("-");
		sb.append(city);
		sb.append("-");
		sb.append(state);
		sb.append("-");
		sb.append(country);
		return sb.toString();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,ClassNotFoundException {
		System.out.println("readObject invoked");
		String address = (String) in.readObject();
		System.out.println("read address:" + address);
		String[] addressspli = address.split("-");
		this.number = addressspli[0];
		this.road = addressspli[1];
		this.city = addressspli[2];
		this.state = addressspli[3];
		this.country = addressspli[4];
	} 

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
