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
        }else{
           System.out.println("The entered zipcode "+zipcode+" gave a 404 value from api");
           throw new RuntimeException("zipcode api gave a 404 response");
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
        responseMap.put("latitude",jsonArray.getJSONObject(0).getBigDecimal("lat"));
        responseMap.put("longitude",jsonArray.getJSONObject(0).getBigDecimal("lon"));
        responseMap.put("name",jsonArray.getJSONObject(0).getString("name"));

        return responseMap;
    }

}
