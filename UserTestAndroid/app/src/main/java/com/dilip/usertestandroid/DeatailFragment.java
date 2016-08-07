package com.dilip.usertestandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeatailFragment extends Fragment {


    private TextView firstNameTV;
    private TextView lastNameTV;
    private TextView emailTV;
    private TextView employeeTypeTV;
    private TextView jobTitleTV;
    private TextView locationTV;
    private TextView departmentTV;

    public HashMap<String, String> user;

    public DeatailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View v=inflater.inflate(R.layout.fragment_deatail, container, false);

        firstNameTV=(TextView)v.findViewById(R.id.first_name);
        lastNameTV=(TextView)v.findViewById(R.id.last_name);
        emailTV=(TextView)v.findViewById(R.id.email_address);
        employeeTypeTV=(TextView)v.findViewById(R.id.employee_type);
        locationTV=(TextView)v.findViewById(R.id.location);
        departmentTV=(TextView)v.findViewById(R.id.department);
        jobTitleTV=(TextView)v.findViewById(R.id.job_title);

        if(user!=null){

            String first_name = user.get("first_name");
            String last_name = user.get("last_name");
            String department = user.get("department");
            String job_title = user.get("job_title");
            String email_address = user.get("email_address");
            String employee_type = user.get("employee_type");
            String location = user.get("location");

            firstNameTV.setText("first_name : "+first_name);
            lastNameTV.setText("last_name : "+last_name);
            locationTV.setText("location : "+location);
            departmentTV.setText("department : "+department);
            jobTitleTV.setText("job_title : "+job_title);
            emailTV.setText("email_address : "+email_address);
            employeeTypeTV.setText("employee_type : "+employee_type);



        }


        return v;
    }




}
