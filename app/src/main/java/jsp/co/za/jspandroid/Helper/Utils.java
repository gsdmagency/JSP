package jsp.co.za.jspandroid.Helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Utils {
    static ProgressDialog dialog = null;
    public  static SharedPreferences.Editor editor;

    public static void showDialog(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Please wait...");
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.show();

    }

    public static void dismis() {
        dialog.dismiss();

    }

    public static void savePre(Context context, String key, int value) {
        editor= context.getSharedPreferences("JSP", MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getPreInt(Context context, String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("JSP", MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);

    }

    public static void clearPreInt(Context context, String key) {
        editor= context.getSharedPreferences("JSP", MODE_PRIVATE).edit();
        editor.putInt(key, 0);
        editor.apply();
    }


}
