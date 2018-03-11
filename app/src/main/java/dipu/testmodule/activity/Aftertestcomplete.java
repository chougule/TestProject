package dipu.testmodule.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import dipu.testmodule.R;

public class Aftertestcomplete extends BaseActivity {

    String Obtained, Outof, TotalTime;
    private PieChart mChart;
    private DBHelper dbHelper;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;
    TextView date,timetaken;

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
                Snackbar snackbar=Snackbar.make(view, "Share this Test Report", Snackbar.LENGTH_LONG)
                        .setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("plain/text");
                                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {});
                                intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Test Report");
                                intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                                startActivity(Intent.createChooser(intent,"Send"));
                            }
                        });
                snackbar.setActionTextColor(Color.RED);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("Obtained")) {

            Obtained = intent.getStringExtra("Obtained");
            Outof = intent.getStringExtra("Outof");
            TotalTime = intent.getStringExtra("TotalTime");
            Log.d("TotalTime",TotalTime);
        }
        mChart = findViewById(R.id.exam_piechart);
        date=findViewById(R.id.tv_datime);
        timetaken=findViewById(R.id.tv_timetaken);
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

        SetDataTime();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_share:

                if (ActivityCompat.checkSelfPermission(
                        Aftertestcomplete.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Aftertestcomplete.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

                } else {
                    CreatePdf();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void CreatePdf() {

        Document document = new Document(PageSize.A4);
        File path = new File( Environment.getExternalStorageDirectory(), "Pdf" );
        if ( !path.exists() )
        { path.mkdir(); }
        File file = new File(path, "graph.pdf");

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addTitlePage(document);
            addGraphImage();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        document.close();
        ShareFile();
    }

    private void addGraphImage() {
    }

    private void ShareFile() {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        String FILE = Environment.getExternalStorageDirectory().toString()
                + "/PDF/" + "graph.pdf";

        File root = Environment.getExternalStorageDirectory();
        String pathToMyAttachedFile = "temp/attachement.xml";
        File file = new File(FILE);
        if (!file.exists() || !file.canRead()) {
            return;
        }
        Uri uri = Uri.fromFile(file);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(emailIntent, "Send"));
    }

    public void addTitlePage(Document document) throws DocumentException {
        // Font Style for Document
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD
                | Font.UNDERLINE, BaseColor.GRAY);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

        // Start New Paragraph
        Paragraph prHead = new Paragraph();
        // Set Font in this Paragraph
        prHead.setFont(titleFont);
        // Add item into Paragraph
        prHead.add("Employee Report\n");

        Paragraph imgTable = new Paragraph();
        // 100.0f mean width of table is same as Document size

        // Create New Cell into Table
        PdfPCell myCell = new PdfPCell(new Paragraph(""));
        myCell.setBorder(Rectangle.BOTTOM);

        PdfPCell imgcell = new PdfPCell(new Paragraph());
        prHead.setFont(catFont);
        prHead.add("\nCompany Name Pvt. ltd.\n");
        prHead.add("\n");
        prHead.setAlignment(Element.ALIGN_CENTER);

        // Add all above details into Document
        document.add(prHead);

        try {
           /* Bitmap bm=mChart.getChartBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
            Image myImg = Image.getInstance(getBytesFromBitmapChunk(bm));
            myImg.setAlignment(Image.MIDDLE);
            imgTable.setAlignment(Element.ALIGN_CENTER);
            document.add(myImg);*/

            PdfPTable table = new PdfPTable(2);
            table.setWidths(new int[]{1, 9});
            Image img = Image.getInstance(GraphImage());
            table.addCell(new PdfPCell(img, true));
            document.add(table);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Now Start another New Paragraph
        Paragraph prPersinalInfo = new Paragraph();
        prPersinalInfo.setFont(smallBold);
        prPersinalInfo.add("\n");
        prPersinalInfo.add("Employee Name : " + "Pankaj Patil" + "\n");
        prPersinalInfo.add("Employee Address : " + "Pune" + "\n");
        prPersinalInfo.add("Employee Designation : " + "Human Resources" + "\n");
        prPersinalInfo.add("Test Status : " + "Complete" + "\n");
        prPersinalInfo.add("Marks Obtained : " + Obtained + "\n");
        prPersinalInfo.add("Time Required : " + TotalTime + "\n");
        prPersinalInfo.add("\n");

        prPersinalInfo.setAlignment(Element.ALIGN_LEFT);

        document.add(prPersinalInfo);
        document.newPage();
    }

    public String GraphImage() {

        String file="/storage/emulated/0/DCIM/test/graph.png";
        mChart.saveToGallery("graph.png", "test", "", Bitmap.CompressFormat.PNG, 85);

        return file;
    }

    private void SetDataTime() {

        Calendar c = Calendar.getInstance();

        int seconds = c.get(Calendar.SECOND);
        int minutes = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR);
        String stime = hour + ":" + minutes + ":" + seconds;


        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String sdate = day + "/" + month + "/" + year;

        date.setText(sdate+"\n"+stime);
        timetaken.setText(TotalTime);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        startActivity(new Intent(Aftertestcomplete.this,ModuleList.class));
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Aftertestcomplete.this,ModuleList.class));
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
