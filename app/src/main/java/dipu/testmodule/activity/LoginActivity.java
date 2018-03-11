package dipu.testmodule.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Region;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import dipu.testmodule.R;
import dipu.testmodule.controllers.CommonController;
import dipu.testmodule.helper.Alerts;


public class LoginActivity extends BaseActivity {

    Spinner spinner;
    EditText username,password;
    Button login;
    String[] user = {"Select User Type", "Regional Manager", "Area Manager", "Medical Representative"};
    String UserType = "Select User Type";
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        init();

        Intent intent = getIntent();
        if (intent.hasExtra("user_type")) {
            UserType = intent.getStringExtra("user_type");
            String tmp = "User Type : " + intent.getStringExtra("user_type");
            user = new String[]{tmp};
            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneradapter, user);
            aa.setDropDownViewResource(R.layout.spinneradapter);
            spinner.setAdapter(aa);
            spinner.setClickable(false);
            spinner.setEnabled(false);
        }
    }

    private void init() {

        spinner = findViewById(R.id.spin_user);
        password = findViewById(R.id.edt_password);
        login = findViewById(R.id.btnlogin);
        username=findViewById(R.id.edt_username);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneradapter, user);
        aa.setDropDownViewResource(R.layout.spinneradapter);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    UserType = user[i];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (UserType.equals("Select User Type")) {

                    Toast.makeText(LoginActivity.this, "Select User Type", Toast.LENGTH_LONG).show();
                    spinner.requestFocus();

                } else if (username.getText().toString().isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Enter User Name", Toast.LENGTH_LONG).show();
                    username.requestFocus();

                }else if (password.getText().toString().isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_LONG).show();
                    password.requestFocus();

                } else {

                    if (UserType.equals("Medical Representative")) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("User_Type", "Medical Representative");
                        startActivity(intent);
                        finish();
                    } else if (UserType.equals("Area Manager")) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("User_Type", "Area Manager");
                        startActivity(intent);
                        finish();
                    } else if (UserType.equals("Regional Manager")) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("User_Type", "Manager");
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private void loginUser() {

        CommonController.getInstance().loginUser(reqLoginSuccessListener(), reqLoginErrorListener());
    }

    public Response.Listener<Region> reqLoginSuccessListener() {
        hideProgressBar();
        return new Response.Listener<Region>() {
            @Override
            public void onResponse(Region response) {

                hideProgressBar();
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