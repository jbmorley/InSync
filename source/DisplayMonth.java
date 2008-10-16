/*
 * DisplayMonth.java
 *
 * Copyright 2004, Jason Barrie Morley
 *
 * Test Class to print out an InSyncMonth object in html.
 *
 */
 
class DisplayMonth {
    
    public static void main(String[] args) {
        
        InSyncDay day = new InSyncDay( 1, 3, 2004 );
        
        // Add items to the day.
        day.addInSyncCalendarItem( new InSyncCalendarItem( 9, 0, 10, 0 ) );
        day.addInSyncCalendarItem( new InSyncCalendarItem( 10, 0, 11, 0 ) );
     
        // Add an few odd items.
        day.addInSyncCalendarItem( new InSyncCalendarItem( 9, 30, 10, 30 ) );
        day.addInSyncCalendarItem( new InSyncCalendarItem( 9, 0, 10, 35 ) );
        
        InSyncMonth month = new InSyncMonth( 3, 2004 );
        InSyncYear year = new InSyncYear( 2004 );

        printHeader( "InSync" );
        
        System.out.println( "<h1>Day View</h1>" );
        printDay( day );
        System.out.println( "<h1>Month View</h1>" );
        printMonth( month );
        System.out.println( "<h1>Year View</h1>" );
        printYear( year );
                        
        printFooter(); 
    }
    
    public static void printHeader( String title ) {
        // Print the Header.
        System.out.println( "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"DTD/xhtml1-transitional.dtd\">" );
        System.out.println( "<html>" );
        System.out.println( "<head>" );
        System.out.println( "<title>" + title + "</title>" );
        System.out.println( "<link rel=\"stylesheet\" href=\"css/insync.css\"/>" );
        System.out.println( "</head>" );  
        System.out.println( "<body>" );      
    }
    
    public static void printFooter() {
        // Print the Footer.
        System.out.println( "</body>" );
        System.out.println( "</html>" );
    }
    
    public static void printDay( InSyncDay day ) {
        
        // The Number of columms required.
        // TODO: This needs to be calculated by running through all the events during the day.
        int intNumberOfConflictingAppointments = 0;
    
        System.out.println( "<table class=\"day\">" );
            
        System.out.println( "<tr>" );
        System.out.println( "<th>" + day.getExtendedName() + "</th>" );
        System.out.println( "</tr>" );
        
        
        // Firstly, we need to count the maximum 'width' of appointments.
        int[] arrayHours = new int[ 24 ];
        // arrayHours.fill( 0 );
        int maximumWidth = 0;
        int startHour = 0;
        int endHour = 0;
        
        // Run through all of the appointments propagating the days.
        for( int i = 0; i < day.getNumberOfCalendarItems(); i++ ) {
            
            // Get the start and end times.
            startHour = day.getInSyncCalendarItem(i).getStartTimeHours();
            endHour = day.getInSyncCalendarItem(i).getEndTimeHours();
            
            // Correct for appointments which end on an hour.
            if ( day.getInSyncCalendarItem(i).getEndTimeMinutes() == 0 ) {
                endHour--;
            }
            
            // Now, increment the value stored in the Hours Array.
            for ( int j = startHour; j <= endHour; j++ ) {
                arrayHours[ j ]++;
                if ( arrayHours[ j ] > maximumWidth ) {
                    maximumWidth++;
                }
            }
        }

        // Next, run through printing out the table cells.
        
        // Array relating to a row in the table.
        // For each hour, this stores the end time of the current active item.
        InSyncCalendarItem[] arrayTableRow = new InSyncCalendarItem[ maximumWidth ];
        
        // Index of the appointment we have got up to.
        int intActiveAppointment = 0;
        
        for( int hour = 0; hour < 24; hour++ ) {
                
            // Get the current active appointments.
            
            // Where to start the search through the array.
            int intStartSearch = 0;
            
            while( ( intActiveAppointment < day.getNumberOfCalendarItems() ) 
                   && ( day.getInSyncCalendarItem(intActiveAppointment).getStartTimeHours() == hour ) ) {

                // Find the first available slot in the row array and add the item.
                for( int i = intStartSearch; i < arrayTableRow.length; i++ ) {

                    intStartSearch = i;

                    if ( arrayTableRow[ i ] == null ) {
                        arrayTableRow[ i ] = day.getInSyncCalendarItem( intActiveAppointment );
                        break;
                    } else if ( ( arrayTableRow[ i ].getEndTimeHours() < hour )
                                || ( arrayTableRow[ i ].getEndTimeHours() == hour)
                                && ( arrayTableRow[ i ].getEndTimeMinutes() == 0 ) ) {
                        arrayTableRow[ i ] = day.getInSyncCalendarItem( intActiveAppointment );
                        break;
                    }
                }
                
                intActiveAppointment++;
            }
            
            // Ensure that all completed items have been removed.
            for( int i = intStartSearch; i < arrayTableRow.length; i++ ) {
                if ( arrayTableRow[ i ] != null ) {
                        
                    if ( ( arrayTableRow[ i ].getEndTimeHours() < hour )
                         || ( arrayTableRow[ i ].getEndTimeHours() == hour)
                         && ( arrayTableRow[ i ].getEndTimeMinutes() == 0 ) ) {
                         
                        arrayTableRow[ i ] = null;
                         
                    }
                }
            }

            // Print the Row.
            printDayRow( hour, arrayTableRow );
            
            
        }
        
        System.out.println( "</table>" );
    }
    
