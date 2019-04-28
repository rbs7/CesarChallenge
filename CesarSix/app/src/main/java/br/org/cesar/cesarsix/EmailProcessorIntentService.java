package br.org.cesar.cesarsix;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

import static android.support.v4.app.NotificationCompat.PRIORITY_MIN;

public class EmailProcessorIntentService extends IntentService {
    public EmailProcessorIntentService() {
        super("EmailProcessorIntentService");
    }

    private LinkedList<String> list;
    private HashSet<String> marked;
    private Stack<Number> toRemove;

    public void removeDuplicates(int node) {
        if (node >= list.size()) {  //end of the linked list
            return;
        }
        if (marked.contains(list.get(node))) {
            toRemove.push(node);
        } else {
            marked.add(list.get(node));
        }
        removeDuplicates(node+1);   //next
    }

    //Workaround for running service in foregound in Oreo and Pie
    //https://stackoverflow.com/questions/44425584/context-startforegroundservice-did-not-then-call-service-startforeground
    //https://stackoverflow.com/questions/6397754/android-implementing-startforeground-for-a-service
    private static final int ID_SERVICE = 102;
    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(NotificationManager notificationManager){
        String channelId = "cesar_six_id";
        String channelName = "Cesar Six Service";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(PRIORITY_MIN)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .build();
        startForeground(ID_SERVICE, notification);

        if (intent != null) {
            list = new LinkedList<String>((ArrayList<String>)intent.getExtras().get("KEY_LL"));

            //Remove duplicates
            marked = new HashSet<>();
            toRemove = new Stack<>();
            removeDuplicates(0);
            while (!toRemove.empty()) {
                int r = toRemove.pop().intValue();
                list.remove(r);
            }

            //Send results
            Intent intentResponse = new Intent();
            intentResponse.setAction("br.org.cesar.cesarsix.RESULT");
            intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
            intentResponse.putExtra("KEY_LL_RESULT", list);
            sendBroadcast(intentResponse);
        }

        stopForeground(true);
    }
}
