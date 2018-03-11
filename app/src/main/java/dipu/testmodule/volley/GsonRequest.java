/**
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dipu.testmodule.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Listener<T> listener;

    private HashMap<String, String> parameter = null;
    private JSONObject parameters;

 //   private JSONObject parameters = null;
    private  Map<String, String> hasmap;



    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
                       Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
        setRetryPolicy(new DefaultRetryPolicy(30 * 1000,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
                       Listener<T> listener, ErrorListener errorListener, JSONObject parameters) {
        this(method, url, clazz, headers, listener, errorListener);
        this.parameters = parameters;
        setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
                       Listener<T> listener, ErrorListener errorListener, HashMap<String,String > parameters) {
        this(method, url, clazz, headers, listener, errorListener);
        this.parameter = parameters;
        setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
                       Listener<T> listener, ErrorListener errorListener, Map<String,String> parameters) {
        this(method, url, clazz, headers, listener, errorListener);
        hasmap = parameters;
        setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
     return headers != null ? headers : super.getHeaders();

    }

    @Override
    public String getBodyContentType() {
      //  return "application/json";
        return "application/x-www-form-urlencoded; charset=UTF-8";
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        return hasmap;
    }

    /*@Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return parameter.toString().getBytes(getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
            Log.d("GET_BODY_ERROR",e.getLocalizedMessage());
        }
        return null;
    }*/

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));Log.i("RESPONSE", json);
            return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError){

        if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
            volleyError = error;
        }

        return volleyError;
    }


}