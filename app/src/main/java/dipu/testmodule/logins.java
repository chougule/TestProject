package dipu.testmodule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class logins extends AppCompatActivity {

    Spinner spinner;
    EditText password;
    Button login;
    String[] user = {"Select User Type", "Manager", "Area Manager", "Employee"};
    String UserType = "Select User Type";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        init();
    }

    private void init() {

        spinner = findViewById(R.id.spin_user);
        password = findViewById(R.id.edt_password);
        login = findViewById(R.id.btnlogin);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, user);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                UserType = user[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveInSP();
                if (UserType.equals("Select User Type")) {

                    Toast.makeText(logins.this, "Select User Type", Toast.LENGTH_LONG).show();
                    spinner.requestFocus();

                } else if (password.getText().toString().isEmpty()) {

                    Toast.makeText(logins.this, "Enter Password", Toast.LENGTH_LONG).show();
                    password.requestFocus();

                } else {

                    if (UserType.equals("Employee")) {

                        startActivity(new Intent(logins.this, ModuleList.class));
                        finish();
                    } else if (UserType.equals("Manager")) {

                        Intent intent = new Intent(logins.this, ManagerActivity.class);
                        intent.putExtra("User_Type", "Manager");
                        startActivity(intent);
                        finish();
                    } else if (UserType.equals("Area Manager")) {

                        Intent intent = new Intent(logins.this, ManagerActivity.class);
                        intent.putExtra("User_Type", "Area Manager");
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private void saveInSP() {

        SharedPreferences sharedPref = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Name", "Deepak");
        editor.putString("Email", "Deepak@gmail.com");
        editor.putString("Mobile", "9999999999");
        editor.putString("City", "Pune");
        editor.putString("Manager", "Vishal Patil");
        editor.putString("Area", "Kothrud");
        editor.commit();
        editor.apply();

    }
}