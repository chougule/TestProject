package dipu.testmodule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    String UserType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button profile=findViewById(R.id.btn_profile);
        Button detail=findViewById(R.id.btn_detail);

        Intent intent=getIntent();
        if (intent.hasExtra("User_Type")){

            UserType=intent.getStringExtra("User_Type");

        }
        if (UserType.equals("Medical Representative")){

            detail.setText("Attend Test");
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,UserProfileActivity.class);
                intent.putExtra("User_Type",UserType);
                startActivity(intent);
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (UserType.equals("Medical Representative")){

                    startActivity(new Intent(MainActivity.this,ModuleList.class));
                    finish();
                }else if (UserType.equals("Manager")){

                    Intent intent=new Intent(MainActivity.this,ManagerActivity.class);
                    intent.putExtra("User_Type","Manager");
                    startActivity(intent);
                    finish();
                }else if (UserType.equals("Area Manager")){

                    Intent intent=new Intent(MainActivity.this,ManagerActivity.class);
                    intent.putExtra("User_Type","Area Manager");
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
