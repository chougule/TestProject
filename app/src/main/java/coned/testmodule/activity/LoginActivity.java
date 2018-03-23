package coned.testmodule.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import coned.testmodule.R;
import coned.testmodule.beans.LoginResponse;
import coned.testmodule.controllers.CommonController;
import coned.testmodule.helper.Alerts;

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
                    loginUser();
                }
            }
        });
    }

    private void ForgotPassword(){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.CustomDialog);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_password, null);
        final EditText email=dialogView.findViewById(R.id.edt_email);
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
}