package coned.testmodule.controllers;

import android.graphics.Region;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import coned.testmodule.beans.Area;
import coned.testmodule.beans.AreaManager;
import coned.testmodule.beans.LoginResponse;
import coned.testmodule.beans.Register;
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

    public void register(Map<String,String>map, Response.Listener successListener, Response.ErrorListener errorListener) {

        try {

            GsonRequest<Register> region_list = new GsonRequest<Register>(Request.Method.POST, AppConstants.REGISTER ,Register.class, null, successListener, errorListener,map);
            ApplicationController.getInstance().addToRequestQueue(region_list, "regionlist");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAreaManager(String region_id, Response.Listener successListener, Response.ErrorListener errorListener) {

        try {

            GsonRequest<AreaManager> region_list = new GsonRequest<AreaManager>(Request.Method.POST, AppConstants.AREAMANAGER+region_id, AreaManager.class, null, successListener, errorListener);
            ApplicationController.getInstance().addToRequestQueue(region_list, "regionlist");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAreaList(String areaMgr_id, Response.Listener successListener, Response.ErrorListener errorListener) {
        try {

            GsonRequest<Area> area_list = new GsonRequest<Area>(Request.Method.POST, AppConstants.AREALIST+"/areaMgr_id", Area.class, null, successListener, errorListener);
            ApplicationController.getInstance().addToRequestQueue(area_list, "arealist");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginUser(Map<String, String> map, Response.Listener successListener, Response.ErrorListener errorListener) {
        try {

            GsonRequest<LoginResponse> login = new GsonRequest<LoginResponse>(Request.Method.POST, AppConstants.LOGIN, LoginResponse.class, null, successListener, errorListener,map);
            ApplicationController.getInstance().addToRequestQueue(login, "login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRegion( Response.Listener successListener, Response.ErrorListener errorListener) {
        /*Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", spManager.getToken());*/
        CustomJsonArrayRequest getPrepaidOprators = new CustomJsonArrayRequest(AppConstants.REGIONLIST, successListener, errorListener);
        ApplicationController.getInstance().addToRequestQueue(getPrepaidOprators, "getRegion");

    }
}