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

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * <b>  </b>
 *
 * <p> </p>
 *
 * @author Jonathan Q. Bo (jonathan.q.bo@gmail.com)
 *
 * Created at Feb 11, 2014 9:48:02 PM
 *
 */
public class ResultViewer {

	public static void showResult(List result, String jpql){
		System.out.println();
		System.out.println("-------- show result : " + jpql + " --------");
		
		if(result.size() == 0)
			System.out.println("0 results returned");
		
		for(Object row : result)
			printResult(row);
	}
	
	public static void printResult(Object obj) {
		if(obj instanceof Object[]){
			Object[] columns = (Object[]) obj;
			for(Object column : columns)
				printResult(column);
		}
		else if( obj instanceof Long || obj instanceof Double || obj instanceof String)
			System.out.print(obj.getClass().getName() + ": " + obj);
		else{
			System.out.print(ReflectionToStringBuilder.toString(obj,ToStringStyle.SHORT_PREFIX_STYLE));
		}
		
		System.out.println();
		
	}
	
}
