package com.example.android.shoppingbasket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        If we call an activity with an intent and we want to get the data out from it use the following code:
        Intent i = getIntent();
        Bundle extras = getIntent().getExtras();
//
        if(extras != null){
            listItems = (ArrayList<Item>) i.getSerializableExtra("sampleObject");
        }
        // We could put standard items in the else clause if we want to
        else {
            listItems = new ArrayList<Item>();
        }
    }
    //        Redirecting to the correct activity on taping a button
    public void onButtonClick(View view) {
//        if(view.getId() == R.id.newItemBTN) {
//            Intent toNewItem = new Intent(MainActivity.this, newItem.class);
//            startActivity(toNewItem);
//        }
        if(view.getId() == R.id.basketBTN) {
            Intent toBasketList = new Intent(MainActivity.this, BasketList.class);
            toBasketList.putExtra("sampleObject", listItems);
            startActivity(toBasketList);
        }
    }
}