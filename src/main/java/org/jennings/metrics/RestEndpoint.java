/*
 * (C) Copyright 2017 David Jennings
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     David Jennings
 */
package org.jennings.metrics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.json.JSONObject;

/**
 * Specify a URL Send request(s) Get/Post that returns Response
 *
 * @author djennings
 */
public class RestEndpoint {

    String url;  // scheme://host:port

    public RestEndpoint(String url) {
        
        if (!url.startsWith("http")) {
            // Assuming http
            this.url = "http://" + url;
        } else {
            this.url = url;
        }
                
        
        
    }

    String get(String path, HashMap<String, String> params) {
        
        String respFromServer = null;
                
        
        try {
            String url2 = this.url + path;

            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
                    sslsf).build();

            HttpGet request = new HttpGet(url2);

            HttpResponse response = httpclient.execute(request);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            
            respFromServer = result.toString();
            

        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
            System.err.println(e.getMessage());
        }

        return respFromServer;
    }

    public static void main(String args[]) {
        String url = "m1:5050";
        RestEndpoint r = new RestEndpoint(url);
        
        JSONObject json = new JSONObject();
        String resp = r.get("/metrics/snapshot", null);
        System.out.println(resp);
        
        json = new JSONObject(resp.toString());
        
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            System.out.println(keys.next());
        }
        
    }
    
    
    
}
