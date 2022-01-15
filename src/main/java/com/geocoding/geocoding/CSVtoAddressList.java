package com.geocoding.geocoding;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class CSVtoAddressList {
    // Fields
	private String errorMessage;

	private String [] addressesArray;

	private boolean isValid;

    // Constructor
    public CSVtoAddressList(String filepath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(filepath));
            String [] nextLine;
            ArrayList<String> addressesList = new ArrayList<String>();
            while ((nextLine = reader.readNext()) != null) {
               addressesList.add(nextLine[0] + ", " + nextLine[1] + ", " + nextLine[2] + ", " + nextLine[3]);
            }
            addressesArray = addressesList.toArray(String[]::new);
            
        }
        
        catch (IOException e) {
            isValid = false;
        }

        catch (CsvValidationException e) {
            errorMessage = "Please verify the format of your CSV file.";
        }
    }

	// Getters
    public String getErrorMessage(){
        return errorMessage;
    }

    public boolean isValid(){
        return isValid;
    }

    public String[] getAddresses(){
        return addressesArray;
    }

	}
