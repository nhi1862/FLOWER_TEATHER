package com.java.mju_come;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.java.mju_come.Result.FirebaseMypage;
import com.java.mju_come.Result.MypageCheck;
import com.java.mju_come.lost.Check.LostCheckActivity;
import com.java.mju_come.lost.lost_show.FindSearchActivity;
import com.java.mju_come.lost.lost_show.FirebaseClient;



public class MypageFragment extends Fragment{
    private ArrayAdapter<String> mypageAdapter;

    private Spinner mySpinner;
    String DB_URL = "https://mjucome-21a2b.firebaseio.com/result_img";
    String record="";
    FirebaseMypage firebaseMypage;
    ListView listview;
    ViewGroup rootView;
    ImageButton imgbutton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =(ViewGroup) inflater.inflate(R.layout.fragment_mypage,container,false);

        listview = (ListView)rootView.findViewById(R.id.mypage_listview);
        firebaseMypage = new FirebaseMypage(getActivity(), DB_URL, listview);
        firebaseMypage.refreshdata();
        //검색 이미지 버튼 초기화
        imgbutton=(ImageButton)rootView.findViewById(R.id.mypage_search_button);


        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mypageAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.search_category));
        mySpinner = (Spinner)rootView.findViewById(R.id.mypage_spinner_category);


        //각각의 listview 아이템 클릭시 발생하는 이벤트
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView text = (TextView) view.findViewById(R.id.mlist_data2);
                String id = text.getTag().toString();
                Intent intent = new Intent(getActivity(), MypageCheck.class);
                intent.putExtra("id", id);
                startActivity(intent);
//                Toast.makeText(getActivity(),text.getTag().toString(),Toast.LENGTH_LONG).show();



            }
        });

        //검색 버튼을 눌렀을 때 발생하는 이벤트
        imgbutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(record.length()>0) {
                    listview.setFilterText(record);
                }else{
                    listview.clearTextFilter();
                }
            }
        });


        //adapter를 spinner에 연결
        mypageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(mypageAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 1:
                        record = "개나리";
                        break;
                    case 2:
                        record = "동백";
                        break;
                    case 3:
                        record = "목화";
                        break;
                    case 4:
                        record = "백합";
                        break;
                    case 5:
                        record = "장미";
                        break;
                    case 6:
                        record = "나팔꽃";
                        break;
                    case 7:
                        record = "동자꽃";
                        break;
                    case 8:
                        record = "데이지";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });




    }
}
