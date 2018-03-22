package coned.testmodule.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;

import coned.testmodule.R;

/**
 * Created by Deepak on 20-Mar-18.
 */

public class QuestionairNewActivity extends BaseActivity {

    CardView cardView_queno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_questionair_second,mBaseFrameContainer);

        initView();
    }

    private void initView() {

        //cardView_queno=findViewById(R.id.card_queno);

    }
}
