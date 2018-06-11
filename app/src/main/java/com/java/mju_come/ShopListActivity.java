package com.java.mju_come;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShopListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        ListView listView = (ListView) findViewById(R.id.shop_listview);

        final ArrayList<ShopItem> mshop = new ArrayList<>();
        ShopItem shopItem1 = new ShopItem("장플라워", "경기도 용인시 처인구 김량장동", "0313233044");
        ShopItem shopItem2 = new ShopItem("꽃들", "경기도 용인시 처인구 역북동", "0313359566");
        ShopItem shopItem3 = new ShopItem("꽃채움", "경기도 용인시 처인구 역북동", "0313232001");
        ShopItem shopItem4 = new ShopItem("형제화원", "경기도 용인시 처인구 유방동", "0313337117");
        ShopItem shopItem5 = new ShopItem("꽃이야기", "경기도 용인시 처인구 이동면", "0313360711");
        ShopItem shopItem6 = new ShopItem("물속나라", "경기도 용인시 처인구 양지면", "0313384014");
        ShopItem shopItem7 = new ShopItem("모아농원", "경기도 용인시 처인구 남사면", "0313399079");
        ShopItem shopItem8 = new ShopItem("예담원", "경기도 용인시 처인구 양지면", "07077603091");
        ShopItem shopItem9 = new ShopItem("새한농원", "경기도 용인시 처인구 백암면", "0313344219");
        ShopItem shopItem10 = new ShopItem("러브플라워", "경기도 용인시 처인구 고림동", "0313210009");
        ShopItem shopItem11 = new ShopItem("나이스플라워", "경기도 용인시 처인구 고림동", "0313228655");


        mshop.add(shopItem1);
        mshop.add(shopItem2);
        mshop.add(shopItem3);
        mshop.add(shopItem4);
        mshop.add(shopItem5);
        mshop.add(shopItem6);
        mshop.add(shopItem7);
        mshop.add(shopItem8);
        mshop.add(shopItem9);
        mshop.add(shopItem10);
        mshop.add(shopItem11);


        Log.i("hoho", mshop.get(3).getShop_name());
        ShopAdapter shopAdapter = new ShopAdapter(this, R.layout.listshop_item, mshop);
        listView.setAdapter(shopAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String cNumber=mshop.get(i).getShop_number();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+cNumber));
                startActivity(intent);
            }
        });
    }
}
