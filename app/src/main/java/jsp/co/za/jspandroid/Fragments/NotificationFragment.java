package jsp.co.za.jspandroid.Fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jsp.co.za.jspandroid.Activities.MainActivity;
import jsp.co.za.jspandroid.Adapters.NotificationAdapter;
import jsp.co.za.jspandroid.Helper.Interface;
import jsp.co.za.jspandroid.Helper.MyDB;
import jsp.co.za.jspandroid.R;


public class NotificationFragment extends Fragment implements Interface {
    NotificationAdapter menuAdapter;
    ArrayList<String> menu_list;
    RecyclerView notification_view;
    MyDB db;
    Cursor cursor;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notification");
        notification_view = view.findViewById(R.id.notification_view);
        db = new MyDB(getActivity());
        menu_list = new ArrayList<>();
        cursor = db.selectRecords();
        if (cursor != null && cursor.getCount() > 0) {


            if (cursor.moveToFirst()) {
                do {
                    String message = cursor.getString(cursor.getColumnIndex("message"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String id = cursor.getString(cursor.getColumnIndex("_id"));
                    if (title == null)
                        title = "Jolandie's Photography";
                    if (message != null)
                        menu_list.add(id + ";" + title + "," + message);
                } while (cursor.moveToNext());
            }

        }
        menuAdapter = new NotificationAdapter(getActivity(), menu_list, this);
        notification_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        notification_view.setAdapter(menuAdapter);
        return view;
    }

    @Override
    public void callback() {
        cursor= db.selectRecords();
        menu_list = new ArrayList<>();
        menu_list.clear();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String message = cursor.getString(cursor.getColumnIndex("message"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String id = cursor.getString(cursor.getColumnIndex("_id"));
                    if (title == null)
                        title = "Jolandie's Photography";
                    if (message != null)
                        menu_list.add(id + ";" + title + "," + message);
                } while (cursor.moveToNext());
            }

        }
        menuAdapter = new NotificationAdapter(getActivity(), menu_list, this);
        notification_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        notification_view.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();
        if (MainActivity.navigationView != null) {
            if (db.selectRecords() != null && MainActivity.navigationView != null)
                MainActivity.navigationView.getMenu().findItem(R.id.nav_notification).setTitle("Notification " + db.selectRecords().getCount());
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
