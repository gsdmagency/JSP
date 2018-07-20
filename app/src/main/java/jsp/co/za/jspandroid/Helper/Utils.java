package jsp.co.za.jspandroid.Helper;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {
    static  ProgressDialog dialog=null;
    public static void showDialog(Context context) {
        dialog =new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Please wait...");
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.show();

    }
    public static void dismis() {
        dialog.dismiss();

    }
}
