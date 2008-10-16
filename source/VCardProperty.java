/*
 * VCardProperty.java
 *
 * Copyright 2004, Jason Barrie Morley
 *
 * A minimal class for storing VCard Properties.
 *
 */

// Regular Expression Imports
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class VCardProperty {
    
    // Constants.
    
    
    // Internal Variables.
    
    protected String propertyName;
    protected String[] propertyParameters;
    protected String[] propertyValues;
    
    // Store the grouping string assigned to this property.
    protected String propertyGrouping;
    
    // Additional Variables for handling Parameters.
    protected String propertyEncoding;
    protected String propertyCharacterSet;
    protected String propertyLanguage;
    protected String propertyValue;
    protected String propertyType;
    
    // Constructors.
    
    public VCardProperty () {}
        
    public VCardProperty ( String stringProperty ) {
        
        // Set the default values.
        propertyEncoding = "7BIT";
        propertyCharacterSet = "ASCII";
        propertyLanguage = "en-US";
        
        // This method takes a Property line as formatted in standard vCard format and parses it.   
        parseProperty( stringProperty );
        
        // Rescan the Parameters Array and set the fields appropriately.
        updateParameters();
        
    }
    
    // Accessors.
    
    // Name Accessors.
    public void setName ( String newName ) {
        propertyName = newName;
    }
    
    public String getName () {
        return propertyName;
    }
    
    // Paramter Accessors.
    public void setParameters ( String[] newParameters ) {
        propertyParameters = newParameters;
        
        // Update the fields to reflect these new parameters.
        updateParameters();
    }
    
    public String[] getParamters () {
        return propertyParameters;
    }
    
    // Value Accessors.
    public void setValues ( String[] newValues ) {
        propertyValues = newValues;
    }
    
    public String[] getValues () {
        return propertyValues;
    }
    
    public String getParameterEncoding() {
        return propertyEncoding;
    }
    
    public String getParameterCharacterSet() {
        return propertyCharacterSet;
    }
    
    public String getParameterLanguage() {
        return propertyLanguage;
    }
    
    public String getParameterValue() {
        return propertyValue;
    }
    
    public String getParameterType() {
        return propertyType;
    }
    
    public String getRepresentation() {
        String returnString;
        
        returnString = getName();
        
        returnString = returnString + ":";
        
        for( int i = 0; i < ( propertyValues.length - 1 ); i++ ) {
            returnString = returnString + propertyValues[ i ] + ";";
        }
        returnString = returnString + propertyValues[ propertyValues.length - 1 ];
     
        return returnString;
    }
    
    // Internal Methods.
    
    // Parses a Property String as would be found within a vCard.
    protected void parseProperty( String property ) {
        
        // Variables to be used within this method.
        int intColonPosition;
        int intSemicolonPosition;
        
        // Look for the first instance of the ':'.
        // TODO: Note we should probably check to see if the ':'has not been delimited.
        intColonPosition = property.indexOf(":");
        
        // Split the string at the colon.
        String stringNameParameters = property.substring( 0, intColonPosition );
        String stringValues = property.substring( intColonPosition + 1, property.length() );
        
        // Split the Property and Parameters.
        // If no ';' is found, then we assume that there are no parameters.
        // We do not need to check for delimited semi-colons at this point, as the first one
        // will always be the separator between the Property and Parameter.
        intSemicolonPosition = stringNameParameters.indexOf(";");
        
        String stringName;
        String stringParameters;
        
        if ( intSemicolonPosition == -1 ) {
            stringName = stringNameParameters;
            stringParameters = "";
        } else {
            stringName = stringNameParameters.substring( 0, intSemicolonPosition );
            stringParameters = stringNameParameters.substring( intSemicolonPosition + 1,
                                                              stringNameParameters.length() );
        }
        
        // Split the Parameters and the Values.
        String[] Parameters = splitAtSemicolons( stringParameters );
        String[] Values = splitAtSemicolons( stringValues );
        
        // Set the global fields to reflect these new values.
        propertyName = stringName;
        propertyParameters = Parameters;
        propertyValues = Values;
        
    }
    
    // Splits a string into an array of items at every non-delimited semicolon.
    public String[] splitAtSemicolons( String StringToSplit ) {
        
        // The Return String Array.
        String[] ReturnArray;
        
        // Firstly, check to see that the String is of non-zero length.
        if ( StringToSplit.length() == 0 ) {
            // Initialize the return array with zero elements.
            ReturnArray = new String[0];
            return ReturnArray;
        }
        
        // Initialise the counters.
        int IntSemicolonCount = 0;
        int IntStartingPosition = 0;
        int IntSemicolonPosition = StringToSplit.indexOf(";");
        
        // Count the number of Semicolons.
        while ( IntSemicolonPosition != -1 ) {
            IntStartingPosition = IntSemicolonPosition + 1;
            
            // Check to see if the semicolon has been delimited.
            if ( StringToSplit.indexOf( "\\" ) != ( IntSemicolonPosition - 1 ) ) {
                IntSemicolonCount ++;
            }
            IntSemicolonPosition = StringToSplit.indexOf( ";", IntStartingPosition );                
            
        }
        
        // Initialise the String Array.
        ReturnArray = new String[ IntSemicolonCount + 1 ];
        
        // Reset the counters.
        IntSemicolonPosition = StringToSplit.indexOf( ";" );
        IntSemicolonCount = 0;
        IntStartingPosition = 0;
        int IntBeginningOfString = 0;
        
        // Read the values into the array.
        while ( IntSemicolonPosition != -1 ) {
            
            // Check to see if the semicolon has been delimited.
            if ( StringToSplit.indexOf( "\\" ) != ( IntSemicolonPosition - 1 ) ) {
                ReturnArray[ IntSemicolonCount ] = StringToSplit.substring( IntBeginningOfString,
                                                                            IntSemicolonPosition);
                IntSemicolonCount ++;
                IntBeginningOfString = IntSemicolonPosition + 1;
            }
            
            IntStartingPosition = IntSemicolonPosition + 1;
            IntSemicolonPosition = StringToSplit.indexOf( ";", IntStartingPosition );
        }
        
        // Add the last string the the array.
        ReturnArray[ IntSemicolonCount ] = StringToSplit.substring( IntStartingPosition,
                                                                    StringToSplit.length() );
        
        // Finally, replace all the delimited semicolons with semicolons.
        for ( int i = 0; i < ReturnArray.length; i++ ) {
            ReturnArray[i] = ReturnArray[ i ].replaceAll( "\\\\;", ";" );
        }
        
        // Return the Array.
        return ReturnArray;
    }
    
    
    // Parses the list of properties and updates the internal property fields.
    protected void updateParameters() {
        
        Pattern patternEquals = Pattern.compile( "(.+)=(.+)" );
        String parameterName = "";
        String parameterValue = "";
        
        for( int i = 0; i < propertyParameters.length; i++ ) {
        
            Matcher matcherEquals = patternEquals.matcher( propertyParameters[ i ] );
            
            if ( matcherEquals.matches() ) {
            
                parameterName = matcherEquals.group( 1 );
                parameterValue = matcherEquals.group( 2 );
            
            } else {
            
                parameterValue = propertyParameters[ i ];
            
            }
                        
            if ( propertyName.equals( "ENCODING" ) ) {
                propertyEncoding = parameterValue;
            } else if ( propertyName.equals( "CHARSET" ) ) {
                propertyCharacterSet = parameterValue;
            } else if ( propertyName.equals( "LANGUAGE" ) ) {
                propertyLanguage = parameterValue;
            } else if ( propertyName.equals( "VALUE" ) ) {
                propertyValue = parameterValue;
            } else if ( propertyName.equals( "TYPE" )) {
                propertyType = parameterValue;
            } else {
                
                // System.out.println( "Unhandled: " + parameterName + "=" + parameterValue );

                // Catch all the type parameters.
                if ( parameterValue.equals( "DOM" )
                    || parameterValue.equals( "INTL" )
                    || parameterValue.equals( "POSTAL" )
                    || parameterValue.equals( "PARCEL" )
                    || parameterValue.equals( "HOME" )
                    || parameterValue.equals( "WORK" )
                    || parameterValue.equals( "GIF" )
                    || parameterValue.equals( "CGM" )
                    || parameterValue.equals( "WMF" )
                    || parameterValue.equals( "BMP" )
                    || parameterValue.equals( "MET" )
                    || parameterValue.equals( "PMB" )
                    || parameterValue.equals( "DIB" )
                    || parameterValue.equals( "PICT" )
                    || parameterValue.equals( "TIFF" )
                    || parameterValue.equals( "PS" )
                    || parameterValue.equals( "PDF" )
                    || parameterValue.equals( "JPEG" )
                    || parameterValue.equals( "MPEG" )
                    || parameterValue.equals( "MPEG2" )
                    || parameterValue.equals( "AVI" )
                    || parameterValue.equals( "QTIME" )
                    || parameterValue.equals( "PREF" )
                    || parameterValue.equals( "VOICE" )
                    || parameterValue.equals( "FAX" )
                    || parameterValue.equals( "MSG" )
                    || parameterValue.equals( "CELL" )
                    || parameterValue.equals( "PAGER" )
                    || parameterValue.equals( "BBS" )
                    || parameterValue.equals( "MODEM" )
                    || parameterValue.equals( "CAR" )
                    || parameterValue.equals( "ISDN" )
                    || parameterValue.equals( "VIDEO" ) ) {
    
                    // Add this type to the current list of types.
                    // TODO: This array should be cleared before we begin adding items.
                    propertyType = propertyValue;                  
                }           
                
            }
            
        }
        
    }
    
    
}