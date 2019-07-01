package cn;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server extends Serverthread{
    public Server(){
        try {
            serverSocket = new ServerSocket(30001);
            allClient = new HashMap<String, PrintWriter>();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void start(){
        try {
            while (true){
                System.out.println("等待客户端连接.....");
                Socket socket = serverSocket.accept();                  //连接客户端
                System.out.println("客户端连接成功...");
                Runnable run = new Serverthread.GetClientMsgHandler(socket);
                new Thread(run).start();                               //启动一个线程
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}