package com.java.mju_come;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.java.mju_come.Result.ResultActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class NetworkTask extends AsyncTask<Integer,Integer,String> {

    private String Uploadapi = "http://106.10.43.212:3389/api/upload";
    private String Loadapi = "http://106.10.43.212:3389/api/load";
    private byte[] values;
    private Context context;
    private Uri source;
    ProgressDialog asyncDialog ;

    public NetworkTask(byte[] values, Uri source, Context context){
        this.values = values;
        this.context = context;
        this.source = source;
        asyncDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("Upload..");
        asyncDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer... integers) {
        String result = "";
        TensorflowConnect tensorflowConnect = new TensorflowConnect(context);
        tensorflowConnect.Upload(Uploadapi, values);
        result = tensorflowConnect.Load(Loadapi, source);
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String label = null;
        String percent = null;

        try {
            JSONObject jsonObject = new JSONObject(s);
            label = jsonObject.getString("label");
            percent = jsonObject.getString("percent");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        asyncDialog.dismiss();
        Intent intent2= new Intent(context.getApplicationContext(),ResultActivity.class);
        //갤러리에서 선택한 이미지 uri값을 전달함
        intent2.putExtra("filepath",source);
        intent2.putExtra("label",label);
        intent2.putExtra("percent",percent);
        context.startActivity(intent2);
    }
}
