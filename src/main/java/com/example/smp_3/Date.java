package com.example.smp_3;

import java.util.Calendar;

/**
 * The Date class is the primary class that stores the inputted date
 * Date(String date){}:
 * This method takes the string input of the date (ex:6/12/2023) and converts it into an int
 * isValid();
 * This method checks if the date is a valid date
 * checkIfDateIsCorrect();
 * This method determines if the date is correct according to it being a leap year
 * checkDate();
 * this method checks if the date is a valid date
 * date();
 * contsructor for the year, month, day
 * getYear(), getMonth(), getDay()
 * getter methods for year, month and day
 * equals()
 * check if the date is equal to the object date
 * compareTo()
 * compares the private date to the Date date
 *
 * @author Judah Farkas, David Rahabi
 */
public class Date implements Comparable<Date> { // add comparable method
    private int year;
    private int month;
    private int day;
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;


    /**
     * Constructor for the date class
     *
     * @param month month
     * @param day   day
     * @param year  year
     */
    Date(int month, int day, int year) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Constructor that splits the given date from string into ints
     * input: 6/12/23
     * month = 6
     * day = 12
     * year= 2023
     *
     * @param date string with the date
     */
    Date(String date) {
        String[] splitD = date.split("/");
        this.month = Integer.parseInt(splitD[0]);
        this.day = Integer.parseInt(splitD[1]);
        this.year = Integer.parseInt(splitD[2]);
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Checks if date is a valid calendar date
     *
     * @return true, if date is invalid return false
     */
    public boolean isValid() {
        if (!checkLeap() || !checkDate(this.month, this.day, this.year)) {
            return false;
        }
        return true;
    }

    /**
     * Calculates the age based on the date of birth
     *
     * @return age the person's age in years
     */
    public int getAge() {
        Calendar today = Calendar.getInstance();
        int currentMonth = today.get(Calendar.MONTH);
        int currentDay = today.get(Calendar.DAY_OF_MONTH);
        int currentYear = today.get(Calendar.YEAR);

        int age = currentYear - this.year;
        if (currentMonth + 1 < this.month || (currentMonth + 1 == this.month && currentDay < this.day)) {
            age--;
        }
        return age;
    }

    /**
     * An extension to the isValid method, checks if the date is valid date as well as a leap year
     *
     * @return true if date is valid, false if invalid date or leap year
     */
    public boolean checkLeap() {
        switch (this.month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if (this.day >= 1 && this.day <= 31) {
                    break;
                }
                System.out.println("DOB invalid: " + this.month + "/" + this.day + "/" + this.year + " not a valid calendar date!");
                return false;
            case 4:
            case 6:
            case 9:
            case 11:
                if (this.day >= 1 && this.day <= 30) {
                    break;
                }
                System.out.println("DOB invalid: " + this.month + "/" + this.day + "/" + this.year + " not a valid calendar date!");
                return false;
            case 2:
                if (this.year % QUADRENNIAL == 0) {
                    if (this.year % CENTENNIAL == 0) {
                        if (this.year % QUATERCENTENNIAL == 0) {
                            if (this.day <= 29 && this.day >= 1) {
                            }
                        } else if (this.day <= 28 && this.day >= 1) {
                        }
                    } else if (this.day <= 29 && this.day >= 1) {
                    }
                } else if (this.day <= 28 && this.day >= 1) {
                } else {
                    System.out.println("DOB invalid: " + this.month + "/" + this.day + "/" + this.year + " not a valid calendar date!");
                    return false;
                }
        }
        return true;
    }

    /**
     * Checks if the Date is a valid date
     *
     * @param month month
     * @param day   day
     * @param year  year
     * @return true if valid, false if not
     */
    public boolean checkDate(int month, int day, int year) {
        if ((this.month > 12) || (this.day > 31) || this.month < 1 || this.day < 1) {
            System.out.println("DOB invalid: " + this.month + "/" + this.day + "/" + this.year + " not a valid calendar date!");
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        int calMonth = calendar.get(Calendar.MONTH);
        int calDay = calendar.get(Calendar.DAY_OF_MONTH) + 1;
        int calYear = calendar.get(Calendar.YEAR);

        if ((this.year == calYear && this.month == calMonth && this.day == calDay) ||
                ((this.year > calYear) || (this.year == calYear && this.month > calMonth) ||
                        (this.year == calYear && this.month == calMonth && this.day > calDay))) {
            System.out.println("DOB invalid: " + this.month + "/" + this.day + "/" + this.year + " cannot be today or a future day.");
            return false;
        }
        return true;
    }


    /**
     * Overide function for equals
     * checks if year, month, and day are equal
     *
     * @param obj date object
     * @return true if equal, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date date = (Date) obj;

            return date.year == this.year && date.month == this.month && date.day == this.day;

        }
        return false;
    }

    /**
     * Overide function to compare dates
     *
     * @param date the object to be compared.
     * @return 0 if equal, 1 if year, month, or day is >, -1 otherwise
     */
    @Override
    public int compareTo(Date date) {
        if (this.year > date.year)
            return 1; // compare years first
        else if (date.year > this.year)
            return -1;
        else {// if they are equal
            if (this.month > date.month)
                return 1; // compare months
            else if (date.month > this.month)
                return -1;
            else { // if months are equal
                if (this.day > date.day)
                    return 1; // compare days
                else if (date.day > this.day)
                    return -1;
                else
                    return 0; // if days are equal
            }
        }
    }
}


//
//   /**
//    * Test case main
//    *
//    * @param args
//    */
//   public static void main(String[] args) {
//       testDaysInFeb_NonLeap();
//       testDaysInFeb_Leap();
//       testCorrectDate();
//   }
//
//  /**
//   * Test Case #1
//   * Tests if February is not a leap year
//   */
//  private static void testDaysInFeb_NonLeap() {
//      Date date = new Date(2, 29, 2011);
//      boolean expectedOutput = false;
//      boolean actualOutput = date.checkLeap();
//      System.out.println("**Test case #1: # of days in Feb. in a non-leap year is 28");
//      testResult(date, expectedOutput, actualOutput);
//  }
//
//  /**
//   * Test Case 2
//   * Tests if february is a leap year
//   */
//  public static void testDaysInFeb_Leap() {
//      Date date = new Date(2, 29, 2012);
//      boolean expectedOutput = true;
//      boolean actualOutput = date.checkLeap();
//      System.out.println("**Test case #2: # of days in Feb. in a non-leap year is 29");
//      testResult(date, expectedOutput, actualOutput);
//  }
//
//  /**
//   * Test case 3
//   * Tests is date is correct
//   */
//  public static void testCorrectDate() {
//      Date date = new Date(4, 29, 2023);
//      boolean expectedOutput = false;
//      boolean actualOutput = date.checkDate(date.month, date.day, date.year);
//      System.out.println("**Test case #3: is the date after today");
//      testResult(date, expectedOutput, actualOutput);
//  }
//
//  /**
//   * Test result method
//   *
//   * @param date           date
//   * @param expectedOutput Expected output
//   * @param actualOutput   Actual output
//   */
//  public static void testResult(Date date, boolean expectedOutput, boolean actualOutput) {
//      if (expectedOutput == actualOutput) {
//          System.out.println("PASS");
//      } else {
//          System.out.println("FAIL");
//      }
//  }
//