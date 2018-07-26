package jsp.co.za.jspandroid.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import jsp.co.za.jspandroid.Activities.MainActivity;
import jsp.co.za.jspandroid.Helper.Interface;
import jsp.co.za.jspandroid.Helper.MyDB;
import jsp.co.za.jspandroid.R;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private Context context;
    private ArrayList<String> itemList;
    public Interface anInterface;
    AlertDialog.Builder dialog;

    // Constructor of the class
    public NotificationAdapter(Context context, ArrayList<String> itemList, Interface anInterface) {
        this.context = context;
        this.itemList = itemList;
        this.anInterface = anInterface;
    }

    public NotificationAdapter() {
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    // specify the row layout file and click for each row
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cadview_layout, parent, false);
        return new ViewHolder(view);
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int listPosition) {
        TextView title = holder.title;
        TextView message = holder.message;
        TextView id = holder.txtId;
        String strTitle = itemList.get(listPosition).substring(itemList.get(listPosition).indexOf(',') + 1);
        String strId = itemList.get(listPosition).substring(0, itemList.get(listPosition).indexOf(';'));
        String strMessage = itemList.get(listPosition).substring(itemList.get(listPosition).indexOf(';') + 1, itemList.get(listPosition).indexOf(','));
        title.setText(strTitle);
        id.setText(strId);
        message.setText(strMessage);
    }

    // Static inner class to initialize the views of rows
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView message;
        private TextView txtId;
        private RelativeLayout relative_layout;

        private ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.message);
            message = (TextView) itemView.findViewById(R.id.title);
            txtId = (TextView) itemView.findViewById(R.id.id);
            relative_layout = itemView.findViewById(R.id.relative_layout);
            relative_layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final MyDB db = new MyDB(context);
            final Cursor cursor = db.selectRecords();
            switch (v.getId()) {
                case R.id.relative_layout:
                    dialog = new AlertDialog.Builder(context, R.style.DialogTheme);
                    dialog.setTitle("Alert");
                    dialog.setMessage("Are you sure you want to delete notification");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            db.delete(Integer.parseInt(txtId.getText().toString().trim()));
                            anInterface.callback();
                        }
                    });
                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setNeutralButton("Delete All", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MyDB db = new MyDB(context);
                            db.deleteAll();
                            anInterface.callback();
                        }
                    });

                    AlertDialog alert = dialog.create();
                    alert.show();

                    break;

            }
        }
    }


}