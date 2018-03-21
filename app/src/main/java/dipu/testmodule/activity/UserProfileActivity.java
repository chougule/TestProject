package dipu.testmodule.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import dipu.managers.SpManager;
import dipu.testmodule.R;
import dipu.testmodule.controllers.ControllerManager;

public class UserProfileActivity extends BaseActivity {

    TextView name,email,mobile,address,designation,reportmgr,area;
    LinearLayout linearLayout_mgr,ll_area;
    String UserType;
    SpManager spManager;

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
        Setdata();

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