/*
 * InSyncYear.java
 *
 * Copyright 2004, Jason Barrie Morley
 *
 * Class for representing a year of events.
 * Internally, this uses nested months and days.
 *
 */
 
class InSyncYear {
    
    // Variables.
    InSyncMonth[] inSyncMonths;
    int intYear;
    
    
    // Constructors.
    public InSyncYear( int year ) {
        
        // Intialise the internal variables.
        intYear = year;
        inSyncMonths = new InSyncMonth[ 12 ];
        
        // Get the days for each month.
        for( int i = 0; i < inSyncMonths.length; i++ ) {
            inSyncMonths[ i ] = new InSyncMonth( i + 1, year );
        }
        
    }
    
    
    // Accessors.
    public int getYear() {
        return intYear;
    }
    
    public InSyncMonth getMonth( int month ) {
        return inSyncMonths[ month - 1 ];
    }
    
    // Internal Methods.
    
}