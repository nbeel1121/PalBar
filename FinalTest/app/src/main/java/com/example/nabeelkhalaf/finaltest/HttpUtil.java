package com.example.nabeelkhalaf.finaltest;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class HttpUtil {
    private static final int DEFAULT_TIMEOUT = 30000;
    private static DefaultHttpClient _httpClient = null;

    public static void getRequest(final String url, JSONObject jsonObject, final HttpRequestCallBack callBack) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpGet httpGet = new HttpGet(url);
                    HttpResult result = executeHttpRequest(httpGet);

                    if (result != null) {
                        if (result.getStatusCode() != HttpStatus.SC_OK) {
                            callBack.onError("Failed Request with status code " + result.getStatusCode());
                        }
                        else {
                            callBack.onSuccess(result.getResponseBody());
                        }
                    }
                }
                catch (Exception e) {
                    callBack.onError(e.getMessage());
                }

            }
        });
    }

    public static void postRequest(final String url, final JSONObject json, final HttpRequestCallBack callBack) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpPost request = new HttpPost(url);

                    if (json != null) {
                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

                        if (json.names() != null) {
                            for (int i = 0; i < json.names().length(); i++) {
                                String key = json.names().getString(i);
                                Object valObj = json.get(key);
                                String val;
                                if (!(valObj instanceof String)) {
                                    val = String.valueOf(valObj);
                                }
                                else {
                                    val = (String) valObj;
                                }

                                val = String.format(Locale.US, "%s", val);
                                nameValuePairs.add(new BasicNameValuePair(key, val));
                            }
                        }

                        request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                    }
                    else {
                        request.setEntity(new StringEntity("", "UTF-8"));
                    }

                    HttpResult result = executeHttpRequest(request);

                    if (result != null) {
                        if (result.getStatusCode() != HttpStatus.SC_OK) {
                            callBack.onError("Failed Request with status code " + result.getStatusCode());
                        }
                        else {
                            callBack.onSuccess(result.getResponseBody());
                        }
                    }
                }
                catch (Exception e) {
                    callBack.onError(e.getMessage());
                }
            }
        });
    }

    private static HttpResult executeHttpRequest(HttpRequestBase requestBase) throws Exception {
        if (requestBase == null) {
            throw new Exception("Request is null");
        }
        HttpEntity entity = null;
        try {
            HttpResponse httpResponse = getHttpClient().execute(requestBase);
            entity = httpResponse.getEntity();
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return new HttpResult(statusCode, null);
            }

            String output = EntityUtils.toString(entity);
            return new HttpResult(statusCode, output);
        }
        finally {
            consumeHttpEntitiy(entity);
        }
    }

    private static void consumeHttpEntitiy(HttpEntity entity) {
        if (entity != null) {
            try {
                entity.consumeContent();
            }
            catch (Exception e) {

            }
        }
    }

    private static synchronized DefaultHttpClient getHttpClient() {
        if (_httpClient != null) {
            return _httpClient;
        }
        HttpParams prm = new BasicHttpParams();
        int timeOut = DEFAULT_TIMEOUT;
        HttpConnectionParams.setConnectionTimeout(prm, timeOut);
        HttpConnectionParams.setSoTimeout(prm, timeOut);

        ConnManagerParams.setMaxTotalConnections(prm, 6);
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager manager = new ThreadSafeClientConnManager(prm, registry);
        _httpClient = new DefaultHttpClient(manager, prm);

        return _httpClient;
    }

    public interface HttpRequestCallBack {
        void onSuccess(String response);
        void onError(String error);
    }
}
