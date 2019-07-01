package Client;
import java.io.*;

public class ClientThread extends ClientMethods{
    class GetServerMsgHandler implements Runnable{
        @Override
        public void run() {
            try {
                InputStream is = clientSocket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String msgString = null;
                while ((msgString = br.readLine()) != null){
                    System.out.println("服务器端消息：" + msgString);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}