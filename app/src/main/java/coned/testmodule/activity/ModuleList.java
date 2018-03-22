package coned.testmodule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import coned.testmodule.R;

/**
 * Created by Deepak on 08-Feb-18.
 */

public class ModuleList extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_list);
        listView=findViewById(R.id.listview);

        // Defined Array values to show in ListView
        String[] values = new String[] { "HR Module",
                "Marketing Module",
                "Doctor Module",
                "Nurse Module",
                "MR Module",
                "Admin Module",
                "IT Module"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinneradapter, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                startActivity(new Intent(ModuleList.this,QuestionairActivity.class));

            }

        });
    }

}
