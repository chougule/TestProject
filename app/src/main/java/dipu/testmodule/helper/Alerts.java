package dipu.testmodule.helper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.Spanned;


public class Alerts {


    public static AlertDialog internetConnectionErrorAlert(final Context context, DialogInterface.OnClickListener onClickTryAgainButton) {

        AlertDialog alertDialog = null;
        try {
            String message = "Unable to complete request.Check network and try again"/*Internet not available, Cross check your internet connectivity and try again*/;
            alertDialog= new AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setTitle(" Error")
                    .setMessage(message)
                    .setPositiveButton("Try Again", onClickTryAgainButton)
                    .setNegativeButton("Turn Internet On", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Settings.ACTION_SETTINGS);
                            ((Activity) context).startActivityForResult(i, 0);
                        }
                    })
                    .show();
        }catch (Exception e){
            e.printStackTrace();
        }
        return alertDialog;
    }

    public static AlertDialog timeoutErrorAlert(Context context, DialogInterface.OnClickListener onClickTryAgainButton) {

        AlertDialog alertDialog = null;
        try{
        String message = " Unable to complete request cause of connection time out.Check network and try again";

            alertDialog= new AlertDialog.Builder(context)
                .setCancelable(false)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(" Error")
                .setMessage(message)
                .setPositiveButton("Try Again", onClickTryAgainButton)
                .show();
        }catch (Exception e){
            e.printStackTrace();
        }
        return alertDialog;
    }

    public static void genericAlert(final Context context, String title, String msg,String btnText, DialogInterface.OnClickListener onClickTryAgainButton) {

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(btnText, onClickTryAgainButton)
                .show();
    }


    public static void displayActivityDetails(final Context context, Spanned title, Spanned msg, String btnText) {

        new AlertDialog.Builder(context)
                .setCancelable(false)
//                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

//    public static void conFirmSignOutUserAlert(final Context context, final CustomProgressDialog customProgressBar, final Response.Listener<LoginResponse> successLisener, final Response.ErrorListener errorListenertener) {
//        String message = "Confirm Sign out";
//       new AlertDialog.Builder(context)
//                .setCancelable(true)
//                .setIconAttribute(android.R.attr.alertDialogIcon)
//                .setTitle("Sign Out")
//                .setMessage(message)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        customProgressBar.show(null);
//                    //    SettingController.getInstance().signOutUser(successLisener, errorListenertener);
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                       dialog.dismiss();
//                    }
//                })
//                .show();
//    }

//    public static AlertDialog gotToSignInScreenForcfullyAlert(Context context, String title, DialogInterface.OnClickListener onClickTryAgainButton) {
//        String message = title;
//
//        return new AlertDialog.Builder(context)
//                .setCancelable(false)
//                .setIcon(ContextCompat.getDrawable(context,R.drawable.alert_success_icon))
//                .setTitle("Login Again")
//                .setMessage(message)
//                .setPositiveButton("Login", onClickTryAgainButton)
//                .show();
//    }
//


}
