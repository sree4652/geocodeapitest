package org.example;

import org.example.utils.GeoCodeApiUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {


        System.out.println("Hello, Welcome to Geocode api test project");
        System.out.println("Please enter a city or zipcode or combination");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        scanner.close();

        userInputGeoCodes(name);
    }

    public static void userInputGeoCodes(String input) {
        GeoCodeApiUtil geoCodeApiUtil = new GeoCodeApiUtil();
        if (input != null && !input.isEmpty()) {
            List<String> result = new ArrayList<>();
            if (input.contains("“") && input.contains("”")) {
                String[] parts = input.split("“|”");
                for (String part : parts) {
                    if (!part.trim().isEmpty()) {
                        result.add(part.trim());
                    }
                }

               for (String locOrZip : result){
                   Map<String,Object> responseMap;
                  if(locOrZip.matches(".*\\d.*")) {
                     if(locOrZip.length()==4){
                         responseMap = geoCodeApiUtil.getCoordinatesByZipCode("0"+locOrZip);
                     }else{
                         responseMap = geoCodeApiUtil.getCoordinatesByZipCode(locOrZip);
                     }
                     if(!responseMap.get("country").toString().equalsIgnoreCase("US")){
                         System.out.println("The entered zipcode "+ locOrZip + " is not a US zipcode");
                     }else{
                         System.out.println("Latitude of the zipcode "+ locOrZip + " is : "+responseMap.get("latitude").toString());
                         System.out.println("Longitude of the zipcode "+ locOrZip + " is : "+responseMap.get("longitude").toString());
                         System.out.println("Name of the city for zipcode "+ locOrZip + " is : "+responseMap.get("name").toString());
                     }

                  } else {
                      String cityname = locOrZip.split(",")[0].trim();
                      responseMap = geoCodeApiUtil.getCoordinatesByLocation(cityname);
                      System.out.println("Latitude of the city "+ cityname + " is : "+responseMap.get("latitude").toString());
                      System.out.println("Longitude of the city "+ cityname + " is : "+responseMap.get("longitude").toString());
                  }
               }
            }

        }

    }
}