package com.dilip.usertestandroid;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 *
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment implements AdapterView.OnItemSelectedListener ,DataLoaderCallback {


    private ArrayList<HashMap<String, String>> users;

    private ListView listView;
    private ProgressDialog progress;
    private CustomAdapter adapter;
    private Map<String, ArrayList<HashMap<String, String>>> gpedData;
    private FirstFragmentCallback callback;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_first, container, false);

        listView = (ListView) v.findViewById(R.id.listview);
        adapter= new CustomAdapter((Activity)getActivity(),new ArrayList<String >());
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Do something in response to the click
                TextView tv=(TextView)v.findViewById(R.id.text);
                String val= tv.getText().toString();

                if(callback!=null){
                    callback.didReturnGroupedData(val,gpedData);
                }

                SecondFragment secondFragment=new SecondFragment();
                secondFragment.gpName=val;
                String backStateName = secondFragment.getClass().getName();
                String fragmentTag = backStateName;
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_right_frag, R.anim.slide_out_left_frag,R.anim.slide_in_left_frag,R.anim.slide_out_right_frag);

                ft.replace(R.id.fragment_container, secondFragment, fragmentTag);
                ft.addToBackStack(backStateName);
                ft.commit();


            }
        };

        listView.setOnItemClickListener(mMessageClickedHandler);

        Spinner spinner = (Spinner) v.findViewById(R.id.filter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.filter_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FirstFragmentCallback) {
            callback = (FirstFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(this.users==null){
            progress = new ProgressDialog(getContext());
            progress.setMessage("Loading User Data :)");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(true);
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        if(this.users==null) {
            try {

                final JsonDataParser parser = new JsonDataParser(this,getActivity().getAssets().open("Users.json"));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        parser.parseJsonData();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        if(this.users!=null){
            String attr=(String) parent.getItemAtPosition(pos);
            gpedData= new UserGrouper().groupUsersByAttribute(users,attr);
            Set keys=gpedData.keySet();

            adapter.updateAdapterData(new ArrayList<String>(keys));

        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback

    }

    @Override
    public void didFinishLoadingData(boolean success, ArrayList<HashMap<String, String>> users) {

        this.users=(ArrayList<HashMap<String, String>>)users.clone();

        gpedData= new UserGrouper().groupUsersByAttribute(users,"department");

        final Set keys=gpedData.keySet();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (FirstFragment.this.progress != null)
                    progress.dismiss();
                adapter.updateAdapterData(new ArrayList<String>(keys));

            }
        });
    }
}
