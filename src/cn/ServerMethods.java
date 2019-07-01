package cn;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Map;

public class ServerMethods {
    public ServerSocket serverSocket;
    public Map <String,PrintWriter> allClient;

    public void addClient(String key, PrintWriter value){
        synchronized (this){
            allClient.put(key,value);
        }
    }                   //用来存储Client
    public synchronized void removeClient(String key){
        allClient.remove(key);
        System.out.println("当前在线人数为: " + allClient.size());
    }                      //删除客户端并且刷新显示在线人数
    public synchronized void sendMsgToAll(String message){
        for(PrintWriter out : allClient.values()){
            out.println(message);
            System.out.println("当前在线人数为：" + allClient.size());
        }
    }                    //发送信息给所有客户端
    public synchronized void sendMsgToPrivate(String message,String clientName){
        PrintWriter pw = allClient.get(clientName);                 //发送给指定的客户端
        if (pw != null){
            pw.println(message);
            System.out.println("当前在线人数为: " + allClient.size());
        }
    }     //发送信息给指定客户端进行私聊
}