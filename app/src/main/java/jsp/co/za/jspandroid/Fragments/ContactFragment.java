package jsp.co.za.jspandroid.Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import jsp.co.za.jspandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment implements View.OnClickListener {
    EditText name = null;
    EditText email = null;
    EditText phone = null;
    EditText wedding = null;
    EditText message = null;
    TextView txtWeb = null;
    String packages=null;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FAQFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Contact Us");
        Spinner dropdown = view.findViewById(R.id.spinner1);
        Button btnSend = view.findViewById(R.id.button_submit);
        LinearLayout btnWhatsapp = view.findViewById(R.id.whatsapp);
        LinearLayout btnPhone = view.findViewById(R.id.phone);
        LinearLayout btnWeb = view.findViewById(R.id.web);
        LinearLayout btnEmail = view.findViewById(R.id.btnEmail);
        ImageView imgLogo = view.findViewById(R.id.logo);
        name = view.findViewById(R.id.edtName);
        email = view.findViewById(R.id.edtEmail);
        phone = view.findViewById(R.id.edtNumber);
        wedding = view.findViewById(R.id.edtWedding);
        message = view.findViewById(R.id.edtMessagebox);
        txtWeb = view.findViewById(R.id.txtWeb);

        String[] items = new String[]{"Platinum Package", "Gold Package", "Silver Package", "Bronze Package", "Design Package"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(((TextView) parent.getChildAt(0))!=null) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    packages = ((TextView) parent.getChildAt(0)).getText().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Glide
                .with(this)
                .load("http://www.jolandiesphotography.co.za/jspapp/logo.png")
                .into(imgLogo);
        dropdown.setAdapter(adapter);
        btnSend.setOnClickListener(this);
        btnWhatsapp.setOnClickListener(this);
        btnPhone.setOnClickListener(this);
        btnWeb.setOnClickListener(this);
        btnEmail.setOnClickListener(this);
        wedding.setOnClickListener(this);


        return view;
    }

    public void sendEmail(String subject,String body) {

        try {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "inf@jolandiesphotography.co.za", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void whatsapp(Activity activity) {

        try {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "");
            sendIntent.putExtra("jid", "27714056504" + "@s.whatsapp.net");
            sendIntent.setPackage("com.whatsapp");
            activity.startActivity(sendIntent);
        } catch (Exception e) {
            Toast.makeText(activity, "Error\n" + "Whatsapp App not avaliable", Toast.LENGTH_SHORT).show();
        }
    }

    public static void callPhone(Activity activity, String number) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, "Please accept all your permission", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean validate(TextView name, TextView email, TextView phone, TextView wedding, TextView message) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (name.getText().toString().isEmpty()) {
            name.setError("Please fill text");
            return false;
        } else if (email.getText().toString().isEmpty()) {
            email.setError("Please fill text");
            return false;
        } else if (phone.getText().toString().isEmpty()) {
            phone.setError("Please fill text");
            return false;
        } else if (wedding.getText().toString().isEmpty()) {
            wedding.setError("Please fill text");
            return false;
        } else if (message.getText().toString().isEmpty()) {
            message.setError("Please fill text");
            return false;
        } else if (!email.getText().toString().matches(emailPattern)) {
            email.setError("Not valid email");
            return false;
        } else {
            return true;
        }

    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public  void openCalendar()
    {
        final Calendar myCalendar = Calendar.getInstance();


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };
        new DatePickerDialog(Objects.requireNonNull(getActivity()),R.style.DialogTheme, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH))
                .show();

    }
    private void updateLabel(Calendar myCalendar) {
        String myFormat = "dd/MMM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        wedding.setText(sdf.format(myCalendar.getTime()));
    }

    public  void openWeb(String url)
    {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                if (validate(name,email,phone,wedding,message)) {
                    sendEmail("JSP contact form","Name: "+name.getText() +"\n \n Email: "+email.getText()+"\n \n Phone: "+phone.getText()+"\n \n Date: "+wedding.getText()+"\n \n Package: "+packages+"\n \n Message: "+message.getText());
                }
                break;

            case R.id.whatsapp:
                whatsapp(getActivity());
                break;

            case R.id.phone:
                callPhone(getActivity(), "0714056504");
                break;

            case R.id.edtWedding:
                openCalendar();
                break;
            case R.id.web:
                openWeb("http://"+txtWeb.getText().toString());
                break;
            case R.id.btnEmail:
                sendEmail("","");
                break;

        }
    }

    // TODO: Rename method, update argument and hook method into UI event

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
