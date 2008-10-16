/*
 * DateFunctions.java
 *
 * Copyright 2004, Jason Barrie Morley
 *
 * Utility class to provide some basic date functions.
 *
 */

class DateFunctions {
    
    public static void main( String[] args ) {
        
        int intNumberOfDays = getNumberOfDays( 2, 2000 );
        System.out.println( (new Integer( intNumberOfDays )).toString() );
        
        if ( isLeapYear( 2004 ) ) {
            System.out.println( "Leap Year" );
        } else {
            System.out.println( "Normal Year" );
        }
        
        getDayOfWeek( 22, 2, 2004 );
        
    }
    
    public static int getNumberOfDays( int month, int year ) {
        switch( month ) {
            case 1:
                return 31;
            case 2:
                if ( isLeapYear( year ) ) {
                    return 29;
                } else {
                    return 28;
                }
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
        }
        // We were not passed a valid month, therefore we return -1.
        return -1;
    }
    
    // Return true if year refers to a leap year.
    // year is represented by 4 digits.
    public static boolean isLeapYear( int year ) {
        if ( ( (( year % 4 ) == 0 ) && (( year % 100 ) != 0 )) || (( year % 400 ) == 0 ) ) {
            return true;
        } else {
            return false;
        }
    }
    
    // Returns the day of the week (0-6) of a particular date.
    public static int getDayOfWeek( int day, int month, int year ) {
    
        int intDaysFrom = 0;
    
        if ( year < 1960 ) {
            return -1;
        }
        
        // Add the years.
        for( int i = 1960; i < year; i++ ) {
            if ( isLeapYear( i ) ) {
                intDaysFrom = intDaysFrom + 366;
            } else {
                intDaysFrom = intDaysFrom + 365;
            }
        }
        
        // Add the months.
        for( int i = 1; i < month; i++ ) {
            intDaysFrom = intDaysFrom + getNumberOfDays( i, year );
        }
        
        // Add the days.
        intDaysFrom = intDaysFrom + day;
        
        // Correct.
        intDaysFrom = intDaysFrom + 4;
        
        // Divide By Seven.
        int intDayOfWeek = ( intDaysFrom % 7 );
        
        return intDayOfWeek;

    }
    
    public static String getDayString( int day ) {
        switch( day % 7 ) {
            case 0: return "Sun";
            case 1: return "Mon";
            case 2: return "Tue";
            case 3: return "Wed";
            case 4: return "Thu";
            case 5: return "Fri";
            case 6: return "Sat";
        }
        return (new String());
    }
    
    public static String getMonthString( int month ) {
        switch( ( month - 1 ) % 12 ) {
            case 0: return "January";
            case 1: return "February";
            case 2: return "March";
            case 3: return "April";
            case 4: return "May";
            case 5: return "June";
            case 6: return "July";
            case 7: return "August";
            case 8: return "September";
            case 9: return "October";
            case 10: return "November";
            case 11: return "December";
        }
        return (new String());
    }
}