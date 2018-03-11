package dipu.testmodule.controllers;

import android.graphics.Region;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import dipu.testmodule.beans.Area;
import dipu.testmodule.beans.LoginResponse;
import dipu.testmodule.helper.AppConstants;
import dipu.testmodule.volley.GsonRequest;

public class CommonController {

    private static CommonController commonController;

    public static synchronized CommonController getInstance() {
        if (commonController == null) {
            commonController = new CommonController();
        }
        return commonController;
    }

    public void getRegionList( Response.Listener successListener, Response.ErrorListener errorListener) {

        try {

            GsonRequest<Region> region_list = new GsonRequest<Region>(Request.Method.POST, AppConstants.REGIONLIST, Region.class, null, successListener, errorListener);
            ApplicationController.getInstance().addToRequestQueue(region_list, "regionlist");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAreaList( Response.Listener successListener, Response.ErrorListener errorListener) {
        try {

            GsonRequest<Area> area_list = new GsonRequest<Area>(Request.Method.POST, AppConstants.AREALIST, Area.class, null, successListener, errorListener);
            ApplicationController.getInstance().addToRequestQueue(area_list, "arealist");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginUser( Response.Listener successListener, Response.ErrorListener errorListener) {
        try {

            GsonRequest<LoginResponse> login = new GsonRequest<LoginResponse>(Request.Method.POST, AppConstants.AREALIST, LoginResponse.class, null, successListener, errorListener);
            ApplicationController.getInstance().addToRequestQueue(login, "login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}