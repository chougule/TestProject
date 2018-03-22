package coned.testmodule.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.Map;

/**
 * Created by yocto on 7/2/18.
 */

public class CustomJsonArrayRequest extends JsonArrayRequest {
    Map<String, String> parameters;
    Map<String, String> headers;

    @Override
        public Map<String, String> getHeaders() throws AuthFailureError {

        return headers != null ? headers : super.getHeaders();

    }

        @Override
        public String getBodyContentType() {
            return "application/json";
        }


    public CustomJsonArrayRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);


    }
    public CustomJsonArrayRequest(String url, Map<String, String> headers, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        this.headers = headers;
        setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


}
