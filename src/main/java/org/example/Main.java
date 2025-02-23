package org.example;

import org.example.utils.GeoCodeApiUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static final String ANSI_RED = "\033[0;31m";
    public static final String ANSI_BLUE = "\033[0;34m";
    public static final String ANSI_YELLOW = "\033[0;33m";

    public static void main(String[] args) {


        System.out.println(ANSI_YELLOW+"Hello, Welcome to Geocode api test project.");
        System.out.println(ANSI_YELLOW+"Please enter a city or zipcode or combination");
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
            } else if (input.contains("\"")) {
                String[] parts = input.split("\" \"");
                for (String part : parts) {
                    part = part.replace("\"", "");
                    result.add(part.trim());

                }
            }else if(input.contains(",") && !input.contains("\"") && !input.contains("“")){
                String[] parts = input.split(",");
                for (String part : parts) {
                    result.add(part.trim());

                }
            }else{
                result.add(input);
            }

            for (String locOrZip : result) {
                Map<String, Object> responseMap;
                if (locOrZip.matches(".*\\d.*")) {
                    if (locOrZip.length() == 5) {
                        responseMap = geoCodeApiUtil.getCoordinatesByZipCode(locOrZip);
                    } else {
                        System.out.println(ANSI_RED + "The entered zipcode " + locOrZip + " is not a 5 digit one. please make sure to enter a 5 digit zipcode");
                        responseMap = null;
                    }
                    if(responseMap != null && !responseMap.isEmpty()){
                        if (!responseMap.get("country").toString().equalsIgnoreCase("US")) {
                            System.out.println(ANSI_RED+"The entered zipcode " + locOrZip + " is not a US zipcode");
                        } else {
                            System.out.println(ANSI_BLUE+"Latitude of the zipcode " + locOrZip + " is : " + responseMap.get("latitude").toString());
                            System.out.println("Longitude of the zipcode " + locOrZip + " is : " + responseMap.get("longitude").toString());
                            System.out.println("Name of the city for the zipcode " + locOrZip + " is : " + responseMap.get("name").toString());
                            System.out.println("Name of the country where the zipcode " + locOrZip + " located is : " + responseMap.get("country").toString() + "\n");
                        }
                    }else{
                        System.out.println(ANSI_RED + "No data found for zipcode : " + locOrZip+"\n");
                    }


                } else {
                        String cityname = locOrZip.split(",")[0].trim();
                        responseMap = geoCodeApiUtil.getCoordinatesByLocation(cityname);
                    if(responseMap != null && !responseMap.isEmpty()) {
                        System.out.println(ANSI_BLUE+"Latitude of the city " + cityname + " is : " + responseMap.get("latitude").toString());
                        System.out.println("Longitude of the city " + cityname + " is : " + responseMap.get("longitude").toString());
                        System.out.println("State where the city " + cityname + " is located : " + responseMap.get("state").toString());
                        System.out.println("Country where the city " + cityname + " is located : " + responseMap.get("country").toString() + "\n");
                    }else{
                        System.out.println(ANSI_RED + "No data found for cityname : " + locOrZip+"\n");
                    }
                }
            }


        } else {
            System.out.println("Looks like you didn't enter any value. Please run again and enter a valid input");
        }

    }
}