package jsp.co.za.jspandroid.Fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import jsp.co.za.jspandroid.Adapters.GalleryAdapter;
import jsp.co.za.jspandroid.R;
import jsp.co.za.jspandroid.Helper.Utils;


public class GalleryFragment extends Fragment {
    GridView gridView;
    String[] myUrls;
    LayoutInflater mInflator;


    public GalleryFragment() {
        // empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gridview, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Gallery");
        mInflator = inflater;
        myUrls = new String[]{"http://www.jolandiesphotography.co.za/jspapp/image1.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image2.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image3.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image4.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image5.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image6.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image7.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image8.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image9.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image10.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image11.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image12.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image13.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image14.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image15.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image16.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image17.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image18.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image19.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image20.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image21.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image22.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image23.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image24.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image25.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image26.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image27.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image28.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image29.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image30.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image31.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image32.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image33.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image34.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image35.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image36.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image37.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image38.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image39.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image40.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image41.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image42.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image43.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image45.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image46.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image47.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image48.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image49.jpg",
                "http://www.jolandiesphotography.co.za/jspapp/image49.jpg"};
        gridView = view.findViewById(R.id.grdView);
        GalleryAdapter gridviewAdapter = new GalleryAdapter(getActivity(), myUrls);
        gridView.setAdapter(gridviewAdapter);

        //onItemClicked(gridView);
        return view;
    }


    public void onItemClicked(GridView gridView) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.showDialog(getActivity());
                new DownloadImage().execute(myUrls[position]);
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setImage(Drawable drawable) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        View dialogView = this.mInflator.inflate(R.layout.custom_image_layout, null);
        dialogBuilder.setView(dialogView);
        ImageView imgZoom = dialogView.findViewById(R.id.imgZoom);
        Utils.dismis();
        imgZoom.setBackgroundDrawable(drawable);
        dialogBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        Objects.requireNonNull(b.getWindow()).setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        b.show();

    }
    //Download new image because Glide library uses cached images
    public class DownloadImage extends AsyncTask<String, Integer, Drawable> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Drawable doInBackground(String... arg0) {
            // This is done in a background thread
            return downloadImage(arg0[0]);
        }


        protected void onPostExecute(Drawable image) {
            setImage(image);
        }

        private Drawable downloadImage(String _url) {
            //Prepare to download image
            URL url;
            BufferedOutputStream out;
            InputStream in;
            BufferedInputStream buf;

            //BufferedInputStream buf;
            try {
                url = new URL(_url);
                in = url.openStream();


                buf = new BufferedInputStream(in);

                // Convert the BufferedInputStream to a Bitmap
                Bitmap bMap = BitmapFactory.decodeStream(buf);
                if (in != null) {
                    in.close();
                }
                if (buf != null) {
                    buf.close();
                }

                return new BitmapDrawable(bMap);

            } catch (Exception e) {
                Log.e("Error reading file", e.toString());
            }

            return null;
        }

    }


}
