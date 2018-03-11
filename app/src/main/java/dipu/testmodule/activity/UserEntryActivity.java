package dipu.testmodule.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Region;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;

import dipu.managers.SpManager;
import dipu.testmodule.R;
import dipu.testmodule.beans.Area;
import dipu.testmodule.controllers.CommonController;
import dipu.testmodule.controllers.ControllerManager;
import dipu.testmodule.helper.Alerts;

public class UserEntryActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    SpManager spManager;
    TextView skip;
    RadioGroup radioGroup;
    EditText name, email, mobile;
    Spinner city, manager, area;
    Button submit;
    String[] region;
    ArrayList<String> ListMgr;
    ArrayList<String> ListAreaMgr;
    ArrayList<String> ListArea;
    ArrayAdapter adapter_city;
    ArrayAdapter adapter_mgr;
    ArrayAdapter adapter_area;
    String user_type = "Regional Manager";
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_userentry);

        init();
        HideToolbar();
        GenerateSpinnerData();
        SetSpinnerAdapter();
    }

    private void saveInSP() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        spManager = ControllerManager.getInstance().getSpManager();
        spManager.setUsertype(user_type);
        spManager.setFullname(name.getText().toString().trim());
        spManager.setUserEmail(email.getText().toString().trim());
        spManager.setMobileNumber(mobile.getText().toString().trim());
        if (manager.getVisibility()==View.VISIBLE)
            spManager.setReportMgr(manager.getSelectedItem().toString());
        if (area.getVisibility()==View.VISIBLE)
            spManager.setArea(area.getSelectedItem().toString());
        /*SharedPreferences sharedPref = getSharedPreferences("MyPREFERENCES",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Name", );
        editor.putString("Email",);
        editor.putString("Mobile",);
        editor.putString("City", String.valueOf(city.getSelectedItem()));
        editor.putString("Manager",manager.getVisibility()==View.GONE?"":String.valueOf(manager.getSelectedItem()));
        editor.putString("Area",area.getVisibility()==View.GONE?"":String.valueOf(area.getSelectedItem()));
        editor.commit();
        editor.apply();*/

    }

    private void SetSpinnerAdapter() {
        region = getResources().getStringArray(R.array.region);
        adapter_city = new ArrayAdapter(this, R.layout.spinneradapter, region);
        adapter_city.setDropDownViewResource(R.layout.spinneradapter);
        city.setAdapter(adapter_city);
    }

    private void GenerateSpinnerData() {

        ListMgr = new ArrayList<>();
        ListAreaMgr = new ArrayList<>();
        ListArea = new ArrayList<>();

        ListMgr.add("Select Regional Manager");
        ListMgr.add("Akash Agrawal");
        ListMgr.add("Bharat Magdum");
        ListMgr.add("Pavan Kumar");

        ListAreaMgr.add("Select Area Manager");
        ListAreaMgr.add("Pavan Naik");
        ListAreaMgr.add("Harshal Khot");
        ListAreaMgr.add("Sumit Patil");
        ListAreaMgr.add("Bharat Godse");

        ListArea.add("Select Area");
        ListArea.add("Katraj");
        ListArea.add("Hadpsar");
        ListArea.add("Chnichwad");
        ListArea.add("Kothrud");
        ListArea.add("Kharadi");
    }

    private void init() {

        skip = findViewById(R.id.tv_entry_skip);
        radioGroup = findViewById(R.id.radio_gp_entry);
        name = findViewById(R.id.edt_entry_name);
        email = findViewById(R.id.edt_entry_email);
        mobile = findViewById(R.id.edt_entry_mobile);
        city = findViewById(R.id.spin_entry_city);
        manager = findViewById(R.id.spin_entry_mgr);
        area = findViewById(R.id.spin_entry_area);
        submit = findViewById(R.id.btn_entry_submit);

        skip.setOnClickListener(this);
        submit.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {

                    case R.id.radio_region_mgr:
                        user_type = "Regional Manager";
                        city.setSelection(0);
                        manager.setVisibility(View.GONE);
                        area.setVisibility(View.GONE);
                        break;

                    case R.id.radio_areamgr:
                        user_type = "Area Manager";
                        city.setSelection(0);
                        manager.setVisibility(View.VISIBLE);
                        area.setVisibility(View.GONE);
                        adapter_mgr = new ArrayAdapter(UserEntryActivity.this, R.layout.spinneradapter, ListMgr);
                        adapter_mgr.setDropDownViewResource(R.layout.spinneradapter);
                        manager.setAdapter(adapter_mgr);
                        break;

                    case R.id.radio_represent:
                        user_type = "Medical Representative";
                        city.setSelection(0);
                        manager.setVisibility(View.VISIBLE);
                        area.setVisibility(View.VISIBLE);
                        adapter_area = new ArrayAdapter(UserEntryActivity.this, R.layout.spinneradapter, ListArea);
                        adapter_area.setDropDownViewResource(R.layout.spinneradapter);
                        area.setAdapter(adapter_area);
                        adapter_mgr = new ArrayAdapter(UserEntryActivity.this, R.layout.spinneradapter, ListAreaMgr);
                        adapter_mgr.setDropDownViewResource(R.layout.spinneradapter);
                        manager.setAdapter(adapter_mgr);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_entry_skip:
                startActivity(new Intent(UserEntryActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.btn_entry_submit:
                if (Validate()) {

                    saveInSP();
                    Intent intent = new Intent(UserEntryActivity.this, LoginActivity.class);
                    intent.putExtra("user_type", user_type);
                    startActivity(intent);
                    finish();
                }
                break;
        }

    }

    public void getRegionList() {

        CommonController.getInstance().getRegionList(reqRegionSuccessListener(), reqRegionErrorListener());
    }

    public void getAreaList() {

        CommonController.getInstance().getAreaList(reqAreaSuccessListener(), reqAreaErrorListener());
    }

    public Response.Listener<Region> reqRegionSuccessListener() {
        hideProgressBar();
        return new Response.Listener<Region>() {
            @Override
            public void onResponse(Region response) {

                hideProgressBar();
            }
        };
    }

    private Response.ErrorListener reqRegionErrorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();

                try {
                    hideProgressBar();
                    DialogInterface.OnClickListener retryBTN = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            getAreaList();
                        }
                    };

                    if (error instanceof NetworkError) {
                        alertDialog = Alerts.internetConnectionErrorAlert(UserEntryActivity.this, retryBTN);
                    } else if (error instanceof ServerError) {
                        tost.displayToastLONG("Server error");
                    } else if (error instanceof NoConnectionError) {
                        tost.displayToastLONG("Unable to connect server !");
                    } else if (error instanceof TimeoutError) {
                        alertDialog = Alerts.timeoutErrorAlert(UserEntryActivity.this, retryBTN);
                    } else if (error instanceof ParseError) {

                        getAreaList();

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

    public Response.Listener<Area> reqAreaSuccessListener() {
        hideProgressBar();
        return new Response.Listener<Area>() {
            @Override
            public void onResponse(Area response) {

                hideProgressBar();

            }
        };
    }

    private Response.ErrorListener reqAreaErrorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();

                try {
                    hideProgressBar();
                    DialogInterface.OnClickListener retryBTN = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            getAreaList();
                        }
                    };

                    if (error instanceof NetworkError) {
                        alertDialog = Alerts.internetConnectionErrorAlert(UserEntryActivity.this, retryBTN);
                    } else if (error instanceof ServerError) {
                        tost.displayToastLONG("Server error");
                    } else if (error instanceof NoConnectionError) {
                        tost.displayToastLONG("Unable to connect server !");
                    } else if (error instanceof TimeoutError) {
                        alertDialog = Alerts.timeoutErrorAlert(UserEntryActivity.this, retryBTN);
                    } else if (error instanceof ParseError) {

                        getAreaList();

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

    private boolean Validate() {

        boolean check = true;

        if (name.getText().toString().isEmpty()) {
            name.setError("Please Enter Name");
            check = false;
        } else if (email.getText().toString().isEmpty()) {
            email.setError("Please Enter Email");
            check = false;
        } else if (mobile.getText().toString().isEmpty()) {
            mobile.setError("Please Enter Mobile Number");
            check = false;
        } else if (city.getSelectedItemPosition() == 0) {

            Toast.makeText(UserEntryActivity.this, "Please Select City", Toast.LENGTH_SHORT).show();
            check = false;

        } else if (manager.getVisibility() == View.VISIBLE) {
            if (manager.getSelectedItemPosition() == 0) {
                Toast.makeText(UserEntryActivity.this, "Please Select Manager", Toast.LENGTH_SHORT).show();
                check = false;
            } else if (area.getVisibility() == View.VISIBLE) {
                if (area.getSelectedItemPosition() == 0) {
                    Toast.makeText(UserEntryActivity.this, "Please Select Area", Toast.LENGTH_SHORT).show();
                    check = false;
                }
            }
        }

        return check;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Log.d("Iddd", String.valueOf(view.getId()));

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}