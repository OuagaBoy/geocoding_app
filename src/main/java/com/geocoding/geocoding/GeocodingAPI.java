package com.geocoding.geocoding;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class GeocodingAPI {
    // Fields
    private String resultJson;
	private String errorMessage;
	private boolean successful;

    // Constructor
    public GeocodingAPI(String address) {
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(EnvVariables.GoogleAPIKey)
                .build();
            GeocodingResult[] results =  GeocodingApi.geocode(context, address).await();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            resultJson = gson.toJson(results[0]);
            // Invoke .shutdown() after your application is done making requests
            context.shutdown();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        
        catch (ApiException e) {
            successful = false;
            e.printStackTrace();
        }

        catch (InterruptedException e) {
        e.printStackTrace();
        }
        
    }

	// Getters
    public String getErrorMessage(){
        return errorMessage;
    }

    public String resultJson(){
        return resultJson;
    }

    public boolean successful(){
        return successful;
    }

	}
