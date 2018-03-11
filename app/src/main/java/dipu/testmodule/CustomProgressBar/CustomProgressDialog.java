package dipu.testmodule.CustomProgressBar;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import dipu.testmodule.R;

public class CustomProgressDialog extends Dialog {
    MaterialProgressBar progress1;

    Context mContext;
    TextView tvLoadingText;
    CustomProgressDialog dialog;
    public CustomProgressDialog(Context context) {
        super(context);
        this.mContext=context;
    }
    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomProgressDialog show(CharSequence message) {

        dialog = new CustomProgressDialog (mContext, R.style.ProgressDialog);
        dialog.setContentView(R.layout.view_material_progress);

        progress1 = (MaterialProgressBar) dialog.findViewById (R.id.progress1);
        tvLoadingText= (TextView)dialog. findViewById(R.id.tv_loading_Text);
        tvLoadingText.setText(message);

        progress1.setColorSchemeResources(R.color.amaze_green_dark, R.color.skyBlue, R.color.green, R.color.skyBlue);


        dialog.setCancelable(false);
        dialog.setTitle("Loading...");
        if(dialog!= null) {
            dialog.show ();
        }
        return dialog;
    }

    public CustomProgressDialog dismiss(CharSequence message) {
        if(dialog!=null) {
            dialog.dismiss();
        }

        return dialog;

    }


}
