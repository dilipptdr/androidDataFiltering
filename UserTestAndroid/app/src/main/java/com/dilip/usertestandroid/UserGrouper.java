package com.dilip.usertestandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dilippatidar on 06/08/16.
 */

public class UserGrouper {


    public  Map<String, ArrayList<HashMap<String, String>>> groupUsersByAttribute( ArrayList<HashMap<String, String>> list,String attr){


        Map<String, ArrayList<HashMap<String, String>>> groupedlist = new HashMap<String, ArrayList<HashMap<String, String>>>();


        for (HashMap<String, String> each:list) {
            String attrVal=each.get(attr);

            if (groupedlist.get(attrVal) == null) {
                groupedlist.put(attrVal, new ArrayList<HashMap<String, String>>());
            }
            groupedlist.get(attrVal).add(each);

        }
        return groupedlist;
    }
}
