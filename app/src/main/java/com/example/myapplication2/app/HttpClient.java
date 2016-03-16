package com.example.myapplication2.app;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class HttpClient
{
    public HttpClient()
    {
        if (CookieHandler.getDefault() == null)
        {
            CookieHandler.setDefault(new CookieManager());
        }
    }

    public String get(String requestUrl) throws IOException
    {
        HttpURLConnection connection = getConnection(requestUrl);
        String response = null;

        try
        {
            InputStream inputStream = connection.getInputStream();
            response = IOUtils.toString(inputStream);
            IOUtils.closeQuietly(inputStream);
        } finally
        {
            connection.disconnect();
        }

        return response;
    }

    public JSONObject getJSONObject(String url) throws Exception
    {
        return new JSONObject(get(url));
    }

    public JSONArray getJSONArray(String url) throws Exception
    {
        return new JSONArray(get(url));
    }

    private HttpURLConnection getConnection(String urlAddress) throws IOException
    {
        URL url = new URL(urlAddress);
        Proxy currentWifiProxy = ProxySelector.getDefault().select(URI.create("https://api.7pixel.it")).get(0);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(currentWifiProxy);

        connection.setConnectTimeout(10 * 1000);
        connection.setReadTimeout(10 * 1000);

        return connection;
    }
}