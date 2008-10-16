/*
 * InSyncVCardHandler.java
 *
 * Copyright 2004, Jason Barrie Morley
 *
 * description
 *
 */

class InSyncVCardHandler {

    // Variables.
    
    // The Formatted Name (optional).
    String vCardFN = "";
    
    // The Name.
    String vCardNFamily = "Morley";
    String vCardNGiven = "Jason";
    String vCardNAdditional = "Barrie";
    String vCardNPrefix = "Mr.";
    String vCardNSuffix = "BA";
    
    // The Photograph (optional).
    String vCardPhoto = "";
    
    // The BirthDate (optional).
    String vCardBirthdate = "19821228";
    
    // The Delivery Address (optional).
    
    // Domestic Address Information.
    String vCardAdrDomPostOffice = "";
    String vCardAdrDomExtended = "";
    String vCardAdrDomStreet = "";
    String vCardAdrDomLocality = "";
    String vCardAdrDomRegion = "";
    String vCardAdrDomPostalCode = "";
    String vCardAdrDomCountry = "";

    // Entry Method.

    public static void main( String[] args ) {

	// Instantiate a new InSyncVCardHandler
	InSyncVCardHandler vCardHandler = new InSyncVCardHandler();
	
        String vCardPropertyString
            = "ADR;DOM;HOME:P.O. Box 101;Suite 101;123 Main Street;Any Town;CA;91921-1234;";

        System.out.println( vCardPropertyString );

        VCardProperty vCardProperty = new VCardProperty( vCardPropertyString );

        // Extract the information from the vCardProperty.
        System.out.println( "vCard Name:" );
        System.out.println( "  " + vCardProperty.getName() );
        System.out.println( "vCard Parameters:" );
        System.out.println( "  Encoding:      " + vCardProperty.getParameterEncoding() );
        System.out.println( "  Character Set: " + vCardProperty.getParameterCharacterSet() );
        System.out.println( "  Language:      " + vCardProperty.getParameterLanguage() );
        System.out.println( "  Value:         " + vCardProperty.getParameterValue() );
        System.out.println( "  Type:          " + vCardProperty.getParameterType() );
        System.out.println( "vCard Values:" );
        String[] values = vCardProperty.getValues();
        for ( int i = 0; i < values.length; i++ ) {
            System.out.println( "  " + values[ i ] );
        }
        
        System.out.println( "" );
        System.out.println( vCardProperty.getRepresentation() );
    }

    // Constructors.

    public InSyncVCardHandler() {}

    // Accessors.
    
    public void getInSyncCalendarItem() {}

    public void updateInSyncCalendarItem( InSyncCalendarItem calendarItem ) {}
    
    public void getRepresentation() {
        
        // Output the vCard.
        
        // Print the header.
        System.out.println("BEGIN:VCARD");
        
        // Print the footer.
        System.out.println("END:VCARD");
    }
    
    // Internal Functions.
    
    public void setLocalFields( String name, String[] parameters, String[] values ) {
        
        // Huge and horrid function for checking the name off against all known possible names
        // and handling the other parameters as appropriate.
        
        if ( name.equals("") ) {
            
            // Formatted Name.
            // Since this is optional, it will not be handled to begin with.
            printError( name );
            
        } else if ( name.equals("N") ) {
        
            // Name.
            vCardNFamily = values[0];
            vCardNGiven = values[1];
            vCardNAdditional = values[2];
            vCardNPrefix = values[3];
            vCardNSuffix = values[4];
            
        } else if ( name.equals("PHOTO") ) {
        
            // Photo.
            // Since this is optional, we are ignoring it for the time being.
            printError( name );
            
        } else if ( name.equals("") ) {
            
            // Birthdate.
            vCardBirthdate = name;
                    
        } else {
            printError( name );
        }
        
    }
    
    public void printError( String ParameterName ) {
        
        // Not Caught - Print out the line to warn us.
        System.err.println( "Parsing vCard: Unhandled Parameter (" + ParameterName + ")" );        
        
    }

}