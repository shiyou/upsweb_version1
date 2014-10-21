package com.eshore.upsweb.util;

import java.util.regex.Pattern;

public class StringUtil {
	
	public static boolean isNumber(String str){
		for(int i=str.length();--i>=0;){
			int chr = str.charAt(i);
			if(chr<48 || chr>57){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNumber2(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	
	

}
