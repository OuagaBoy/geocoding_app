package com.geocoding.geocoding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;


public class CSVtoAddressList {

    private final HttpFileService fileService = new HttpFileService();
    // Fields
	private String errorMessage;

	private String [] addressesArray;

	private boolean isValid;

    private String[] rawAddresses;

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
            errorMessage = "Please upload a valid CSV file!";
        }
    }

    public static void writeCSVtoResultFile(String[] geocodedAddressesArray, String resultFilePath) {
        try {
            for (Integer i =0; i<geocodedAddressesArray.length; i++){
                BufferedWriter bw = new BufferedWriter(new FileWriter(resultFilePath, true));
                String line = geocodedAddressesArray[i];
                bw.write(line);
                bw.newLine();
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCSVFiletoArray(String filepath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            List<String> list = new ArrayList<String>();
            while((line = br.readLine()) != null){
                list.add(line);
            }
            br.close();
            rawAddresses = list.toArray(new String[0]); 
        } catch (Exception e) {
            e.printStackTrace();
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

    public String[] getRawAddresses(){
        return rawAddresses;
    }

	}
