package dipu.testmodule;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.SeekBar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

/**
 * Created by Deepak on 11-Dec-17.
 */

public class ReportActivity extends  AppCompatActivity  {

    private PieChart mChart;
    private DBHelper dbHelper;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        dbHelper=new DBHelper(this);
        mChart = findViewById(R.id.pieChart1);
        entries = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        PieEntryLabels = new ArrayList<String>();

        generatePieData();
        pieDataSet = new PieDataSet(entries, "");

        pieData = new PieData(PieEntryLabels, pieDataSet);
        int[] COLORFUL_COLORS ={ Color.rgb(34,139,34),Color.rgb(255, 0, 0)};
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

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String query="Select * from Questionair";
        Cursor cursor=db.rawQuery(query,new String[]{});

        if (cursor.getCount()>0){
            cursor.moveToLast();

            entries.add(new BarEntry(Float.parseFloat(cursor.getString(cursor.getColumnIndex("Obtained"))), 0));
            entries.add(new BarEntry(Float.parseFloat(cursor.getString(cursor.getColumnIndex("Outof")))-Float.parseFloat(cursor.getString(cursor.getColumnIndex("Obtained"))), 1));

            PieEntryLabels.add("Right Answers");
            PieEntryLabels.add("Wrong Answers");

        }
        cursor.close();

    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Test Report");
        s.setSpan(new RelativeSizeSpan(2f), 0, 11, 0);
        return s;
    }
}