    public static void printDayRow( int hour, InSyncCalendarItem[] arrayTableRow ) {

        // Generate the time representation.
        String stringHour = (new Integer( hour )).toString();
        
        // Zero extend the string.
        if ( stringHour.length() == 1 ) {
            stringHour = "0" + stringHour;
        }
        
        // Append the minutes.
        stringHour = stringHour + ":00";
        
        // Generate the string to represent the time of day.
        String stringHomeWork;
        if ( ( hour < 8 ) || ( hour > 16 ) ) {
            stringHomeWork = "home";
        } else {
            stringHomeWork = "work";
        } 

        // Output the table;
        System.out.println( "<tr>" );
        System.out.println( "<td class=\"" + stringHomeWork + "\">" + stringHour + "</td>" );
        
        // Boolean to indicated if we have already printed the cell in question.
        // This is used to allow colspan of non appointment cells.
        boolean alreadyPrintedBlank = false;
        
        // Print Each column, using colspans where appropriate.
        System.out.println( "<td class=\"" + stringHomeWork + "\">&nbsp</td>" );
        
        for( int i = 0; i < arrayTableRow.length; i++ ) {
        
            // Check to see if we are to print an appointment or not
            if ( ( arrayTableRow[ i ] == null ) && ( alreadyPrintedBlank == false ) ) {
        
                // Work out the required width of the blank cell by counting forwards.
                int intColspan = 0;
                
                for ( int j = i; j < arrayTableRow.length; j++ ) {
                    if ( arrayTableRow[ j ] == null ) {
                        intColspan++;
                    } else {
                        break;
                    }
                }
                
                String stringColspan = (new Integer( intColspan )).toString();
            
                System.out.println( "<td class=\"" + stringHomeWork + "\" colspan=\""
                                    + stringColspan + "\">&nbsp</td>" );
                alreadyPrintedBlank = true;
                
            } else if ( ( arrayTableRow[ i ] != null ) && ( arrayTableRow[ i ].getStartTimeHours() == hour ) ) {
            
                int intAppointmentLength = arrayTableRow[ i ].getEndTimeHours() - hour + 1;
                
                // Check to see if the end hour is misleading.
                if ( arrayTableRow[ i ].getEndTimeMinutes() == 0 ) {
                    intAppointmentLength--;
                }
                
                // Now output the appointment cell.
                String stringAppointmentLength = (new Integer( intAppointmentLength )).toString();
                System.out.println( "<td class=\"appointment_blue\" rowspan=\""
                                    + stringAppointmentLength +"\">&nbsp</td>" );
                
                alreadyPrintedBlank = false;
                
            } else if ( arrayTableRow[ i ] != null ) {
            
                // This else catches scenarios where we have padding of cells due to rowspan.
                alreadyPrintedBlank = false;               
                 
            }


        }
        
        System.out.println( "<td class=\"" + stringHomeWork + "\">&nbsp</td>" );
        
        
        System.out.println( "</tr>" );        

    }

    public static void printYear( InSyncYear year ) {
        
        // Essentially, for the moment I am going to use print month to print the months
        // and then customise it within CSS.
        System.out.println( "<table>" );
        
        int intMonthCounter = 1;
        
        for( int rows = 0; rows < 4; rows++ ) {
        
            System.out.println( "<tr>" );
        
            for( int cols = 0; cols < 3; cols++ ) {
                System.out.println( "<td>" );
                System.out.println( year.getMonth( intMonthCounter ).getName() );
                printMonth( year.getMonth( intMonthCounter ) );
                intMonthCounter++;
                System.out.println( "</td>" );
            }
            
            System.out.println( "</tr>" );
        }
        
        System.out.println( "</table>" );
        
    }
    
    public static void printMonth( InSyncMonth month ) {
      
        // Print out the table structure.
        System.out.println( "<table>" );
        
        // Counter for the days.
        int intDayCounter = 0;
        
        for( int rows = 0; rows < 7; rows++ ) {
            
            if ( rows == 0 ) {
                System.out.println( "<tr class=\"title\">" );
            } else {
                System.out.println( "<tr class=\"content\">" );
            }
            
            for ( int cols = 0; cols < 7; cols++ ) {
                
                // Print the Names of the Days.
                if ( rows == 0 ) {
                    System.out.println( "<td>" + DateFunctions.getDayString( cols ) + "</td>" );                    
                } else {
                    
                    // Increment the counter.
                    if ( (( cols == ( month.getStartDay() ) ) || ( intDayCounter > 0 ))
                         && ( intDayCounter < month.getNumberOfDays() ) ) {
                        intDayCounter++;
                        System.out.println( "<td>&nbsp;</td>" );                    
                        
                    } else {
                        System.out.println( "<td class=\"none\">&nbsp;</td>" );                    
                    }
                    
                }            
            }
            
            
            System.out.println( "</tr>" );
        }
        
        System.out.println( "</table>" );
        
    }
    
}