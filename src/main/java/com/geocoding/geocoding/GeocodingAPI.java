package com.geocoding.geocoding;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class GeocodingAPI {
    // Fields
    private String resultJson;
	private String errorMessage;
	private boolean successful;
    private String lng;
    private String lat;

    // Constructor
    public GeocodingAPI(String address) {
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(EnvVariables.GoogleAPIKey)
                .build();
            GeocodingResult[] results =  GeocodingApi.geocode(context, address).await();
            Gson gson = new GsonBuilder().create();
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

    public void jsonParser(String Json) {
        JsonElement jsonElement = JsonParser.parseString(Json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject = jsonObject.getAsJsonObject("geometry");
        jsonObject = jsonObject.getAsJsonObject("location");
        lat = jsonObject.get("lat").getAsString();
        lng = jsonObject.get("lng").getAsString();
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

    public String getLat(){
        return lat;
    }

    public String getLng(){
        return lng;
    }

	}
