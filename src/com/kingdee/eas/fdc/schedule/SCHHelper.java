package com.kingdee.eas.fdc.schedule;

public class SCHHelper {
	public static String getFixLenNum(int length,int num){
		char[] zeroChars = new char[length];
		for(int i = 0;i<length;i++){
			zeroChars[i] = '0';
		}
		String fixNum = new String(zeroChars);
		String numStr = Integer.toString(num) ;
		fixNum = fixNum.concat(numStr);
		fixNum = fixNum.substring(numStr.length());
		return fixNum;
	}
}
