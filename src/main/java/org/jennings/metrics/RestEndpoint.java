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

import java.util.HashMap;
import org.json.JSONObject;

/**
 * Specify a URL 
 * Send request(s) Get/Post that returns Response
 * 
 * @author djennings
 */
public class RestEndpoint {
    
    String url;  // scheme://host:port

    public RestEndpoint(String url) {
        this.url = url;
    }
    
    
    JSONObject get(String path, HashMap<String,String> params) {
        JSONObject json = new JSONObject();
        
        
        
        return json;
    }
    
    
    
    
}
