package jsp.co.za.jspandroid.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDB {

    private MyDatabaseHelper dbHelper;

    private SQLiteDatabase database;

    public final static String NOTIFICATIONS_TABLE = "MyNotifications";

    public final static String NOTIFICATIONS_ID = "_id"; //
    public final static String NOTIFICATIONS_TITLE = "title";
    public final static String NOTIFICATIONS_MESSAGE = "message";

    /**
     * @param context
     */
    public MyDB(Context context) {
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public void createRecords( String title, String message) {
        ContentValues values = new ContentValues();
        values.put(NOTIFICATIONS_TITLE, title);
        values.put(NOTIFICATIONS_MESSAGE, message);
        database.insert(NOTIFICATIONS_TABLE, null, values);
    }

    public Cursor selectRecords() {

        Cursor cursor = database.rawQuery("SELECT * FROM MyNotifications", null);
        cursor.moveToFirst();
        return cursor; // iterate to get each value.
    }

    public void delete(int id) {
        database.execSQL("delete from "+ NOTIFICATIONS_TABLE +" where _id="+id);
    }

    public void deleteAll() {
        database.execSQL("delete from "+ NOTIFICATIONS_TABLE);
    }



}
