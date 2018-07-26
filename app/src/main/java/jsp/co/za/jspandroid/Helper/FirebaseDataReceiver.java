package jsp.co.za.jspandroid.Helper;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import jsp.co.za.jspandroid.Activities.MainActivity;
import jsp.co.za.jspandroid.R;

public class FirebaseDataReceiver extends WakefulBroadcastReceiver {

    private final String TAG = "FirebaseDataReceiver";
    private static final AtomicInteger count = new AtomicInteger(0);

    public void onReceive(Context context, Intent intent) {


        Bundle bundle = intent.getExtras();
        MyDB db = new MyDB(context);

        String strTitle = "";
        String strMessage = "";
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();
            if (bundle.get("from") != null) {
                strTitle = bundle.get("google.c.a.c_l").toString();
            } else {
                strTitle = "Jolandie's Photography";
            }
            if (bundle.get("gcm.notification.body") != null) {
                strMessage = bundle.get("gcm.notification.body").toString();
            }
            Log.d(TAG, strTitle);

            db.createRecords(strTitle, strMessage);
            if (MainActivity.navigationView != null) {
                if (db.selectRecords() != null && MainActivity.navigationView != null)
                    MainActivity.navigationView.getMenu().findItem(R.id.nav_notification).setTitle("Notification " + db.selectRecords().getCount());
            }
            Utils.savePre(context, "notification", 1);

        }

    }
}