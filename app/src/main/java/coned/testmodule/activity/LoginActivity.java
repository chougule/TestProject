package coned.testmodule.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import coned.managers.SpManager;
import coned.testmodule.R;
import coned.testmodule.beans.LoginResponse;
import coned.testmodule.controllers.CommonController;
import coned.testmodule.controllers.ControllerManager;
import coned.testmodule.helper.Alerts;
import coned.testmodule.helper.AppConstants;

import static coned.testmodule.helper.AppConstants.BASE_URL;

public class LoginActivity extends BaseActivity {

    ProgressBar progressBar;
    EditText username,password;
    TextView register,forgotpass;
    Button login;
    String[] user = {"Select User Type", "Regional Manager", "Area Manager", "Medical Representative"};
    String UserType = "Select User Type";
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        Intent intent = getIntent();
        if (intent.hasExtra("user_type")) {
            UserType = intent.getStringExtra("user_type");
            String tmp = "User Type : " + intent.getStringExtra("user_type");
            user = new String[]{tmp};
            /*ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneradapter, user);
            aa.setDropDownViewResource(R.layout.spinneradapter);
            spinner.setAdapter(aa);
            spinner.setClickable(false);
            spinner.setEnabled(false);*/
        }
    }

    private void init() {

        progressBar=findViewById(R.id.progress);
        register=findViewById(R.id.link_signup);
        //spinner = findViewById(R.id.spin_user);
        password = findViewById(R.id.edt_password);
        login = findViewById(R.id.btnlogin);
        username=findViewById(R.id.edt_username);
        forgotpass=findViewById(R.id.tv_forgot_password);
        forgotpass.setPaintFlags(forgotpass.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneradapter, user);
        aa.setDropDownViewResource(R.layout.spinneradapter);
       // spinner.setAdapter(aa);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPassword();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,UserEntryActivity.class);
                startActivity(intent);
            }
        });
        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    UserType = user[i];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (username.getText().toString().isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Enter User Name", Toast.LENGTH_LONG).show();
                    username.requestFocus();

                }else if (password.getText().toString().isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_LONG).show();
                    password.requestFocus();

                } else {
                    new SendPostRequest().execute();
                    //loginUser();
                }
            }
        });
    }

    private void ForgotPassword(){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_password, null);
        final EditText email=dialogView.findViewById(R.id.edt_email);
        dialogBuilder.setTitle("Forgot Password");
        dialogBuilder.setMessage("Password will send on registered email id");
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();

        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (email.getText().toString().isEmpty()){

                    email.setError("Enter registered email id");

                }else {

                    tost.displayToastLONG("Password sent on your registered email");
                }
                dialog.cancel();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBuilder.show();
        dialogBuilder.setCancelable(false);
    }

    private void loginUser() {
        if (getNetworkState()) {
            Map<String, String> map = new HashMap<>();
            map.put("username", username.getText().toString());
            map.put("password", password.getText().toString());
            CommonController.getInstance().loginUser(map, reqLoginSuccessListener(), reqLoginErrorListener());
        }else {

            tost.displayToastLONG("Check internet connection");
        }
    }

    public Response.Listener<LoginResponse> reqLoginSuccessListener() {
        hideProgressBar();
        return new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {

                hideProgressBar();
                Log.d("##########resp",response.toString());
                tost.displayToastLONG("Login Successful");
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        };
    }

    private Response.ErrorListener reqLoginErrorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();

                try {
                    hideProgressBar();
                    DialogInterface.OnClickListener retryBTN = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            loginUser();
                        }
                    };

                    if (error instanceof NetworkError) {
                        alertDialog = Alerts.internetConnectionErrorAlert(LoginActivity.this, retryBTN);
                    } else if (error instanceof ServerError) {
                        tost.displayToastLONG("Server error");
                    } else if (error instanceof NoConnectionError) {
                        tost.displayToastLONG("Unable to connect server !");
                    } else if (error instanceof TimeoutError) {
                        alertDialog = Alerts.timeoutErrorAlert(LoginActivity.this, retryBTN);
                    } else if (error instanceof ParseError) {

                        loginUser();

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

    public class SendPostRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){
            startLoading_1("Loading...");
        }

        protected String doInBackground(String... arg0) {

            try{
                String LOGIN= AppConstants.BASE_URL+"auth/login";
                URL url = new URL(LOGIN);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("username", username.getText().toString());
                postDataParams.put("password", password.getText().toString());
                Log.e("params",postDataParams.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                stopLoading_1();
                if (!result.equals("false : 400")) {
                    JSONObject jsonObject = new JSONObject(result);

                    String userid = jsonObject.getString("userid");
                    String user_name = jsonObject.getString("user name");
                    String mobile = jsonObject.getString("mobile");
                    String token = jsonObject.getString("token");
                    String user_type = jsonObject.getString("user_type");
                    SpManager spManager = ControllerManager.getInstance().getSpManager();
                    spManager.setUserEmail(username.getText().toString());
                    spManager.setMobileNumber(mobile);
                    spManager.setUsertype(user_type);
                    spManager.setFullname(user_name);
                    spManager.setToken(token);
                    Log.d("##########resp",result.toString());
                    tost.displayToastLONG("Login Successful");
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("User_Type",user_type);
                    startActivity(intent);
                }else {
                    tost.displayToastLONG("Invalid User Name or Password");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}