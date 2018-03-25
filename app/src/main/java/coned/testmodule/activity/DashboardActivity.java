package coned.testmodule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import coned.managers.SpManager;
import coned.testmodule.R;
import coned.testmodule.controllers.ControllerManager;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView name,email;
    String UserType;
    private LinearLayout layout;
    private View questionair, report;
    SpManager spManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        name=drawer.findViewById(R.id.tv_name);
        email=drawer.findViewById(R.id.tv_empname);

        spManager= ControllerManager.getInstance().getSpManager();

        name.setText(spManager.getFullname());
        email.setText(spManager.getUserEmail());
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
        Menu nav_Menu = navigationView.getMenu();

        if (UserType.equals("Medical_Representative")) {
            nav_Menu.findItem(R.id.nav_test).setVisible(true);
            layout.setVisibility(View.VISIBLE);
            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(DashboardActivity.this, EmployeeDetailsActivity.class);
                    intent.putExtra("Name", spManager.getFullname());
                    startActivity(intent1);
                }
            });
            questionair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(DashboardActivity.this, ModuleList.class);
                    startActivity(intent1);
                }
            });
        } else {
            nav_Menu.findItem(R.id.nav_test).setVisible(false);
            layout.setVisibility(View.GONE);
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(DashboardActivity.this, UserProfileActivity.class);
                    intent.putExtra("User_Type", UserType);
                    startActivity(intent);
                }
            });

            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (UserType.equals("Medical_Representative")) {

                        startActivity(new Intent(DashboardActivity.this, ModuleList.class));
                    } else if (UserType.equals("Manager")) {

                        Intent intent = new Intent(DashboardActivity.this, ManagerActivity.class);
                        intent.putExtra("User_Type", "Manager");
                        startActivity(intent);
                    } else if (UserType.equals("Area Manager")) {

                        Intent intent = new Intent(DashboardActivity.this, ManagerActivity.class);
                        intent.putExtra("User_Type", "Area Manager");
                        startActivity(intent);
                    }
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.exit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            System.exit(0);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_report) {

        } else if (id == R.id.nav_logout) {

        } else if (id == R.id.nav_test) {

        } else if (id == R.id.nav_aboutus) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
