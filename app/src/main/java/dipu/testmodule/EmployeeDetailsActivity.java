package dipu.testmodule;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

import dipu.testmodule.beans.Employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;


public class EmployeeDetailsActivity extends AppCompatActivity {

    private PieChart mChart;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;
    TextView name;
    String strname = "";
    Employee emp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.emloyee_details);

        emp=new Employee();
        Intent intent=getIntent();
        if (intent.hasExtra("Name")){
            
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

                }else {
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

        name=findViewById(R.id.tv_empname);
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

    private void CreatePdf(){

    String FILE = Environment.getExternalStorageDirectory().toString()
            + "/PDF/" + "Name.pdf";

    // Add Permission into Manifest.xml
    // <uses-permission
    // android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

    // Create New Blank Document
    Document document = new Document(PageSize.A4);

    // Create Directory in External Storage
    String root = Environment.getExternalStorageDirectory().toString();
    File myDir = new File(root + "/PDF");
		myDir.mkdirs();

    // Create Pdf Writer for Writting into New Created Document
		try {
        PdfWriter.getInstance(document, new FileOutputStream(FILE));

        // Open Document for Writting into document
        document.open();

        // User Define Method
        addMetaData(document);
        addTitlePage(document);
    } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (DocumentException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    // Close Document after writting all content
		document.close();

		Toast.makeText(this, "PDF File is Created. Location : " + FILE,
    Toast.LENGTH_LONG).show();

		ShareFile();
}

    private void ShareFile() {

        /*Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {});
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Test Report");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent,"Send"));*/

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        String FILE = Environment.getExternalStorageDirectory().toString()
                + "/PDF/" + "Name.pdf";

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

    // Set PDF document Properties
    public void addMetaData(Document document)

    {
        document.addTitle("RESUME");
        document.addSubject("Person Info");
        document.addKeywords("Personal,	Education, Skills");
        document.addAuthor("TAG");
        document.addCreator("TAG");
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
        prHead.add("RESUME – Name\n");

        // Create Table into Document with 1 Row
        PdfPTable myTable = new PdfPTable(1);
        // 100.0f mean width of table is same as Document size
        myTable.setWidthPercentage(100.0f);

        // Create New Cell into Table
        PdfPCell myCell = new PdfPCell(new Paragraph(""));
        myCell.setBorder(Rectangle.BOTTOM);

        // Add Cell into Table
        myTable.addCell(myCell);

        prHead.setFont(catFont);
        prHead.add("\nName1 Name2\n");
        prHead.setAlignment(Element.ALIGN_CENTER);

        // Add all above details into Document
        document.add(prHead);
        document.add(myTable);

        document.add(myTable);

        // Now Start another New Paragraph
        Paragraph prPersinalInfo = new Paragraph();
        prPersinalInfo.setFont(smallBold);
        prPersinalInfo.add("Address 1\n");
        prPersinalInfo.add("Address 2\n");
        prPersinalInfo.add("City: SanFran. State: CA\n");
        prPersinalInfo.add("Country: USA Zip Code: 000001\n");
        prPersinalInfo
                .add("Mobile: 9999999999 Fax: 1111111 Email: john_pit@gmail.com \n");

        prPersinalInfo.setAlignment(Element.ALIGN_CENTER);

        document.add(prPersinalInfo);
        document.add(myTable);

        document.add(myTable);

        Paragraph prProfile = new Paragraph();
        prProfile.setFont(smallBold);
        prProfile.add("\n \n Profile : \n ");
        prProfile.setFont(normal);
        prProfile
                .add("\nI am Mr. XYZ. I am Android Application Developer at TAG.");

        prProfile.setFont(smallBold);
        document.add(prProfile);

        // Create new Page in PDF
        document.newPage();
    }
}