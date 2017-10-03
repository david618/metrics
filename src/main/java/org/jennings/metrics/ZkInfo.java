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

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.json.JSONObject;

/**
 *
 * @author djennings
 */
public class ZkInfo {
    
    private String connString;
    private ZooKeeper zk;
    private CountDownLatch connSignal = new CountDownLatch(0);
    
       

    public ZkInfo(String connString) {
        this.connString = connString;
        
        try {
            zk = new ZooKeeper(this.connString, 5000, new Watcher() {
                @Override
                public void process(WatchedEvent we) {
                    if (we.getState() == Event.KeeperState.SyncConnected) {
                        connSignal.countDown();
                    } 
                }
            });
            connSignal.await();
            
            
            
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    public void listNodes(String znode) {
        try {
            for (String c : zk.getChildren(znode, false)) {
                System.out.println(c);
            }
            
        } catch (KeeperException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void listHubs() {
        String znode = "/realtime-gis/hubs";
        try {
            for (String c : zk.getChildren(znode, false)) {
                String node = znode + "/" + c;
                
                String jsonString = new String(zk.getData(node, false, null));
                //System.out.println(jsonString);
                JSONObject json = new JSONObject(jsonString);
                System.out.println("name:" + json.getString("name"));
            }
            
        } catch (KeeperException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    
    }
    
    public void listSats() {
        String znode = "/realtime-gis/tasks/sat";
        try {
            for (String c : zk.getChildren(znode, false)) {
                String satnode = znode + "/" + c;
                
                String jsonString = new String(zk.getData(satnode, false, null));
                //System.out.println(jsonString);
                JSONObject json = new JSONObject(jsonString);
                System.out.println("name:" + json.getString("name"));
                System.out.println("nodeNameFQDN:" + json.getString("nodeNameFQDN"));
                System.out.println("httpPort:" + json.getInt("httpPort"));
            }
            
        } catch (KeeperException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    
    
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e ){
            System.err.println(e.getMessage());
        }
    }
    
    
    
    
    
    
    public static void main(String[] args) {
        ZkInfo t = new ZkInfo("localhost:2181");
        t.listNodes("/realtime-gis");
        System.out.println("***");
        t.listSats();
        t.listHubs();
        t.close();
        
        
        
        
    }
    
}
