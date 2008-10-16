/*
 * InSyncMonth.java
 *
 * Copyright 2004, Jason Barrie Morley
 *
 * Class for representing a month of events.
 *
 */
 
class InSyncMonth {
    
    // Variables.
    int intNumberOfDays;
    int intStartDay;
    int intMonth;
    int intYear;
    
    // Constructors.
    public InSyncMonth( int month, int year ) {
        
        // Initalise the local variables.
        intMonth = month;
        intYear = year;
        
        // Initialise the Calendar Item Array.
        intNumberOfDays = DateFunctions.getNumberOfDays( month, year );
        intStartDay = DateFunctions.getDayOfWeek( 1, month, year );
        
    }
    
    // Accessors.
    public int getNumberOfDays() {
        return intNumberOfDays;
    }
    
    public int getStartDay() {
        return intStartDay;
    }
    
    public int getMonth() {
        return intMonth;
    }
    
    public int getYear() {
        return intYear;
    }
    
    public String getName() {
        return DateFunctions.getMonthString( intMonth );
    }
    
    // Internal Methods.

    
}