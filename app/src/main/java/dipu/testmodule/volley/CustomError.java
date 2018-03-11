package dipu.testmodule.volley;

import com.android.volley.VolleyError;

/**
 * Created by Administrator on 10/18/2016.
 */

public class CustomError extends VolleyError {

    public  CustomError(String customError){
        super(customError);
    }
}

