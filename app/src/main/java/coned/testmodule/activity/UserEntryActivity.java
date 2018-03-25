package coned.testmodule.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coned.managers.SpManager;
import coned.testmodule.R;
import coned.testmodule.beans.Area;
import coned.testmodule.beans.AreaManager;
import coned.testmodule.beans.Register;
import coned.testmodule.controllers.CommonController;
import coned.testmodule.controllers.ControllerManager;
import coned.testmodule.helper.Alerts;

public class UserEntryActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    SpManager spManager;
    View view_city, view_mgr, view_area;
    RadioGroup radioGroup;
    EditText name, email, mobile, password;
    Spinner spin_region, spin_manager, spin_area;
    TextView tv_area_mgr, tv_area;
    Button submit;
    String[] region;
    String[] areaManager;
    String[] area;
    String region_id, area_mgr_id, area_id;
    ArrayList<coned.testmodule.beans.Region> regionList;
    ArrayList<AreaManager> ListMgr;
    ArrayList<Area> ListArea;
    ArrayAdapter adapter_region;
    ArrayAdapter adapter_mgr;
    ArrayAdapter adapter_area;
    String user_type = "";
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_userentry, mBaseFrameContainer);

        init();
        getRegionList();
        /*GenerateSpinnerData();
        SetSpinnerAdapter();*/
    }

    private void saveInSP() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        spManager = ControllerManager.getInstance().getSpManager();
        spManager.setUsertype(user_type);
        spManager.setFullname(name.getText().toString().trim());
        spManager.setUserEmail(email.getText().toString().trim());
        spManager.setMobileNumber(mobile.getText().toString().trim());
        if (spin_manager.getVisibility() == View.VISIBLE)
            spManager.setReportMgr(spin_manager.getSelectedItem().toString());
        if (spin_area.getVisibility() == View.VISIBLE)
            spManager.setArea(spin_area.getSelectedItem().toString());
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

    private void init() {

        //skip = findViewById(R.id.tv_entry_skip);
        radioGroup = findViewById(R.id.radio_gp_entry);
        name = findViewById(R.id.edt_entry_name);
        email = findViewById(R.id.edt_entry_email);
        mobile = findViewById(R.id.edt_entry_mobile);
        spin_region = findViewById(R.id.spin_entry_city);
        spin_manager = findViewById(R.id.spin_entry_mgr);
        spin_area = findViewById(R.id.spin_entry_area);
        submit = findViewById(R.id.btn_entry_submit);
        view_city = findViewById(R.id.view_city);
        view_mgr = findViewById(R.id.view_mgr);
        view_area = findViewById(R.id.view_area);
        tv_area_mgr = findViewById(R.id.tv_area_mgr);
        tv_area = findViewById(R.id.tv_area);
        spin_area.setOnItemSelectedListener(this);
        spin_manager.setOnItemSelectedListener(this);
        spin_region.setOnItemSelectedListener(this);
        //skip.setOnClickListener(this);

        areaManager = new String[]{};
        area = new String[]{};

        submit.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {

                    case R.id.radio_region_mgr:
                        user_type = "Regional Manager";
                        spin_region.setSelection(0);
                        tv_area.setVisibility(View.GONE);
                        tv_area_mgr.setVisibility(View.GONE);
                        view_area.setVisibility(View.GONE);
                        view_mgr.setVisibility(View.GONE);
                        spin_manager.setVisibility(View.GONE);
                        spin_area.setVisibility(View.GONE);
                        break;

                    case R.id.radio_areamgr:
                        user_type = "Area Manager";
                        spin_region.setSelection(0);
                        spin_manager.setVisibility(View.VISIBLE);
                        spin_area.setVisibility(View.GONE);
                        view_area.setVisibility(View.GONE);
                        view_mgr.setVisibility(View.VISIBLE);
                        tv_area_mgr.setVisibility(View.VISIBLE);
                        /*adapter_mgr = new ArrayAdapter(UserEntryActivity.this, R.layout.spinneradapter, ListMgr);
                        adapter_mgr.setDropDownViewResource(R.layout.spinneradapter);
                        spin_manager.setAdapter(adapter_mgr);*/
                        break;

                    case R.id.radio_represent:
                        user_type = "Medical Representative";
                        spin_region.setSelection(0);
                        tv_area.setVisibility(View.VISIBLE);
                        spin_manager.setVisibility(View.VISIBLE);
                        spin_area.setVisibility(View.VISIBLE);
                        view_city.setVisibility(View.VISIBLE);
                        view_area.setVisibility(View.VISIBLE);
                        view_mgr.setVisibility(View.VISIBLE);
                        /*adapter_area = new ArrayAdapter(UserEntryActivity.this, R.layout.spinneradapter, ListArea);
                        adapter_area.setDropDownViewResource(R.layout.spinneradapter);
                        area.setAdapter(adapter_area);
                        adapter_mgr = new ArrayAdapter(UserEntryActivity.this, R.layout.spinneradapter, ListAreaMgr);
                        adapter_mgr.setDropDownViewResource(R.layout.spinneradapter);
                        manager.setAdapter(adapter_mgr);*/
                        break;
                }
            }
        });
    }

    public final static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_entry_submit:
                if (Validate()) {
                    showProgressBar();
                    register();
                    /*saveInSP();
                    Intent intent = new Intent(UserEntryActivity.this, LoginActivity.class);
                    intent.putExtra("user_type", user_type);
                    startActivity(intent);
                    finish();*/
                }
                break;
        }

    }

    public void getRegionList() {

        if (getNetworkState()) {
            showProgressBar();
            CommonController.getInstance().getRegion(reqRegionSuccessListener(), reqRegionErrorListener());

        } else {

            tost.displayToastLONG("Please check internet connection");
        }

    }

    public void getAreaList(String areaMgr_id) {

        if (getNetworkState()) {

            CommonController.getInstance().getAreaList(areaMgr_id, reqAreaSuccessListener(), reqAreaErrorListener());

        } else {

            tost.displayToastLONG("Please check internet connection");
        }
    }

    public void getAreaManager(String region_id) {

        showProgressBar();
        if (getNetworkState()) {

            CommonController.getInstance().getAreaManager(region_id, reqAreaMgrSuccessListener(), reqAreaMgrErrorListener());
        } else {

            tost.displayToastLONG("Please check internet connection");
        }

    }

    public void register() {

        if (getNetworkState()) {
            Map<String, String> map = new HashMap<>();
            map.put("user_type", user_type);
            map.put("full_name", name.getText().toString());
            map.put("email", email.getText().toString());
            map.put("mobileno", mobile.getText().toString());
            map.put("region", "");
            map.put("area", "");
            map.put("password", "Pass1234");
            map.put("under_user", "");
            CommonController.getInstance().register(map, registerSuccessListener(), registerErrorListener());
        } else {

            tost.displayToastLONG("Please check internet connection");
        }

    }

    public Response.Listener<Register> registerSuccessListener() {
        hideProgressBar();
        return new Response.Listener<Register>() {
            @Override
            public void onResponse(Register response) {

                hideProgressBar();
                tost.displayToastLONG("Register Successful");
                Intent intent = new Intent(UserEntryActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };
    }

    private Response.ErrorListener registerErrorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();

                try {
                    hideProgressBar();
                    DialogInterface.OnClickListener retryBTN = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            register();
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

                        register();

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

    public Response.Listener<JSONArray> reqAreaSuccessListener() {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    hideProgressBar();
                    ListArea = new Gson().fromJson(response.toString(), new TypeToken<List<Area>>() {

                    }.getType());
                    area = new String[ListArea.size()];
                    for (int i = 0; i < ListArea.size(); i++) {

                        area[i] = ListArea.get(i).getArea_name();
                    }

                    adapter_area = new ArrayAdapter(UserEntryActivity.this, R.layout.spinneradapter, area);
                    adapter_area.setDropDownViewResource(R.layout.spinneradapter);
                    spin_area.setAdapter(adapter_area);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        };
    }

    public Response.ErrorListener reqAreaErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();

                try {
                    hideProgressBar();
                    DialogInterface.OnClickListener retryBTN = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
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
                        getRegionList();

                    } else {

                        JSONObject jsonObject = new JSONObject(error.getMessage());
                        if (jsonObject.has("result"))
                            tost.displayToastSHORT(jsonObject.getString("result"));
                        else
                            tost.displayToastSHORT("something went wrong");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Response.ErrorListener reqRegionErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();

                try {
                    hideProgressBar();
                    DialogInterface.OnClickListener retryBTN = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getRegionList();
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
                        getRegionList();

                    } else {

                        JSONObject jsonObject = new JSONObject(error.getMessage());
                        if (jsonObject.has("result"))
                            tost.displayToastSHORT(jsonObject.getString("result"));
                        else
                            tost.displayToastSHORT("something went wrong");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Response.Listener<JSONArray> reqRegionSuccessListener() {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    hideProgressBar();
                    regionList = new Gson().fromJson(response.toString(), new TypeToken<List<coned.testmodule.beans.Region>>() {

                    }.getType());
                    region = new String[regionList.size()];

                    for (int i = 0; i < regionList.size(); i++) {

                        region[i] = regionList.get(i).getReg_name();
                    }

                    adapter_region = new ArrayAdapter(UserEntryActivity.this, R.layout.spinneradapter, region);
                    adapter_region.setDropDownViewResource(R.layout.spinneradapter);
                    spin_region.setAdapter(adapter_region);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        };
    }

    public Response.Listener<JSONArray> reqAreaMgrSuccessListener() {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    hideProgressBar();

                    ListMgr = new Gson().fromJson(response.toString(), new TypeToken<List<AreaManager>>() {

                    }.getType());
                    areaManager = new String[ListMgr.size()];

                    for (int i = 0; i < ListMgr.size(); i++) {

                        areaManager[i] = ListMgr.get(i).getName();
                    }

                    adapter_mgr = new ArrayAdapter(UserEntryActivity.this, R.layout.spinneradapter, areaManager);
                    adapter_mgr.setDropDownViewResource(R.layout.spinneradapter);
                    spin_manager.setAdapter(adapter_mgr);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        };
    }

    private Response.ErrorListener reqAreaMgrErrorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();

                try {
                    hideProgressBar();
                    DialogInterface.OnClickListener retryBTN = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

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
        } else if (isValidEmail(email.getText().toString())) {
            email.setError("Please Enter Valid Email");
            check = false;
        } else if (mobile.getText().toString().isEmpty()) {
            mobile.setError("Please Enter Mobile Number");
            check = false;
        } else if (regionList.size() == 0) {

            Toast.makeText(UserEntryActivity.this, "Please Select Region", Toast.LENGTH_SHORT).show();
            check = false;

        } else if (spin_manager.getVisibility() == View.VISIBLE) {
            if (ListMgr.size() == 0) {
                Toast.makeText(UserEntryActivity.this, "Please Select Manager", Toast.LENGTH_SHORT).show();
                check = false;
            } else if (spin_area.getVisibility() == View.VISIBLE) {
                if (ListArea.size() == 0) {
                    Toast.makeText(UserEntryActivity.this, "Please Select Area", Toast.LENGTH_SHORT).show();
                    check = false;
                }
            }
        }

        return check;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()) {

            case R.id.spin_entry_city:

                region_id = regionList.get(i).getRegion_id();
                getAreaManager(regionList.get(i).getRegion_id());
                area = new String[0];
                adapter_area = new ArrayAdapter(UserEntryActivity.this, R.layout.spinneradapter, area);
                adapter_area.setDropDownViewResource(R.layout.spinneradapter);
                spin_area.setAdapter(adapter_area);

                break;

            case R.id.spin_entry_mgr:

                area_mgr_id = ListMgr.get(i).getId();
                getAreaList(area_mgr_id);


                break;
            case R.id.spin_entry_area:

                area_id = ListArea.get(i).getArea_id();

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}