/*
 * InSyncDay.java
 *
 * Copyright 2004, Jason Barrie Morley
 *
 * Class for representing a day of events.
 *
 */
 
// Imports.
import java.util.*;
 
class InSyncDay {
    
    // Variables.
    protected int intDay;
    protected int intMonth;
    protected int intYear;
    
    // N.B. This ArrayList is always inherently sorted by date thanks to the
    // addInSyncCalendarItem method.
    protected ArrayList arrayInSyncCalendarItems = new ArrayList();
    
    // Constructors.
    
    public InSyncDay( int day, int month, int year ) {
        
        // Set the local variables.
        intDay = day;
        intMonth = month;
        intYear = year;
        
    }
    
    // Accessors.
    
    public String getExtendedName() {
        
        // String stringDay = DateFunctions.getDayString( int day );
        // String stringMonth = DateFunctions.get
        // DateFunctions.getMontString( intMonth )
        
        return ( "Day, Month, Year" );
    }
    
    public int getWidthOfConflictingItems() {
    
        return 1;
    }
    
    // Accessors for the InSyncCalendarItems.
    public InSyncCalendarItem getInSyncCalendarItem( int index ) {
        return (InSyncCalendarItem) arrayInSyncCalendarItems.get( index );
    }
    
    public int getNumberOfCalendarItems() {
        return arrayInSyncCalendarItems.size();
    }
    
    public void addInSyncCalendarItem( InSyncCalendarItem newItem ) {

        // Loop Variables.
        InSyncCalendarItem currentItem;
                        
        // Scan through all elements until we reach one which after the entry we are adding.
        // TODO: This is inherantly O(n) and could be optimised by using a binary split.
        for( int i = 0; i < arrayInSyncCalendarItems.size(); i++ ) {
            
            // Get the current item.
            currentItem = (InSyncCalendarItem) arrayInSyncCalendarItems.get( i );
          
            // Add the item at the first oportunity we have.
            if ( ( newItem.getStartTimeHours() < currentItem.getStartTimeHours() )
                 || ( ( newItem.getStartTimeHours() == currentItem.getStartTimeHours() )
                      && ( newItem.getStartTimeMinutes() <= currentItem.getStartTimeMinutes() ) )
                 ) {

                arrayInSyncCalendarItems.add( i, newItem );
                return;
            }
        }
        
        // If we have got here, then there are no items in the array or we have got to the end.
        arrayInSyncCalendarItems.add( newItem );
        
    }
    
    public void removeInSyncCalendarItem( int index ) {
        arrayInSyncCalendarItems.remove( index );
    }
    
    // Internal Methods.
    
}