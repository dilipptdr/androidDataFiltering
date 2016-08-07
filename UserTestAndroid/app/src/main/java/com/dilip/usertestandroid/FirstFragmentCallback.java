package com.dilip.usertestandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dilippatidar on 06/08/16.
 */
public interface FirstFragmentCallback {

    public void didReturnGroupedData(String gpName,Map<String, ArrayList<HashMap<String, String>>> gpedData);

}

