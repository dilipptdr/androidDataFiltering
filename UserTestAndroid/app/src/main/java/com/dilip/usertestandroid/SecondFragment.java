package com.dilip.usertestandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecondFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {


    public   String gpName;
    private OnFragmentInteractionListener mListener;
    private ListView listView;
    private CustomAdapter adapter;
    private Map<String, ArrayList<HashMap<String, String>>> gpedData;
    private ArrayList<HashMap<String, String>> filtdUsers;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();

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
         View v=inflater.inflate(R.layout.fragment_second, container, false);
        listView = (ListView) v.findViewById(R.id.listview2);
        Activity act=getActivity();
        adapter= new CustomAdapter(act,new ArrayList<String >());
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Do something in response to the click
                TextView tv=(TextView)v.findViewById(R.id.text);
                String val= tv.getText().toString();

                HashMap<String, String> user=filtdUsers.get(position);
                DeatailFragment detailFragment  =new DeatailFragment();
                detailFragment.user=new HashMap<String,String >(user);
                String backStateName = detailFragment.getClass().getName();
                String fragmentTag = backStateName;
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_right_frag, R.anim.slide_out_left_frag,R.anim.slide_in_left_frag,R.anim.slide_out_right_frag);

                ft.replace(R.id.fragment_container, detailFragment, fragmentTag);
                ft.addToBackStack(backStateName);
                ft.commit();

            }
        };

        listView.setOnItemClickListener(mMessageClickedHandler);

        this.gpedData=new HashMap<String, ArrayList<HashMap<String, String>>>(((MainActivity)getActivity()).gpedData);
        if(gpedData!=null)
            filtdUsers=gpedData.get(gpName);
        if(filtdUsers!=null) {
            ArrayList<String> dataToShow = new ArrayList<>();
            for (int i = 0; i <filtdUsers.size() ; i++) {
                HashMap<String, String> user=filtdUsers.get(i);
                dataToShow.add(user.get("first_name") + " " + user.get("last_name"));
            }

            adapter.updateAdapterData(dataToShow);
        }
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void didReceiveFilteredUserdata(ArrayList<HashMap<String, String>> filtdUsers);
    }
}
