package com.demo.BankingApp.utils;

import java.time.*;


public class AccountUtils {

    /*
      currentYear + randomSixNumbers
     */


    public static String generateAccountNo() {


      Year year = Year.now();

      int min = 100000;
      int max = 999999;

        int readNo = (int) Math.floor(Math.random() * ((max - min + 1) + min));

        String yr = String.valueOf(year);
        String numberStr =String.valueOf(readNo);

        return yr + numberStr;
    }
}
