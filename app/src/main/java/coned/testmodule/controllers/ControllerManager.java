package coned.testmodule.controllers;

import android.content.Context;

import coned.managers.SpManager;

public class ControllerManager {

    private static ControllerManager controllerManager=null;

    private Context context;
    SpManager spManager;

    private ControllerManager(Context context)
    {
        this.context=context;
        initManagers(context);
    }

    public static ControllerManager getInstance() {
        if (null == controllerManager) {
            System.out.println("getInstance + ControllerManager ");
            throw new ExceptionInInitializerError();
        }
        return controllerManager;
    }

    // Should be called only Once
    public static ControllerManager createInstance(Context context) {
        if (null != controllerManager) {
            System.out.println("createInstance");
            throw new ExceptionInInitializerError();
        }

        synchronized (ControllerManager.class) {
            if (null != controllerManager) {
                throw new ExceptionInInitializerError();
            }
            controllerManager = new ControllerManager(context);
            return controllerManager;
        }
    }

    private void initManagers(Context mContext) {
        //initialize classes here
        spManager=new SpManager(mContext);
    }

    public SpManager getSpManager() {

        return spManager;
    }



}