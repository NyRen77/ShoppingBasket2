package com.example.android.shoppingbasket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringTokenizer;

import static android.R.id.list;
import static android.os.Build.VERSION_CODES.M;
import static com.example.android.shoppingbasket.R.id.moneyTxt;
import static com.example.android.shoppingbasket.R.id.searchArea;

public class BasketList extends AppCompatActivity {

    private int cost;
    private TextView moneyView;
    private EditText search;
    private Button scanBTN;
    private Button submitBTN;
    private Button getSettingsBTN;
    private ListView listView;
    private  LinearLayout mainLayout;
    ArrayAdapter<Item> listViewAdapter;
    private static AdapterItem adapter;
    ArrayList<Item> listItems;
    static final String COST = "items_cost";
    static final String MONEY_VIEW = "money_view";
    static final String SEARCH = "search_text";
    static final String LISTITEMS = "listarray";
    private static final String STATE_LIST = "State Adapter Data";
    private String barCode;
    private CheckBox chcBox;
    private Button settingsBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_list);


//        //If restoring from state, load the list from the bundle
//        if (savedInstanceState != null) {
//            ArrayList<Item> list = savedInstanceState.getParcelableArrayList(STATE_LIST);
//            AdapterItem adapter = new MenuItemAdapter(list, getActivity(), this);
//        }
////        else {
//            //Else we are creating our Activity from scratch, pull list from where ever you initially get it from
//            ArrayList<Item> list = getInitData();
//            ItemAdapter adapter = new MenuItemAdapter(list, getActivity(), this);
//        }
        Intent i = getIntent();
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
                listItems = (ArrayList<Item>) i.getSerializableExtra("sampleObject");
        }

        moneyView = (TextView)findViewById(R.id.moneyTxt);
        search = (EditText)findViewById(R.id.itemSearch);
        search.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            search.setText("");

                                        }
                                    });
        scanBTN = (Button)findViewById(R.id.barcodeScn);
        scanBTN.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          IntentIntegrator scanIntegrator = new IntentIntegrator(BasketList.this);
                                          scanIntegrator.initiateScan();
                                          displayPrice();

                                      }
                                  });
        submitBTN = (Button)findViewById(R.id.submit);
        submitBTN.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Item temp = new Item();
                                           temp.Item(search.getText().toString());
                                           listItems.add(temp);
                                           moneyView.requestFocus();
                                           InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                           imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                                           search.getText().clear();
                                           adapter.notifyDataSetChanged();
                                           displayPrice();
                                       }
                                     });

        listView = (ListView) findViewById(R.id.shoppingList);
//        listViewAdapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1,
//                listItems);
//        listView.setAdapter(listViewAdapter);

        adapter = new AdapterItem(listItems,getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        displayPrice();


    }

    public void displayPrice (){
        float sum = 0;
        for (int i =0; i < listItems.size(); i++){
            sum += listItems.get(i).getPrice();
        }
        moneyView.setText("$ "+sum);


    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            barCode = scanningResult.getContents();
//            String scanFormat = scanningResult.getFormatName();
//            formatTxt.setText("FORMAT: " + scanFormat);
//            contentTxt.setText("CONTENT: " + barCode);
            Item temp = new Item();
            temp.Item("", barCode, "", 0, "");
            Intent toSettings = new Intent(BasketList.this, settings.class);
            toSettings.putExtra("sampleObject",temp);
            startActivity(toSettings);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList(STATE_LIST, adapter.getList());
//    }


//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        long viewId = view.getId();
//
//        if (viewId == R.id.settings) {
//            Toast.makeText(this, "Button 1 clicked", Toast.LENGTH_SHORT).show();
//        } else if (viewId == R.id.item_check) {
//            Toast.makeText(this, "Button 2 clicked", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "ListView clicked" + id, Toast.LENGTH_SHORT).show();
//        }
//    }
//


//
//    /**
//     * Functions to save and restore the current state
//     */
//    @Override
//    public void onSaveInstanceState (Bundle savedInstanceState) {
//        savedInstanceState.putFloat(COST,cost);
//        savedInstanceState.putSerializable(LISTITEMS, listItems);
//
//
//        super.onSaveInstanceState(savedInstanceState);
//    }
//
//    @Override
//    public void onRestoreInstanceState (Bundle savedInstanceState){
//        super.onRestoreInstanceState(savedInstanceState);
//        cost = savedInstanceState.getInt(COST);
//        listItems = (ArrayList<Item>) savedInstanceState.getSerializable(LISTITEMS);
//
//    }

}
