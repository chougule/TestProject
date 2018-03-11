package dipu.testmodule.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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
import java.nio.ByteBuffer;
import java.util.ArrayList;

import dipu.testmodule.R;
import dipu.testmodule.beans.Employee;


public class EmployeeDetailsActivity extends BaseActivity {

    ArrayList<Entry> entries;
    ArrayList<String> PieEntryLabels;
    PieDataSet pieDataSet;
    PieData pieData;
    TextView name;
    String strname = "";
    Employee emp;
    private PieChart mChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.emloyee_details);

        emp = new Employee();
        Intent intent = getIntent();
        if (intent.hasExtra("Name")) {

            emp.setName(intent.getStringExtra("Name"));
        }
        init();
        GetEmpDetails();

    }

    private void GetEmpDetails() {

        emp.setId("1");
        emp.setAddress("Kothrud,Pune,411001");
        emp.setAge("30");
        emp.setAttempt_question("5/7");
        emp.setDesignatin("Human Resources");
        emp.setGendar("Male");
        emp.setMarks("7/7");
        emp.setTest_time("1 min");
        emp.setTest_status("Complete");

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
                        EmployeeDetailsActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EmployeeDetailsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

                } else {
                    CreatePdf();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void CheckPermission() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (ActivityCompat.checkSelfPermission(EmployeeDetailsActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                CreatePdf();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name = findViewById(R.id.tv_empname);
        name.setText(strname);
        mChart = findViewById(R.id.pieChart_employee);
        Displaypiechart();
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
    }

    private void generatePieData() {

        entries.add(new BarEntry(40, 0));
        entries.add(new BarEntry(60, 1));

        PieEntryLabels.add("Correct Answer");
        PieEntryLabels.add("Wrong Answer");

    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Test Score");
        s.setSpan(new RelativeSizeSpan(2f), 0, 10, 0);
        return s;
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
        prPersinalInfo.add("Employee Name : " + emp.getName() + "\n");
        prPersinalInfo.add("Employee Address : " + emp.getAddress() + "\n");
        prPersinalInfo.add("Employee Designation : " + emp.getDesignatin() + "\n");
        prPersinalInfo.add("Test Status : " + emp.getTest_status() + "\n");
        prPersinalInfo.add("Marks Obtained : " + emp.getMarks() + "\n");
        prPersinalInfo.add("Time Required : " + emp.getTest_time() + "\n");
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

    /*public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }*/

    private byte[] getBytesFromBitmapChunk(Bitmap bitmap) {
        int bitmapSize = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmapSize);
        bitmap.copyPixelsToBuffer(byteBuffer);
        byteBuffer.rewind();
        return byteBuffer.array();
    }
}