package com.java.mju_come.lost.lost_show;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.java.mju_come.lost.Check.LostCheckActivity;
import com.java.mju_come.R;


public class SearchActivity extends AppCompatActivity {
    private ArrayAdapter<String> myAdapter;

    private Spinner mySpinner;
    String DB_URL = "https://mjucome-21a2b.firebaseio.com/lost_img";
    String record="";
    FirebaseClient firebaseClient;
    ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listview = (ListView)findViewById(R.id.search_listview);
        firebaseClient = new FirebaseClient(this, DB_URL, listview);
        firebaseClient.refreshdata();

        myAdapter = new ArrayAdapter<String>(SearchActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.search_category));
        mySpinner = (Spinner) findViewById(R.id.spinner_category);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView text = (TextView) view.findViewById(R.id.titletext);
                String id = text.getTag().toString();

                Intent intent = new Intent(getApplicationContext(), LostCheckActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);


            }
        });

        //adapter를 spinner에 연결
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 1:
                        record = "악세사리";
                        break;
                    case 2:
                        record = "의류";
                        break;
                    case 3:
                        record = "문구";
                        break;
                    case 4:
                        record = "전자기기";
                        break;
                    case 5:
                        record = "화장품";
                        break;
                    case 6:
                        record = "가방/지갑";
                        break;
                    case 7:
                        record = "기타";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });




    }

    public void searchbutton(View view) {
        if(record.length()>0) {
            listview.setFilterText(record);
        }else{
            listview.clearTextFilter();
        }
    }
}