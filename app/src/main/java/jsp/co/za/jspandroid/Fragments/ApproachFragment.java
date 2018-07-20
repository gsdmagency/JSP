package jsp.co.za.jspandroid.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jsp.co.za.jspandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApproachFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApproachFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApproachFragment extends Fragment {

    public ApproachFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApproachFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApproachFragment newInstance(String param1, String param2) {
        ApproachFragment fragment = new ApproachFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approach, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Our Approach");
        ImageView imageView = view.findViewById(R.id.landingImage);
        Glide
                .with(this)
                .load("http://www.jolandiesphotography.co.za/jspapp/image1.jpg")
                .into(imageView);
        return view;
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
