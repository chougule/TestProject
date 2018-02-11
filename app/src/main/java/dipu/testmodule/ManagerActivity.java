package dipu.testmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class ManagerActivity extends AppCompatActivity {

    Spinner manager,employee;
    ListView listView;
    ArrayAdapter a;
    String[]  managervalues= new String[] { "Select Area Manager","Kothrud Manager 1",
            "Hadpsar Manager",
            "Katraj Manager",
            "Hadpsar Manager",
            "Chinchwad Manager",
            "Hinjawadi Manager",

    };

    String[] empvalues = new String[] { "Select Employee","Ganesh Lagad",
            "Pankaj Shinde",
            "Rohit Patil",
            "Vishal Dhole",
            "Vijay Chavan",
            "John Disuja",
            "Vinay Patil",
            "Raj Chougule"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.manager);
        init();
    }

    private void init() {

        manager=findViewById(R.id.spin_areamanager);
        employee=findViewById(R.id.spin_employee);

       // listView = (ListView) findViewById(R.id.list);



        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,managervalues);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        manager.setAdapter(aa);

        final ArrayAdapter a = new ArrayAdapter(this,android.R.layout.simple_spinner_item,empvalues);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        manager.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i!=0)
                employee.setAdapter(a);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        employee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i!=0){

                    Intent intent=new Intent(ManagerActivity.this,EmployeeDetailsActivity.class);
                    intent.putExtra("Name",empvalues[i]);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Intent intent=getIntent();
        if (intent!=null){

            String User=intent.getStringExtra("User_Type");
            if (User.equals("Area Manager")) {
                manager.setVisibility(View.INVISIBLE);
                employee.setAdapter(a);
            }
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
}