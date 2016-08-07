package com.dilip.usertestandroid;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dilippatidar on 06/08/16.
 */
public class JsonDataParser {

    private InputStream inputStream;
    private DataLoaderCallback delegate;
    private ArrayList<HashMap<String, String>> users = new ArrayList<HashMap<String, String>>();

    public JsonDataParser(DataLoaderCallback delegate,InputStream is){

        this.inputStream=is;
        this.delegate=delegate;

    }
    private  JsonDataParser(){}


    public ArrayList<HashMap<String, String>> getUserList(){

        return this.users;

    }
    private String loadJSONFromAsset() {

        String json = null;

        try {
//            InputStream is = this.context.getAssets().open("Users.json");
            if(this.inputStream==null) return null;
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public void parseJsonData(){
        try {


            JSONArray usersArray = new JSONArray(loadJSONFromAsset());

            HashMap<String, String> m_li;
            
            for (int i = 0; i < usersArray.length(); i++) {

                JSONObject user = usersArray.getJSONObject(i);
                String first_name = user.getString("first_name");
                String last_name = user.getString("last_name");
                String department = user.getString("department");
                String job_title = user.getString("job_title");
                String email_address = user.getString("email_address");
                String employee_type = user.getString("employee_type");
                String location = user.getString("location");

                m_li = new HashMap<String, String>();
                m_li.put("first_name", first_name);
                m_li.put("last_name", last_name);
                m_li.put("department", department);
                m_li.put("job_title", job_title);
                m_li.put("email_address", email_address);
                m_li.put("employee_type", employee_type);
                m_li.put("location", location);

                users.add(m_li);
            }


        if(this.delegate!=null){
            delegate.didFinishLoadingData(true,users);
        }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }





}
