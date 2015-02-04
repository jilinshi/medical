package com.medical.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Idcard {
	private static int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
	private static int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
	private static int[] ai = new int[18];

	// 验证15位18位
	public static boolean Verify(String idcard) {
		if (idcard.length() == 15) {
			idcard = uptoeighteen(idcard);
		}
		if (idcard.length() != 18) {
			return false;
		}
		String verify = idcard.substring(17, 18);
		if (verify.equals(getVerify(idcard))) {
			return true;
		}
		return false;
	}// 得到最后地位校验码

	public static String getVerify(String eightcardid) {
		int remaining = 0;
		if (eightcardid.length() == 18) {
			eightcardid = eightcardid.substring(0, 17);
		}
		if (eightcardid.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eightcardid.substring(i, i + 1);
				ai[i] = Integer.parseInt(k);
			}
			for (int i = 0; i < 17; i++) {
				sum = sum + wi[i] * (ai[i]);
			}
			remaining = sum % 11;
		}
		return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
	}

	// 15转18位
	public static String uptoeighteen(String fifteencardid) {
		String eightcardid = fifteencardid.substring(0, 6);
		eightcardid = eightcardid + "19";
		eightcardid = eightcardid + fifteencardid.substring(6, 15);
		eightcardid = eightcardid + getVerify(eightcardid);
		return eightcardid;
	}
	
	public static String getBirthday(String idcard){
		// 获取出生日期   
        String birthday = idcard.substring(6, 14);   
        Date birthdate;
        int year = 0;
        int month = 0;
        int day = 0;
		try {
			birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
	        birthday = birthdate.toString();
	        GregorianCalendar currentDay = new GregorianCalendar();   
	        currentDay.setTime(birthdate);
	        year = currentDay.get(Calendar.YEAR);
	        month = currentDay.get(Calendar.MONTH) + 1;  
	        day = currentDay.get(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		birthday = year +"-"+ month + "-" + day;
		return birthday;
	}

	public static void main(String[] args) {
		System.out.println(Idcard.uptoeighteen("220221800906051"));
		System.out.println(Idcard.getBirthday("220221198009060510"));
	}
}
