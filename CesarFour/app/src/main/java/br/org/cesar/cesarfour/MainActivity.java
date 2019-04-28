package br.org.cesar.cesarfour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list_view;
    EditText input_search;
    CustomArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] items = {"you", "probably", "despite", "moon", "misspellings", "pale"};
        ArrayList<String> itemList;

        list_view = (ListView) findViewById(R.id.list_view);
        input_search = (EditText) findViewById(R.id.input_search);

        itemList = new ArrayList<>();
        for(int i = 0; i < items.length; i++) {
            itemList.add(items[i]);
        }

        adapter = new CustomArrayAdapter(this, R.layout.list_item, R.id.item_name, itemList);
        list_view.setAdapter(adapter);

        input_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
