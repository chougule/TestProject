package coned.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class SpManager {

    // Sharedpref file name
    private static final String PREF_NAME = "AmazeLife_SP";
    // All Shared Preferences Keys
    private static final String IS_LOGGED_IN = "true";
    private static final String USER_NAME = "userName";
    private static final String USER_PASSWORD = "password";
    private static final String USER_EMAIL = "useremail";
    private static final String FULL_NAME = "fullname";
    private static final String USER_MOBILE = "usermobile";
    private static final String USER_type = "usertype";
    private static final String REPORT_MGR = "report_manager";
    private static final String AREA = "area";
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    public SpManager(Context mContext) {
        this._context = mContext;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    //All Methods

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setUserPrefernces(boolean isLoggedIn, String username, String password, String userEmail,
                                  String fullname, String mobile, String type) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        try {
            editor.putString(USER_NAME, username);
            editor.putString(USER_PASSWORD, password);
            editor.putString(USER_EMAIL, userEmail);
            editor.putString(FULL_NAME, fullname);
            editor.putString(USER_MOBILE, mobile);
            editor.putString(USER_type, type);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setReportMgr(String reportMgr)
    {
        try {
            editor.putString(REPORT_MGR,reportMgr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public String getReportMgr ()
    {
        try {
            return pref.getString(REPORT_MGR,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setArea(String area)
    {
        try {
            editor.putString(AREA,area);

        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public String getArea ()
    {
        try {
            return pref.getString(AREA,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setUsername(String username)
    {
        try {
            editor.putString(USER_NAME,username);

        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public String getUsername ()
    {
        try {
            return pref.getString(USER_NAME,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setUserpassword(String userpassword)
    {
        try {
            editor.putString(USER_PASSWORD,userpassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public String getPassword ()
    {
        try {
            return pref.getString(USER_PASSWORD,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setUserEmail(String userEmail)
    {
        try {
            editor.putString(USER_EMAIL,userEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public String getUserEmail ()
    {
        try {
            return pref.getString(USER_EMAIL,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setMobileNumber(String mobileNumber)
    {
        try {
            editor.putString(USER_MOBILE,mobileNumber);

        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public String getMobileNumber ()
    {
        try {
            return pref.getString(USER_MOBILE,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setFullname(String fullname)
    {
        try {
            editor.putString(FULL_NAME,fullname);

        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public String getFullname ()
    {
        try {
            return pref.getString(FULL_NAME,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setUsertype(String usertype)
    {
        try {
            editor.putString(USER_type,usertype);

        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public String getUsertype ()
    {
        try {
            return pref.getString(USER_type,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}