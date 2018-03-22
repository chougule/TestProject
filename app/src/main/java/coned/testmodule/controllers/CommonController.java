package coned.testmodule.controllers;

import android.graphics.Region;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import coned.testmodule.beans.Area;
import coned.testmodule.beans.LoginResponse;
import coned.testmodule.helper.AppConstants;
import coned.testmodule.volley.CustomJsonArrayRequest;
import coned.testmodule.volley.GsonRequest;

public class CommonController {

    private static CommonController commonController;

    public static synchronized CommonController getInstance() {
        if (commonController == null) {
            commonController = new CommonController();
        }
        return commonController;
    }

    /*public void getRegionList( Response.Listener successListener, Response.ErrorListener errorListener) {

        try {

            GsonRequest<Region> region_list = new GsonRequest<Region>(Request.Method.POST, AppConstants.REGIONLIST, Region.class, null, successListener, errorListener);
            ApplicationController.getInstance().addToRequestQueue(region_list, "regionlist");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void getAreaList( Response.Listener successListener, Response.ErrorListener errorListener) {
        try {

            GsonRequest<Area> area_list = new GsonRequest<Area>(Request.Method.POST, AppConstants.AREALIST, Area.class, null, successListener, errorListener);
            ApplicationController.getInstance().addToRequestQueue(area_list, "arealist");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginUser(HashMap<String, String> map, Response.Listener successListener, Response.ErrorListener errorListener) {
        try {

            GsonRequest<LoginResponse> login = new GsonRequest<LoginResponse>(Request.Method.POST, AppConstants.AREALIST, LoginResponse.class, null, successListener, errorListener);
            ApplicationController.getInstance().addToRequestQueue(login, "login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRegion(String url, Response.Listener successListener, Response.ErrorListener errorListener) {
        /*Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", spManager.getToken());*/
        CustomJsonArrayRequest getPrepaidOprators = new CustomJsonArrayRequest(url, successListener, errorListener);
        ApplicationController.getInstance().addToRequestQueue(getPrepaidOprators, "getRegion");

    }
}