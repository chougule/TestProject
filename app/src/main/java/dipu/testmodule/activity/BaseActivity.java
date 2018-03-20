package dipu.testmodule.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import dipu.testmodule.CustomProgressBar.CustomProgressDialog;
import dipu.testmodule.CustomProgressBar.MaterialProgressBar;
import dipu.testmodule.R;
import dipu.testmodule.helper.Toaster;

public class BaseActivity extends AppCompatActivity {

    private CustomProgressDialog mCustomProgressDialog;
    protected Toaster tost;
    protected FrameLayout mBaseFrameContainer;
    protected MaterialProgressBar progressBar;
    protected RelativeLayout no_networkRL;
    protected Button btn_no_network_retry;
    private Context mBaseContext;
    private AlertDialog mBaseAlertDialog;

    private CustomProgressDialog mCustomProgressDialog_1;
    Toolbar toolbar;
    int wallet_balance;

    boolean isLoogedIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        tost = new Toaster(this);
        mBaseFrameContainer = (FrameLayout) findViewById(R.id.base_frame_container);
        no_networkRL = (RelativeLayout) findViewById(R.id.frag_no_network_RL);
        btn_no_network_retry = (Button) findViewById(R.id.btn_retry);
        progressBar = findViewById(R.id.parent_progressbar);
        setUpToolBar();
    }


    protected void startLoading_1(String txt) {
        //Display your progressBar here
        if (mCustomProgressDialog_1 == null)
            mCustomProgressDialog_1 = /*CustomProgressDialog.getInstance(BaseActivity.this);*/ new CustomProgressDialog(BaseActivity.this);
        mCustomProgressDialog_1.show((txt == null) ? "Loading..." : txt);

    }


    protected void stopLoading_1() {
//
        if (mCustomProgressDialog_1 != null) {
            mCustomProgressDialog_1.dismiss("");
        }
        Handler mHand = new Handler();
        mHand.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                //Dismiss progressBar here
                if (mCustomProgressDialog_1 != null) {
                    mCustomProgressDialog_1.dismiss("");
                }
            }
        }, 6000);
    }


    protected void startLoading(String txt) {
        //Display your progressBar here
        if (mCustomProgressDialog == null)
            mCustomProgressDialog = /*CustomProgressDialog.getInstance(BaseActivity.this);*/ new CustomProgressDialog(BaseActivity.this);
        mCustomProgressDialog.show((txt == null) ? "Loading..." : txt);

//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
////        if (prev != null) {
////            ft.remove(prev);
////        }
//        ft.addToBackStack(null);


    }

    protected CustomProgressDialog getCustomProgressBar() {
        return (mCustomProgressDialog != null) ? mCustomProgressDialog : new CustomProgressDialog(BaseActivity.this);
    }

    protected void stopLoading() {
//        if (newFragment != null) {
//           // mCustomProgressDialog.dismiss("");
//            newFragment.dismiss();
//            // / mCustomProgressDialog.dismiss();
//            Log.i("HMT", "mCustomProgressDialog");
//            // mCustomProgressDialog.cancel();
//        }
        if (mCustomProgressDialog != null) {
            mCustomProgressDialog.dismiss("");
        }
        Handler mHand = new Handler();
        mHand.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                //Dismiss progressBar here
                if (mCustomProgressDialog != null) {
                    mCustomProgressDialog.dismiss("");
                }
            }
        }, 6000);
    }

    protected void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    protected void setRetryButtonClicklistener(View.OnClickListener listener) {
        btn_no_network_retry.setOnClickListener(listener);
    }

    protected void visible_No_Network_Layout() {
        no_networkRL.setVisibility(View.VISIBLE);
    }

    protected void in_Visible_No_Network_Layout() {

        no_networkRL.setVisibility(View.GONE);
    }

    protected void dummyProgress(String pregressText, final String disReturnText) {

        //Display your progressBar here
        mCustomProgressDialog = /*CustomProgressDialog.getInstance(BaseActivity.this);*/new CustomProgressDialog(this);
        mCustomProgressDialog.show(pregressText);
        Handler mHand = new Handler();
        mHand.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                //Dismiss progressBar here
                mCustomProgressDialog.dismiss("");
                tost.displayToastLONG(disReturnText);
            }
        }, 2000);
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (mBaseAlertDialog != null) {
            mBaseAlertDialog.dismiss();
        }
        if (mCustomProgressDialog != null) {
            mCustomProgressDialog.dismiss("");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBaseAlertDialog != null) {
            mBaseAlertDialog.dismiss();
        }
        if (mCustomProgressDialog != null) {
            mCustomProgressDialog.dismiss("");
        }
    }

    protected void lockScreenOrientation() {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    protected void unlockScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

public boolean getNetworkState() {
    ConnectivityManager cm =
            (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null &&
            activeNetwork.isConnectedOrConnecting();
     return isConnected;
}

    private void setUpToolBar() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.wallet_menu,menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.wallet_balance);
        LinearLayout wallet_bal = (LinearLayout) menuItem.getActionView();
        final ImageView show_popup = wallet_bal.findViewById(R.id.iv_show_popup);
        final TextView tv_balance = wallet_bal.findViewById(R.id.tv_wallet_balance);
        show_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateWalletBalance.addMoney(BaseActivity.this);
            }
        });

        //   show_popup.setTag("down");
        if (isLoogedIn)
        tv_balance.setText(getResources().getString(R.string.Rs)+  ControllerManager.getInstance().getSpManager().getWalletbalance());
        else
            tv_balance.setVisibility(View.GONE);
//
        return true;
    }*/



}
