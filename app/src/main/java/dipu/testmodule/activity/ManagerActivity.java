package dipu.testmodule.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import dipu.testmodule.R;

public class ManagerActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    Spinner manager,employee;
    private PieChart mChart;
    private DBHelper dbHelper;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;
    String User,Chart_Selected="";

    ArrayAdapter a;
    String[]  managervalues= new String[] { "Select Area Manager","Kothrud Manager 1",
            "Hadpsar Manager",
            "Katraj Manager",
            "Hadpsar Manager",
            "Chinchwad Manager",
            "Hinjawadi Manager",

    };

    String[] empvalues_attempt = new String[] { "Select Employee","Ganesh Lagad",
            "Pankaj Shinde",
            "Rohit Patil",
            "Vishal Dhole",
            "Vijay Chavan",
            "John Disuja",
            "Vinay Patil",
            "Raj Chougule"
    };

    String[] empvalues_notattempt = new String[] {
        "Select Employee",
                "Ganesh Koli",
                "Pankaj Gaji",
                "Ajay Lathi",
                "Vishal Lamba",
                "Vijay Chavan",
                "John",
                "Vinay Patil",
                "Raj Chougule",
                "Rohit Patil"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.manager);
        init();
    }
    private void init() {

        manager = findViewById(R.id.spin_areamanager);
        employee = findViewById(R.id.spin_employee);
        mChart = findViewById(R.id.manager_piechart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {

            User = intent.getStringExtra("User_Type");
            if (User.equals("Area Manager")) {
                manager.setVisibility(View.INVISIBLE);
            }else {

                mChart.setVisibility(View.GONE);
            }
        }

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, managervalues);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        manager.setAdapter(aa);

        manager.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0){
                    mChart.setVisibility(View.VISIBLE);
                   Displaypiechart();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        employee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    if (Chart_Selected.equals("Test_Attempt")) {

                        Intent intent = new Intent(ManagerActivity.this, EmployeeDetailsActivity.class);
                        intent.putExtra("Name", empvalues_attempt[i]);
                        startActivity(intent);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(User.equals("Area Manager")){

            Displaypiechart();
        }

    }

    private void Displaypiechart() {
        PieEntryLabels = new ArrayList<String>();
        entries = new ArrayList<>();

        generatePieData();
        pieDataSet = new PieDataSet(entries, "");

        pieData = new PieData(PieEntryLabels, pieDataSet);
        int[] COLORFUL_COLORS = {Color.rgb(34, 139, 34), Color.rgb(255, 0, 0)};
        pieDataSet.setColors(COLORFUL_COLORS);
        mChart.setCenterText(generateCenterText());
        mChart.setDescription("");
        mChart.setData(pieData);
        mChart.animateY(3000);
        mChart.setOnChartValueSelectedListener(this);
    }


    private void generatePieData() {

        entries.add(new BarEntry(40, 0));
        entries.add(new BarEntry(60, 1));

        PieEntryLabels.add("Test Attempted");
        PieEntryLabels.add("Test Not Attempted");

    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Test Report");
        s.setSpan(new RelativeSizeSpan(2f), 0, 11, 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", index: " + h.getDataSetIndex()
                        + ", DataSet index: " + h.getDataSetIndex());
        if (e.getXIndex()==0){

            final ArrayAdapter a = new ArrayAdapter(this, android.R.layout.simple_spinner_item, empvalues_attempt);
            a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            employee.setAdapter(a);
            Chart_Selected="Test_Attempt";

        }else {

            final ArrayAdapter a = new ArrayAdapter(this, android.R.layout.simple_spinner_item, empvalues_notattempt);
            a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            employee.setAdapter(a);
            Chart_Selected="Test_NotAttempt";

        }
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }





        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int itemPosition     = position;
                String  itemValue    = (String) listView.getItemAtPosition(position);



            }

        });*/

}