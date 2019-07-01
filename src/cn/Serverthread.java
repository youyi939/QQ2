package cn;
import java.io.*;
import java.net.Socket;

public class Serverthread extends ServerMethods {
    class GetClientMsgHandler implements Runnable{
        private Socket socket;
        private String clientName;                                      //存储client的昵称
        public GetClientMsgHandler(Socket socket){
            this.socket = socket;
        }
        private String getClientName()throws Exception{
            try {
                InputStream in = socket.getInputStream();                       //服务端的输入流读取客户端发送来的昵称输出流
                InputStreamReader isr = new InputStreamReader(in,"UTF-8"); //把字节流读取的字节进行缓冲而后在通过字符集解码成字符返回
                BufferedReader bfd = new BufferedReader(isr);
                OutputStream out = socket.getOutputStream();

                OutputStreamWriter osw = new OutputStreamWriter(out,"UTF-8");
                PrintWriter ipw = new PrintWriter(osw,true);
                String nameStrig = bfd.readLine();
                while (true){
                    if (nameStrig.trim().length() == 0){                    //trim的用法是去掉两端的空格
                        ipw.println("FAIL");
                    }
                    if (allClient.containsKey(nameStrig)){              //containskey(),用来判断是否包含指定的键名
                        ipw.println("FAIL");
                    }else {
                        ipw.println("OK");
                        return nameStrig;
                    }
                    nameStrig = bfd.readLine();
                }
            }catch (Exception e){
                throw e;
            }
        }

        @Override
        public void run() {
            PrintWriter pw = null;
            try {
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
                pw = new PrintWriter(osw,true);
                clientName = getClientName();                                   //获取用户昵称
                addClient(clientName,pw);
                sendMsgToAll("[系统通知]:欢迎~ ~ ~ " + clientName + " ~ ~ ~ 登陆平头哥!");
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String msgString = null;
                while ((msgString = br.readLine()) != null){
                    if (msgString.equals("拜拜")){
                        removeClient(clientName);
                        sendMsgToAll("[系统通知]: "+clientName+" 已经下线了");
                    }
                    if (msgString.startsWith("@")){
                        int index = msgString.indexOf(":");
                        if (index >= 0){
                            String name = msgString.substring(1,index);
                            String info = msgString.substring(index+1,msgString.length());
                            info = clientName+"对你说："+info;
                            sendMsgToPrivate(info,name);
                            continue;
                        }
                    }
                    System.out.println(clientName+"说："+msgString);
                    sendMsgToAll(clientName+"说："+msgString);
                }
            }catch (Exception e){
                e.printStackTrace();
            } finally {
                removeClient(clientName);
                sendMsgToAll("[系统通知]: "+clientName+" 异常掉线");                       //删除一个client之后通知所有client某某下线了
                if (socket != null){
                    try {
                        socket.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}