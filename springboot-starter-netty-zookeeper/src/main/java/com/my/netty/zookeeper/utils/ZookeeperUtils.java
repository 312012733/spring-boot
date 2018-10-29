package com.my.netty.zookeeper.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;

public class ZookeeperUtils
{
    
    private static final String PARENT_PATH = "/netty";
    private static final String SEPARATOR = "/";
    private static final String COLON = ":";
    
    public static interface CallBack
    {
        void doBack(Map<String, List<ServerInfo>> allServerInfomap);
    }
    
    public static class ServerInfo
    {
        private String host;
        private Integer port;
        
        public ServerInfo(String host, String port)
        {
            this.host = host;
            this.port = Integer.parseInt(port);
        }
        
        public String getHost()
        {
            return host;
        }
        
        public void setHost(String host)
        {
            this.host = host;
        }
        
        public Integer getPort()
        {
            return port;
        }
        
        public void setPort(Integer port)
        {
            this.port = port;
        }
        
        @Override
        public String toString()
        {
            return "ServerInfo [host=" + host + ", port=" + port + "]";
        }
        
    }
    
    private static class MyCuratorWatcher implements CuratorWatcher
    {
        private CuratorFramework curator;
        private CallBack callBack;
        
        public MyCuratorWatcher(CuratorFramework curator, CallBack callBack)
        {
            this.curator = curator;
            this.callBack = callBack;
        }
        
        @Override
        public void process(WatchedEvent event) throws Exception
        {
            Map<String, List<ServerInfo>> allServerInfomap = discoverAllServerInfo(curator, callBack);
            callBack.doBack(allServerInfomap);
        }
    }
    
    public static void registServer(CuratorFramework curator, String applicationName, Integer port) throws Exception
    {
        String path = buildPath(applicationName, port);
        curator.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
    }
    
    public static Map<String, List<ServerInfo>> discoverAllServerInfo(CuratorFramework curator, CallBack callBack)
            throws Exception
    {
        String childPath = PARENT_PATH;
        
        CuratorWatcher watcher = new CuratorWatcher()
        {
            @Override
            public void process(WatchedEvent event) throws Exception
            {
            }
        };
        
        List<String> applicationNames = curator.getChildren().usingWatcher(watcher).forPath(childPath);
        
        Map<String, List<ServerInfo>> allServerInfomap = new HashMap<>();
        
        for (String applicationName : applicationNames)
        {
            List<ServerInfo> serverInfo = getServerInfo(curator, callBack, applicationName);
            allServerInfomap.put(applicationName, serverInfo);
        }
        
        return allServerInfomap;
    }
    
    private static List<ServerInfo> getServerInfo(CuratorFramework curator, CallBack callBack, String applicationName)
            throws Exception
    {
        String childPath = PARENT_PATH + SEPARATOR + applicationName;
        return getServerInfo(curator, new MyCuratorWatcher(curator, callBack), childPath);
    }
    
    private static List<ServerInfo> getServerInfo(CuratorFramework curator, CuratorWatcher watcher, String childPath)
            throws Exception
    {
        
        List<String> hostList = curator.getChildren().usingWatcher(watcher).forPath(childPath);
        
        List<ServerInfo> serverInfoList = new ArrayList<>();
        
        if (null == hostList)
        {
            return serverInfoList;
        }
        
        for (String info : hostList)
        {
            String[] infoAry = info.split(COLON);
            String host = infoAry[0];
            String port = infoAry[1];
            serverInfoList.add(new ServerInfo(host, port));
        }
        
        return serverInfoList;
    }
    
    private static String buildPath(String applicationName, Integer port) throws UnknownHostException
    {
        String ip = InetAddress.getLocalHost().getHostAddress();
        return buildPath(applicationName, ip, port);
    }
    
    private static String buildPath(String applicationName, String ip, Integer port)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(PARENT_PATH);
        sb.append(SEPARATOR);
        sb.append(applicationName);
        sb.append(SEPARATOR);
        sb.append(ip);
        sb.append(COLON);
        sb.append(port);
        
        return sb.toString();
    }
    
}
