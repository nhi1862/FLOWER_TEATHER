package com.java.mju_come.lost;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.java.mju_come.R;
import com.java.mju_come.ShopListActivity;


public class ReservationFragment extends Fragment {

    private Button shoplistbtn ;
    private ViewGroup rootview;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedinstanceState){
        rootview =(ViewGroup) inflater.inflate(R.layout.fragment_reservation,container,false);
        shoplistbtn=(Button)rootview.findViewById(R.id.shop_search_btn);

        return rootview;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shoplistbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShopListActivity.class);
                startActivity(intent);
            }
        });
    }
}
