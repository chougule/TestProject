package dipu.testmodule;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

public class Aftertestcomplete extends AppCompatActivity {

    String Obtained, Outof, TotalTime;
    private PieChart mChart;
    private DBHelper dbHelper;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftertestcomplete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("Obtained")) {

            Obtained = intent.getStringExtra("Obtained");
            Outof = intent.getStringExtra("Outof");
            TotalTime = intent.getStringExtra("TotalTime");
        }
        mChart = findViewById(R.id.exam_piechart);
        entries = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        PieEntryLabels = new ArrayList<String>();

        generatePieData();
        pieDataSet = new PieDataSet(entries, "");

        pieData = new PieData(PieEntryLabels, pieDataSet);
        int[] COLORFUL_COLORS = {Color.rgb(34, 139, 34), Color.rgb(255, 0, 0)};
        pieDataSet.setColors(COLORFUL_COLORS);
        mChart.setCenterText(generateCenterText());
        mChart.setDescription("");
        mChart.setData(pieData);
        mChart.animateY(3000);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void generatePieData() {

            entries.add(new BarEntry(Float.parseFloat(Obtained), 0));
            entries.add(new BarEntry(Float.parseFloat(Outof) - Float.parseFloat(Obtained), 1));

            PieEntryLabels.add("Right Answers");
            PieEntryLabels.add("Wrong Answers");

    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Test Report");
        s.setSpan(new RelativeSizeSpan(2f), 0, 11, 0);
        return s;
    }
}
