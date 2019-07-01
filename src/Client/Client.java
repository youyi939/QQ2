package Client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends ClientThread{                                       //客户端的初始化

    public Client(){
        try {
            clientSocket = new Socket("192.168.3.106",30001);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void start(){
        try {
            Scanner scanner = new Scanner(System.in);
            inputClientName(scanner);
            Runnable run = new ClientThread.GetServerMsgHandler();
            Thread t = new Thread(run);
            t.start();
            OutputStream os = clientSocket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
            PrintWriter pw = new PrintWriter(osw,true);
            while (true){
                pw.println(scanner.nextLine());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (clientSocket != null){
                try {
                    clientSocket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}