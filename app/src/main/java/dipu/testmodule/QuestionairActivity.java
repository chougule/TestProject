package dipu.testmodule;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dipu.testmodule.beans.Questionair;

/**
 * Created by Deepak on 11-Dec-17.
 */

public class QuestionairActivity extends AppCompatActivity {

    DBHelper mydb;
    TextView Que_no,Que,Option_1,Option_2,Option_3,Option_4,Countdown;
    RadioButton opt_1,opt_2,opt_3,opt_4;
    Button Submit,Next;
    CountDownTimer ct;
    ArrayList<Questionair> ListQuestion;
    int Answer;
    long remaincount=31000;
    long CountDownTimer=31000;
    private long TotalTime=0;
    int CorrectAnswer=0;
    int Question=0;
    int time=30;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionair);

        init();
        DetailsAlert();

    }

    private void DetailsAlert() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setMessage(getResources().getString(R.string.info));
        dialogBuilder.setTitle("Test Details");
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


                dialog.cancel();
                Countdown();
                GenerateQuestionar();
                HandleQuestionair();

            }
        });
         AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        b.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CountDownTimer=remaincount;
        StopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CountDownTimer=remaincount;
        //ct.start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Countdown();
    }

    public void Countdown() {
        ct=new CountDownTimer(CountDownTimer, 1000) {

            public void onTick(long millisUntilFinished) {

                Countdown.setText(String.valueOf( time));
                time--;
                CountdownAnimation();
                remaincount=millisUntilFinished;
            }

            public void onFinish() {

                if (Question+1==ListQuestion.size())
                {
                    InsertIntoDB();
                    Alert("Completed Questionair","Thanks for participation!");

                }else {

                    CountDownTimer=30001;
                    Countdown.setText("done!");
                    TotalTime = TotalTime + 30001;
                    SubmitQuestion();
                    NextQuestion();
                    Countdown();
                }
            }

        }.start();
    }
    private void HandleQuestionair() {

        Que_no.setText(String.valueOf(Question+1)+"/"+ListQuestion.size());
        Que.setText(ListQuestion.get(Question).getQuestion());
        Option_1.setText(ListQuestion.get(Question).getOptions().get(0));
        Option_2.setText(ListQuestion.get(Question).getOptions().get(1));
        Option_3.setText(ListQuestion.get(Question).getOptions().get(2));
        Option_4.setText(ListQuestion.get(Question).getOptions().get(3));

        opt_1.setChecked(false);
        opt_2.setChecked(false);
        opt_3.setChecked(false);
        opt_4.setChecked(false);
        Submit.setText("Submit");
        Submit.setTextColor(getResources().getColor(R.color.black));

        YoYo.with(Techniques.SlideInRight)
                .duration(700)
                .playOn(findViewById(R.id.cardview));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init() {
        mydb = new DBHelper(this);

        List<Questionair> a=new ArrayList<>();
        Que_no=findViewById(R.id.tv_queno);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Que=findViewById(R.id.tv_que);
        Option_1=findViewById(R.id.option_1);
        Option_2=findViewById(R.id.option_2);
        Option_3=findViewById(R.id.option_3);
        Option_4=findViewById(R.id.option_4);
        Countdown=findViewById(R.id.tv_countdown);

        opt_1 =findViewById(R.id.radio_1);
        opt_2=findViewById(R.id.radio_2);
        opt_3=findViewById(R.id.radio_3);
        opt_4=findViewById(R.id.radio_4);

        Submit=findViewById(R.id.submit);
        Next=findViewById(R.id.card_Next);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Validate()){
                    UpdateTotalTimeTaken();
                    StopTimer();

                    if (Question+1==ListQuestion.size())
                    {
                        InsertIntoDB();
                        CorrectAnswer++;
                        Alert("Completed Questionair","Thanks for participation!");
                    }
                    else {

                        if(ChechCorrectAnswer()){

                            Submit.setText("Correct");
                            Submit.setTextColor(getResources().getColor(R.color.green));
                            CorrectAnswer++;

                        } else {

                            Alert("Wrong Answer","The Correct answer is "+ListQuestion.get(Question).getAnswerDescription());
                        }

                        SubmitQuestion();
                    }
                }
            }
        });


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NextQuestion();
                StartTimer();

            }
        });

        opt_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){

                    Answer=0;
                    opt_2.setChecked(false);
                    opt_3.setChecked(false);
                    opt_4.setChecked(false);
                }
            }
        });

        opt_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    Answer=1;
                    opt_1.setChecked(false);
                    opt_3.setChecked(false);
                    opt_4.setChecked(false);
                }
            }
        });

        opt_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){

                    Answer=2;
                    opt_2.setChecked(false);
                    opt_4.setChecked(false);
                    opt_1.setChecked(false);
                }
            }
        });

        opt_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){

                    Answer=3;
                    opt_2.setChecked(false);
                    opt_3.setChecked(false);
                    opt_1.setChecked(false);
                }
            }
        });

    }

    private void NavigateToReport() {

        Intent intent=new Intent(QuestionairActivity.this,Aftertestcomplete.class);
        intent.putExtra("Obtained",String.valueOf(CorrectAnswer));
        intent.putExtra("Outof",String.valueOf(ListQuestion.size()));
        intent.putExtra("TotalTime",SecToMin(TotalTime));
        startActivity(intent);
    }

    private String SecToMin(long seconds){
        Log.d("sec",String.valueOf(seconds));
            long s = seconds % 60;
            long m = (seconds / 60) % 60;
            long h = (seconds / (60 * 60)) % 24;
            return String.format("%d:%02d:%02d", h,m,s);
    }

    private void CountdownAnimation(){

        /*YoYo.with(Techniques.TakingOff)
                .duration(1000)
                .playOn(findViewById(R.id.tv_countdown));*/
    }

    private void StartTimer()
    {
        if (ct!=null) {
            ct.start();
        }
    }

    private void StopTimer() {
        if (ct!=null) {
            ct.cancel();
        }
    }

    private void UpdateTotalTimeTaken() {

        TotalTime=TotalTime+remaincount;
    }

    private void SubmitQuestion(){

        Question++;
        Submit.setClickable(false);
        Next.setVisibility(View.VISIBLE);

    }

    private void NextQuestion(){

        time=30;
        Next.setVisibility(View.GONE);
        Submit.setClickable(true);
        HandleQuestionair();
    }

    private void FinishTest(){

    }

    private boolean ChechCorrectAnswer(){
        boolean Check=false;

        if (Answer == ListQuestion.get(Question).getAnswer()) {

            Check=true;
        }
        return Check;
    }

    private void InsertIntoDB() {

        SQLiteDatabase db=mydb.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Obtained",String.valueOf(CorrectAnswer));
        cv.put("Outof",String.valueOf(ListQuestion.size()));
        cv.put("TotalTime",TotalTime);
        db.insert("Questionair",null,cv);
    }

    private void Alert(final String Title , String Anser) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        /*LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);*/


        dialogBuilder.setTitle(Title);
        dialogBuilder.setMessage(Anser);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if(Title.equals("Completed Questionair")){

                    NavigateToReport();

                }
                dialog.cancel();
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.setCanceledOnTouchOutside(false);
        b.show();

    }

    private boolean Validate() {

        boolean Check =true;
        if (opt_1.isChecked()==false&&opt_2.isChecked()==false&&opt_3.isChecked()==false&&opt_4.isChecked()==false){

            Toast.makeText(this,"Please Select the Correct answer",Toast.LENGTH_SHORT).show();
            Check=false;
        }
        return Check;
    }

    private void GenerateQuestionar() {

        ListQuestion = new ArrayList<>();
        Questionair questionair=new Questionair();
        questionair.setQuestion_id("1");
        questionair.setQuestion("What is Android");
        ArrayList<String> option=new ArrayList<>();
        option.add("Mobile Company");
        option.add("Operating System");
        option.add("Device");
        option.add("Type of Computer");
        questionair.setOptions(option);
        questionair.setCorrectAnswer("Operating System");
        questionair.setAnswerDescription("Android is Open Source linux based operating system");
        questionair.setAnswer(1);
        ListQuestion.add(questionair);

        Questionair questionair1=new Questionair();
        questionair1.setQuestion_id("2");
        questionair1.setQuestion("Which is Android latest released version");
        ArrayList<String> option1=new ArrayList<>();
        option1.add("Lollypop");
        option1.add("Orieo");
        option1.add("Nogat");
        option1.add("KitKat");
        questionair1.setOptions(option1);
        questionair1.setCorrectAnswer("Orieo");
        questionair1.setAnswerDescription("Android Latest Released version is Orieo");
        questionair1.setAnswer(1);
        ListQuestion.add(questionair1);

        Questionair questionair2=new Questionair();
        questionair2.setQuestion_id("3");
        questionair2.setQuestion("Does Android support keyboard");
        ArrayList<String> option2=new ArrayList<>();
        option2.add("Yes");
        option2.add("No");
        option2.add("No But we can use external keyboard");
        option2.add("None of these");
        questionair2.setOptions(option2);
        questionair2.setCorrectAnswer("No But we can use external keyboard");
        questionair2.setAnswerDescription("We can connect external keyboard using OTG cable");
        questionair2.setAnswer(2);
        ListQuestion.add(questionair2);

        Questionair questionair3=new Questionair();
        questionair3.setQuestion_id("4");
        questionair3.setQuestion("The first android phone was launched by");
        ArrayList<String> option3=new ArrayList<>();
        option3.add("Samsung");
        option3.add("Nokia");
        option3.add("Motorola");
        option3.add("HTC");
        questionair3.setOptions(option3);
        questionair3.setCorrectAnswer("HTC");
        questionair3.setAnswerDescription("The first android phone was launched by HTC Corporation on 22/10/2008 ");
        questionair3.setAnswer(3);
        ListQuestion.add(questionair3);

        Questionair questionair4=new Questionair();
        questionair4.setQuestion_id("5");
        questionair4.setQuestion("Who invented Android?");
        ArrayList<String> option4=new ArrayList<>();
        option4.add("Mark");
        option4.add("John");
        option4.add("Andy Rubin");
        option4.add("Salman");
        questionair4.setOptions(option4);
        questionair4.setCorrectAnswer("Andy Rubin");
        questionair4.setAnswerDescription("Android, Inc. was founded in Palo Alto, California in October 2003 by Andy Rubin (co-founder of Danger)");
        questionair4.setAnswer(2);
        ListQuestion.add(questionair4);

        Questionair questionair5=new Questionair();
        questionair5.setQuestion_id("6");
        questionair5.setQuestion("Who is owner of Android");
        ArrayList<String> option5=new ArrayList<>();
        option5.add("Google");
        option5.add("Samsung");
        option5.add("Microsoft");
        option5.add("Nolia");
        questionair5.setOptions(option5);
        questionair5.setCorrectAnswer("Google");
        questionair5.setAnswerDescription("Google is the owner of Android");
        questionair5.setAnswer(0);
        ListQuestion.add(questionair5);

        Questionair questionair6=new Questionair();
        questionair6.setQuestion_id("1");
        questionair6.setQuestion("What is Android");
        ArrayList<String> option6=new ArrayList<>();
        option6.add("Mobile Company");
        option6.add("Operating System");
        option6.add("Device");
        option6.add("Type of Computer");
        questionair6.setOptions(option6);
        questionair6.setCorrectAnswer("Operating System");
        questionair6.setAnswerDescription("Android is Open Source linux based operating system");
        questionair6.setAnswer(1);
        ListQuestion.add(questionair6);

    }
}
