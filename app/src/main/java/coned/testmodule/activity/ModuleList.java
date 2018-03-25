package coned.testmodule.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import coned.testmodule.R;
import coned.testmodule.beans.AreaManager;
import coned.testmodule.beans.LoginResponse;
import coned.testmodule.beans.Module;
import coned.testmodule.controllers.CommonController;
import coned.testmodule.helper.Alerts;

/**
 * Created by Deepak on 08-Feb-18.
 */

public class ModuleList extends BaseActivity {

    ListView listView;
    AlertDialog alertDialog;
    ArrayList<Module>moduleList;
    String[] module;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.module_list,mBaseFrameContainer);
        listView=findViewById(R.id.listview);
        moduleList=new ArrayList<>();
        getModule();
        // Defined Array values to show in ListView
        module = new String[] {};
    }

    private void getModule() {
        if (getNetworkState()) {
            startLoading_1("Loading...");
            CommonController.getInstance().getModule( SuccessListener(), ErrorListener());
        }else {

            tost.displayToastLONG("Check internet connection");
        }
    }

    public Response.Listener<JSONArray> SuccessListener() {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    stopLoading_1();

                    moduleList = new Gson().fromJson(response.toString(), new TypeToken<List<Module>>() {

                    }.getType());

                    module = new String[moduleList.size()];

                    for (int i = 0; i < moduleList.size(); i++) {

                        module[i] = moduleList.get(i).getModule_name();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ModuleList.this,
                            R.layout.spinneradapter, android.R.id.text1, module);


                    // Assign adapter to ListView
                    listView.setAdapter(adapter);

                    // ListView Item Click Listener
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                            Intent intent=new Intent(ModuleList.this,QuestionairActivity.class);
                            intent.putExtra("module_id",moduleList.get(position).getId());
                            startActivity(intent);

                        }

                    });
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        };
    }

    private Response.ErrorListener ErrorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stopLoading_1();

                try {
                    hideProgressBar();
                    DialogInterface.OnClickListener retryBTN = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    };

                    if (error instanceof NetworkError) {
                        alertDialog = Alerts.internetConnectionErrorAlert(ModuleList.this, retryBTN);
                    } else if (error instanceof ServerError) {
                        tost.displayToastLONG("Server error");
                    } else if (error instanceof NoConnectionError) {
                        tost.displayToastLONG("Unable to connect server !");
                    } else if (error instanceof TimeoutError) {
                        alertDialog = Alerts.timeoutErrorAlert(ModuleList.this, retryBTN);
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
}
