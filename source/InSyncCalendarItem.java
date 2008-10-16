/*
 * InSyncCalendarItem.java
 *
 * Copyright 2004, Jason Barrie Morley
 *
 * description
 *
 */

class InSyncCalendarItem {

    // Variables.
    int intStartTimeHours;
    int intStartTimeMinutes;
    int intEndTimeHours;
    int intEndTimeMinutes;
            
    // Constructors.
    public InSyncCalendarItem( int startHours, int startMinutes, int endHours, int endMinutes ) {
        
        // Set the class variables.
        intStartTimeHours = startHours;
        intStartTimeMinutes = startMinutes;
        intEndTimeHours = endHours;
        intEndTimeMinutes = endMinutes;
        
    }
    
    // Accessors.
    
    // Start Time Accessors.
    public void setStartTime( int hours, int minutes ) {
        // TODO: We need some sort of bounds checking to ensure that the start time isn't
        // after the end time.
        int intStartTimeHours = hours;
        int intStartTimeMinutes = minutes;
    }
    
    public int getStartTimeHours() {
        return intStartTimeHours;
    }
    
    public int getStartTimeMinutes() {
        return intStartTimeMinutes;
    }
    
    // End Time Accessors.
    public void setEndTime( int hours, int minutes ) {
        // TODO: Again, we need some sort of bounds checking here.
        int intEndTimeHours = hours;
        int intEndTimeMinutes = minutes;
    }
    
    public int getEndTimeHours() {
        return intEndTimeHours;
    }
    
    public int getEndTimeMinutes() {
        return intEndTimeMinutes;
    }    
    
    // Internal Methods.
          
      
}