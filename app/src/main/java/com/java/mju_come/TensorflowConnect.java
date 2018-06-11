package com.java.mju_come;

import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.java.mju_come.Result.ResultActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class TensorflowConnect {

    private Context context;
    public TensorflowConnect(Context context) {
        this.context =  context;
    }

    public void Upload(String uploaduri, byte[] data) {

        Log.i("hoho","시작");
        try {
            URL urlCon = new URL(uploaduri);
            HttpURLConnection httpURLConnection = (HttpURLConnection)urlCon.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(60);
            DataOutputStream request = new DataOutputStream(httpURLConnection.getOutputStream());
            request.write(data,0,data.length);
            request.flush();
            request.close();

            Log.i("hoho",httpURLConnection.getResponseMessage());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String Load(String loadurl, Uri source) {

        String result = null;
        try {
            URL urlCon = new URL(loadurl);
            HttpURLConnection connection = (HttpURLConnection) urlCon.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            result = response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
