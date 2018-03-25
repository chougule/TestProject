package coned.testmodule.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import coned.managers.SpManager;
import coned.testmodule.R;
import coned.testmodule.beans.LoginResponse;
import coned.testmodule.controllers.CommonController;
import coned.testmodule.controllers.ControllerManager;
import coned.testmodule.helper.Alerts;

public class UserProfileActivity extends BaseActivity {

    TextView name,email,mobile,address,designation,reportmgr,area;
    LinearLayout linearLayout_mgr,ll_area;
    String UserType;
    SpManager spManager;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_userprofile,mBaseFrameContainer);

        Intent intent= getIntent();
        UserType=intent.getStringExtra("User_Type");

        name=findViewById(R.id.profile_name);
        email=findViewById(R.id.profile_email);
        mobile=findViewById(R.id.profile_number);
        address=findViewById(R.id.profile_address);
        designation=findViewById(R.id.profile_designation);
        reportmgr=findViewById(R.id.profile_report_mgr);
        area=findViewById(R.id.profile_area);
        linearLayout_mgr=findViewById(R.id.ll_reportmgr);
        ll_area=findViewById(R.id.ll_area);

        getUserDetails();
        Setdata();

    }

    private void getUserDetails() {

        if (getNetworkState()) {
            Map<String, String> map = new HashMap<>();

            CommonController.getInstance().viewProfile( SuccessListener(), ErrorListener());
        }else {

            tost.displayToastLONG("Check internet connection");
        }
    }

    public Response.Listener<LoginResponse> SuccessListener() {
        hideProgressBar();
        return new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {

                hideProgressBar();
                Log.d("##########resp",response.toString());
                tost.displayToastLONG("Login Successful");
                Intent intent=new Intent(UserProfileActivity.this,MainActivity.class);
                startActivity(intent);
            }
        };
    }

    private Response.ErrorListener ErrorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();

                try {
                    hideProgressBar();
                    DialogInterface.OnClickListener retryBTN = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            getUserDetails();
                        }
                    };

                    if (error instanceof NetworkError) {
                        alertDialog = Alerts.internetConnectionErrorAlert(UserProfileActivity.this, retryBTN);
                    } else if (error instanceof ServerError) {
                        tost.displayToastLONG("Server error");
                    } else if (error instanceof NoConnectionError) {
                        tost.displayToastLONG("Unable to connect server !");
                    } else if (error instanceof TimeoutError) {
                        alertDialog = Alerts.timeoutErrorAlert(UserProfileActivity.this, retryBTN);
                    } else if (error instanceof ParseError) {

                        getUserDetails();

                    } else {

                        JSONObject jsonObject = new JSONObject(error.getMessage());
                        tost.displayToastSHORT(jsonObject.getString("error"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void Setdata() {
        spManager= ControllerManager.getInstance().getSpManager();

        name.setText(spManager.getFullname());
        email.setText(spManager.getUserEmail());
        mobile.setText(spManager.getMobileNumber() );
        address.setText("Pune");
        designation.setText(spManager.getUsertype());
        if (UserType.equals("Medical Representative")) {

            reportmgr.setText(spManager.getReportMgr());
            area.setText(spManager.getArea());

        }else if (UserType.equals("Regional Manager")){

            reportmgr.setText(spManager.getReportMgr());
        }else {

            linearLayout_mgr.setVisibility(View.GONE);
            ll_area.setVisibility(View.GONE);
        }
    }
}