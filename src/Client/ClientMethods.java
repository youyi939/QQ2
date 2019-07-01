package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMethods {
    public Socket clientSocket;

    public void inputClientName(Scanner scan)throws Exception{
        String clientName = null;
        PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8"),true
        );
        BufferedReader br = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream(),"UTF-8")
        );
        while (true){
            System.out.println("请创建您的昵称: ");
            clientName = scan.nextLine();
            if (clientName.trim().equals("")){
                System.out.println("昵称不得为空!");
            }else {
                pw.println(clientName);
                String pass = br.readLine();
                if (pass != null && !pass.equals("OK")){
                    System.out.println("该昵称已被占用，请重新输入！");
                }else {
                    System.out.println("你好！"+clientName+" 可以开始聊天啦");
                    break;
                }
            }
        }
    }
}