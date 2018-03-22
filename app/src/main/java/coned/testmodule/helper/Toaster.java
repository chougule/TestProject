package coned.testmodule.helper;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import coned.testmodule.R;

public class Toaster {

    Context context;
    LayoutInflater inflate;
    View toastRoot;
    private static final int  VERY_SHORT_TIME= 400;

    public Toaster(Context context)
    {

        this.context = context;
        inflate=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toastRoot = inflate.inflate(R.layout.custom_toast_layout,null);
    }

    public void displayToastSHORT(String toastMessage)
    {

        TextView text = (TextView)toastRoot.findViewById(R.id.Toast_Text);

        text.setText(toastMessage);

        final Toast toast = new Toast(context);

        // Set layout to toast

        toast.setView(toastRoot);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                toast.cancel();
            }
        }, 1000);
    }
    public void displayToastVERY_SHORT(String toastMessage)
    {

        TextView text = (TextView)toastRoot.findViewById(R.id.Toast_Text);

        text.setText(toastMessage);

        final Toast toast = new Toast(context);

        // Set layout to toast
        toast.setView(toastRoot);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                toast.cancel();
            }
        }, VERY_SHORT_TIME);
    }

    public void displayToastLONG(String toastMessage)
    {

        TextView text = (TextView)toastRoot.findViewById(R.id.Toast_Text);

        text.setText(toastMessage);

        final Toast toast = new Toast(context);

        // Set layout to toast

        toast.setView(toastRoot);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                toast.cancel();
            }
        }, 2000);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
