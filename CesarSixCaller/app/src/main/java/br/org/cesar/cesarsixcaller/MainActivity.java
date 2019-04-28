package br.org.cesar.cesarsixcaller;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    Button button_show;
    Button button_cleaner_show;
    TextView tv_results;

    LinkedList<String> list;
    LinkedList<String> cleaner;

    private UpdateBroadcastReceiver updateBroadcastReceiver;

    private void showResults (LinkedList<String> l) {
        tv_results.setText("");
        if (l != null) {
            for (String item : l) {
                tv_results.setText(tv_results.getText() + "\n" + item);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_show = (Button) findViewById(R.id.button_show);
        button_cleaner_show = (Button) findViewById(R.id.button_cleaner_show);
        tv_results = (TextView) findViewById(R.id.tv_results);

        list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("b");
        list.add("d");
        list.add("c");
        list.add("e");
        list.add("f");
        list.add("b");
        list.add("g");

        showResults(list);

        updateBroadcastReceiver = new UpdateBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter("br.org.cesar.cesarsix.RESULT");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(updateBroadcastReceiver, intentFilter);

        button_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResults(list);
            }
        });

        button_cleaner_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(
                        "br.org.cesar.cesarsix",
                        "br.org.cesar.cesarsix.EmailProcessorIntentService"));
                intent.putExtra("KEY_LL", list);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                } else {
                    startService(intent);
                }
            }
        });
    }

    public class UpdateBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            cleaner = new LinkedList<String>((ArrayList<String>)intent.getExtras().get("KEY_LL_RESULT"));
            showResults(cleaner);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(updateBroadcastReceiver);
    }

}
