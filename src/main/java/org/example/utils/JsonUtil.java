package org.example.utils;

import org.json.JSONArray;
import org.json.JSONObject;
public class JsonUtil {

    public JSONArray filterByCountry(JSONArray jsonArray, String country){
        JSONArray filteredArray = new JSONArray();
        for(int i=0;i<jsonArray.length();i++){

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if(jsonObject.getString("country").equalsIgnoreCase("US")){
                filteredArray.put(jsonObject);
            }
        }
        if(filteredArray.isEmpty()){
            System.out.println("No US cities found from the input");
        }
        return filteredArray;
    }
}
