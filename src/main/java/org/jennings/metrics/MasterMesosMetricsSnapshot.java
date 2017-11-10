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

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

/**
 *
 * @author david
 */
public class MasterMesosMetricsSnapshot {
            
    
    // Guages
    static Double system_load_1min;
    static Double system_load_5min;
    static Double system_load_15min;   
    static Integer master_slaves_connected;    

    static Double master_cpus_used;
    static Double master_cpus_total;
    static Double master_cpus_percent;
    
    static Double master_mem_total; // master/mem_total
    static Double master_mem_used;  // master/mem_used
    static Double master_mem_percent; // master/mem_percent
    
    static Double master_disk_used;
    static Double master_disk_total;
    static Double master_disk_percent;

    RestEndpoint masterEndpoint;
              
    public void update() {
        String resp = masterEndpoint.get("/metrics/snapshot", null);     
        System.out.println(resp);

        //Object document = Configuration.defaultConfiguration().jsonProvider().parse(resp);
        ReadContext ctx = JsonPath.parse(resp);
        

//        system_load_1min = JsonPath.read(document, "system/load_1min");
//        system_load_5min = JsonPath.read(document, "system/load_5min");
//        system_load_15min = JsonPath.read(document, "system/load_15min");
//        
          master_slaves_connected = ctx.read("master/slaves_connected", Integer.class);
//        master_slaves_connected = JsonPath.read(document, "master/slaves_connected");
//        
//        //master_cpus_total = Math.round((double) JsonPath.read(document, "master/cpus_total"));
//        master_cpus_total = JsonPath.read(document, "master/cpus_total");
//        master_cpus_used = JsonPath.read(document, "master/cpus_used");
//        master_cpus_percent = JsonPath.read(document, "master/cpus_percent");
//        
//        
//        master_mem_total = JsonPath.read(document, "master/mem_total");
//        master_mem_used = JsonPath.read(document, "master/mem_used");
//        master_mem_percent = JsonPath.read(document, "master/mem_percent");
//        
//        master_disk_total = JsonPath.read(document, "master/disk_total");
//        master_disk_used = JsonPath.read(document, "master/disk_used");
//        master_disk_percent = JsonPath.read(document, "master/disk_percent");  

        System.out.println(master_slaves_connected);
        
    }

    

    public MasterMesosMetricsSnapshot(RestEndpoint masterEndpoint) {
        this.masterEndpoint = masterEndpoint;
    }
        
    
    public static void main(String args[]) {
        
        String url = "m1:5050";
        RestEndpoint re = new RestEndpoint(url);
        
        MasterMesosMetricsSnapshot t = new MasterMesosMetricsSnapshot(re);
       
        t.update();
        
    }

    
}
