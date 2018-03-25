package coned.testmodule.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import coned.managers.SpManager;
import coned.testmodule.R;
import coned.testmodule.controllers.ControllerManager;

public class MainActivity extends AppCompatActivity {

    String UserType;
    private LinearLayout layout;
    private View questionair, report;
    SpManager spManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spManager = ControllerManager.getInstance().getSpManager();
        Button profile = findViewById(R.id.btn_profile);
        Button detail = findViewById(R.id.btn_detail);
        questionair = findViewById(R.id.check_que);
        report = findViewById(R.id.check_report);
        layout = findViewById(R.id.ll_mr);
        final Intent intent = getIntent();
        if (intent.hasExtra("User_Type")) {

            UserType = intent.getStringExtra("User_Type");
        }

        if (UserType.equals("Medical_Representative")) {

            layout.setVisibility(View.VISIBLE);
            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(MainActivity.this, EmployeeDetailsActivity.class);
                    intent.putExtra("Name", spManager.getFullname());
                    startActivity(intent1);
                }
            });
            questionair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(MainActivity.this, ModuleList.class);
                    startActivity(intent1);
                }
            });
        } else {
            layout.setVisibility(View.GONE);
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                    intent.putExtra("User_Type", UserType);
                    startActivity(intent);
                }
            });

            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (UserType.equals("Medical_Representative")) {

                        startActivity(new Intent(MainActivity.this, ModuleList.class));
                    } else if (UserType.equals("Manager")) {

                        Intent intent = new Intent(MainActivity.this, ManagerActivity.class);
                        intent.putExtra("User_Type", "Manager");
                        startActivity(intent);
                    } else if (UserType.equals("Area Manager")) {

                        Intent intent = new Intent(MainActivity.this, ManagerActivity.class);
                        intent.putExtra("User_Type", "Area Manager");
                        startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:

                System.exit(0);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
