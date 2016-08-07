package com.dilip.usertestandroid;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dilippatidar on 06/08/16.
 */
public interface DataLoaderCallback {

    public void didFinishLoadingData(boolean success,ArrayList<HashMap<String, String>> users);
}
