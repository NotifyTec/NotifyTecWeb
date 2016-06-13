package br.com.notifytec.services;

import br.com.notifytec.models.Resultado;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import sun.net.www.http.HttpClient;

public class RestService<T> {

    private String _DOMINIO_ = "https://fcm.googleapis.com/fcm/send";
    private Class<T> type;

    public RestService(Class<T> type) {
        this.type = type;
    }

    private String getAuthorizationToken() {
        return "key=AIzaSyDypViRHuCZkAPUaPRaPVKj-YHLbvAaUXY";
        //return "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbiI6ImFuZHJvaWQiLCJlbWFpbCI6InRlc3RlQGRvbWFpbi5jb20iLCJhbHRlcm91U2VuaGEiOnRydWUsInBvZGVFbnZpYXIiOnRydWUsImp0aSI6ImMyYmIxYmEyLWYyYjMtNGM2My05ODIwLTFjNmI3NTJiOGVkYyJ9.H8Ze9GRvwp7KO9m4ZL_oIj3HjtjRw1n23bgfAqFuATpBWx336T5iNtAbcLNf4xQzSMQmsiPwmdxuGn5lUPWhRA";
    }

    public T execute(Object obj) {

        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(_DOMINIO_);

            httppost.addHeader("Authorization", getAuthorizationToken());
            httppost.addHeader("Content-Type", "application/json");

            String json = new Gson().toJson(obj);
            StringEntity stringEntity;
            stringEntity = new StringEntity(json, ContentType.create("application/json", Charset.forName("UTF-8")));
            httppost.setEntity(stringEntity);

            //Execute and get the response.
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    // do something useful
                    Reader reader = new InputStreamReader(entity.getContent());
                    int intValueOfChar;
                    String targetString = "";
                    while ((intValueOfChar = reader.read()) != -1) {
                        targetString += (char) intValueOfChar;
                    }
                    reader.close();

                    return new Gson().fromJson(targetString, type);
                } finally {
                    instream.close();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
