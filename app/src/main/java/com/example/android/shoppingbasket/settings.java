package com.example.android.shoppingbasket;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.attr.category;
import static android.R.attr.pointerIcon;

public class settings extends AppCompatActivity {

    private Item item = new Item();
    ArrayList<Item> listItems;
    private EditText nameEd;
    private TextView barcodeEd;
    private EditText priceEd;
    private EditText categEd;
    private EditText shopEd;
    private Button submitBTN;
    private Button scanBTN;
    private TextView debugTxt;
    private float price2;
    private String barcode,name;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent i = getIntent();
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            listItems = (ArrayList<Item>) i.getSerializableExtra("sampleObject");
            position = (int) i.getIntExtra("position",0);
        }
        item = listItems.get(position);
        nameEd = (EditText) findViewById(R.id.nameEd);
        nameEd.setText(item.getName());
        nameEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEd.setText("");
            }
        });
        priceEd = (EditText) findViewById(R.id.priceEd);
        priceEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceEd.setText(0);
            }
        });
        categEd = (EditText) findViewById(R.id.categEd);
        shopEd = (EditText) findViewById(R.id.shopEd);
        barcodeEd = (TextView) findViewById(R.id.barcodeEd);
        barcodeEd.setText(item.getBarcode());

        submitBTN = (Button) findViewById(R.id.submitBTN);
        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, price, category, shop;
                name = nameEd.getText().toString();
                barcode = barcodeEd.getText().toString();
                price = priceEd.getText().toString();
                price2= Float.parseFloat(price);
                category = categEd.getText().toString();
                shop = shopEd.getText().toString();
                Item temp = new Item();
                temp.Item(name,barcode,category,price2,shop);
                item=temp;
                listItems.set(position,item);
                debugTxt.setText(item.getName()+" - "+item.getPrice()+" - "+item.getBarcode()+" - "+item.getCategory()+" - "+item.getShop());
                Intent toBasket = new Intent(settings.this, BasketList.class);
                toBasket.putExtra("sampleObject",listItems);
                startActivity(toBasket);

            }
        });

        scanBTN = (Button) findViewById(R.id.ScanBTN);
        scanBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(settings.this);
                scanIntegrator.initiateScan();

            }
        });



        debugTxt = (TextView) findViewById(R.id.debug);


    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            barcode = scanningResult.getContents();
            barcodeEd.setText(barcode);
//            String scanFormat = scanningResult.getFormatName();
//            formatTxt.setText("FORMAT: " + scanFormat);
//            contentTxt.setText("CONTENT: " + barCode);
//            Item temp = new Item();
//            temp.Item("", barCode, "", 0, "");
//            Intent toSettings = new Intent(BasketList.this, settings.class);
//            toSettings.putExtra("sampleObject",temp);
//            startActivity(toSettings);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
