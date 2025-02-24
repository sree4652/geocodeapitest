package org.example.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class GeoCodeApiUtil {
    private static final String API_KEY = "f897a99d971b5eef57be6fafa0d83239";
    private static final String BASE_URL_ZIP = "http://api.openweathermap.org/geo/1.0/zip";
    private static final String BASE_URL_CITY = "http://api.openweathermap.org/geo/1.0/direct";
    public static final String ANSI_RED = "\033[0;31m";
    JsonUtil jsonUtil = new JsonUtil();
    Map<String,Object> responseMap;



    public Map<String,Object> getCoordinatesByZipCode(String zipcode) {

        responseMap = new HashMap<>();

        Response response = RestAssured.given()
                .queryParam("appid", API_KEY)
                .queryParam("zip", zipcode)
                .when()
                .get(BASE_URL_ZIP)
                .then()
                .extract().response();
        if(response.getStatusCode()==200){
            responseMap.put("latitude",response.path("lat"));
            responseMap.put("longitude",response.path("lon"));
            responseMap.put("name",response.path("name"));
            responseMap.put("country",response.path("country"));
        }else if(response.getStatusCode()==404){
           System.out.println(ANSI_RED +"There seems to be no place with this zipcode "+zipcode+" in USA");
           responseMap=null;
        }


        return responseMap;
    }

    public Map<String,Object> getCoordinatesByLocation(String cityName) {
        responseMap = new HashMap<>();
        Response response = RestAssured.given()
                .queryParam("appid", API_KEY)
                .queryParam("q", cityName)
                .queryParam("limit",1)
                .when()
                .get(BASE_URL_CITY)
                .then()
                .extract().response();

        JSONArray jsonArray = new JSONArray(jsonUtil.filterByCountry(new JSONArray(response.getBody().asPrettyString()),"US"));

        if(response.getStatusCode()==200 && !jsonArray.isEmpty()){
            responseMap.put("latitude",jsonArray.getJSONObject(0).getBigDecimal("lat"));
            responseMap.put("longitude",jsonArray.getJSONObject(0).getBigDecimal("lon"));
            responseMap.put("name",jsonArray.getJSONObject(0).getString("name"));
            responseMap.put("state",jsonArray.getJSONObject(0).getString("state"));
            responseMap.put("country",jsonArray.getJSONObject(0).getString("country"));
        }else if(response.getStatusCode()==404 || jsonArray.isEmpty()) {
            System.out.println(ANSI_RED +"There seems to be no place with this name "+cityName+" in USA");
            responseMap=null;
        }


        return responseMap;
    }

}
