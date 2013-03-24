/*
 * ====================================================
 * Copyright (C) 2013 by Idylwood Technologies, LLC. All rights reserved.
 *
 * Developed at Idylwood Technologies, LLC.
 * Permission to use, copy, modify, and distribute this
 * software is freely granted, provided that this notice 
 * is preserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * The License should have been distributed to you with the source tree.
 * If not, it can be found at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Author: Charles Cooper
 * Date: 2013
 * ====================================================
 */

package com.idylytics.yahoo;

import java.net.URL;
import java.util.Map;
import java.util.HashMap;

// low level convenience class abstracting the yahoo url flags (so you don't have to memorize them)
public class UrlBuilder
{
	public enum Type {
		WEEKLY("w"), DAILY("d"), MONTHLY("m"), DIVIDEND("v");
		private final String flag;
		private Type(final String flag) { this.flag = flag; }
		@Override public String toString() { return flag; }
	}

	private Map<String,String> map = new HashMap<String,String>();

	public String baseUrl = "http://ichart.yahoo.com/table.csv?";
	// for dividends/splits, baseUrl is http://ichart.yahoo.com/x.csv?

	public UrlBuilder() {}
	public UrlBuilder(String symbol, Date start, Date end, Type type) {
		setSymbol(symbol); setStartDate(start); setEndDate(end); setType(type);
	}
	public UrlBuilder(String symbol, Type type)
	{
		setSymbol(symbol); setType(type);
	}

	public void set(String K, String V) {
		map.put(K,V);
	}
	@Override public String toString() {
		String ret = baseUrl;
		for (Map.Entry<String,String> entry : map.entrySet())
			ret += entry.getKey()+"="+entry.getValue()+"&";
		ret += "ignore=.csv";
		return ret;
	}
	public java.net.URL toURL()
		throws java.net.MalformedURLException
	{
		return new URL(this.toString());
	}
	public void setSymbol(String s) {
		set("s",s);
	}
	public void setStartDate(Date d) {
		// maybe don't need setday/month/year?
		setStartDay(d.day);
		setStartMonth(d.month-1); // January == 0
		setStartYear(d.year);
	}
	public void setEndDate(Date d) {
		setEndDay(d.day);
		setEndMonth(d.month-1); // January == 0
		setEndYear(d.year);
	}
	public void setStartDay(int day) {
		set("b",Integer.toString(day));
	}
	public void setStartMonth(int month) {
		set("a",Integer.toString(month));
	}
	public void setStartYear(int year) {
		set("c",Integer.toString(year));
	}
	public void setEndDay(int day) {
		set("e",Integer.toString(day));
	}
	public void setEndMonth(int month) {
		set("d",Integer.toString(month));
	}
	public void setEndYear(int year) {
		set("f",Integer.toString(year));
	}
	public void setType(Type t) {
		set("g",t.toString());
	}
}
