package com.brms.client;

import java.time.LocalTime;

public class DateTimeTest {

	public static void main(String[] a){
		
		System.out.println( java.time.temporal.ChronoUnit.HOURS.between( LocalTime.of(15,0,0)  ,  LocalTime.of(17,0,0) ) );
		
	}
}
