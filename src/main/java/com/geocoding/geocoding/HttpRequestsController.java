package com.geocoding.geocoding;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HttpRequestsController {
    @Autowired
    private HttpFileService fileService;
    @Autowired
    private GeocodingDatabaseRepository database;
    
    private String[] rawAddresses;


    @GetMapping("/")
	public String home(Model model) {
        model.addAttribute("viewMessage", "Please upload a .csv file!");
		return "index";
	}

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            fileService.save(file);
            String filepath = fileService.getuploadPath() + fileService.getfileName();
            CSVtoAddressList addressList = new CSVtoAddressList(filepath);
            addressList.readCSVFiletoArray(filepath);
            model.addAttribute("viewMessage", addressList.getErrorMessage());
            ArrayList<String> mapCoordinatesList = new ArrayList<String>();  
            String[] addressesArray = addressList.getAddresses();
            rawAddresses = addressList.getRawAddresses();
            rawAddresses[0] = rawAddresses[0] + ",lat" + ",lng";
            for (Integer i=1; i<addressesArray.length; i++) {
                // query database for existing geocoded addresses

                // if not in database make api call and save result
                GeocodingAPI APICall = new GeocodingAPI(addressesArray[i]);
                String geocodedAddress = APICall.resultJson();
                GeocodingDatabase newGeocodedAddress = new GeocodingDatabase(geocodedAddress);
                database.save(newGeocodedAddress);
                // retrieve results 
                APICall.jsonParser(geocodedAddress);
                mapCoordinatesList.add(APICall.getLat() + "," + APICall.getLng());
                rawAddresses[i] = rawAddresses[i] + "," + APICall.getLat() + "," + APICall.getLng();
            }
            String [] mapCoordinates = mapCoordinatesList.toArray(String[]::new);
            model.addAttribute("mapCoordinates", mapCoordinates);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "index";
        }
    }

    @GetMapping("/upload")
    public ResponseEntity<Resource> downloadFile(Model model) {
        String resultFilePath = fileService.getuploadPath() + fileService.getfileName() + "geocoded_addresses.csv";
        try {
        CSVtoAddressList.writeCSVtoResultFile(rawAddresses, resultFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileService.download(resultFilePath);
    }

}