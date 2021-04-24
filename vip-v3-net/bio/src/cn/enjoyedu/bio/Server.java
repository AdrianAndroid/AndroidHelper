package cn.enjoyedu.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com
 *
 *类说明：Bio通信的服务端
 */
public class Server {

    public static void main(String[] args) throws IOException {
        //服务端启动必备
        ServerSocket serverSocket = new ServerSocket();
        //表示我们服务器在哪个端口上监听
        serverSocket.bind(new InetSocketAddress(10001));
        System.out.println("Start server.......");
        try {

            while(true){
                new Thread(new ServerTask(serverSocket.accept())).start();
            }
        } finally {
            serverSocket.close();
        }
    }

    private static class ServerTask implements Runnable{
        private Socket socket = null;

        public ServerTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try(ObjectInputStream inputStream =
                        new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputStream =
                        new ObjectOutputStream(socket.getOutputStream()))
            {
                /*接受客户端的输出，也就是服务器的输入*/
                String userName = inputStream.readUTF();
                System.out.println("Accetp client message:"+userName);

                //处理各种实际的业务

                /*服务器的输入的输出，也就是客户端的输入*/
                outputStream.writeUTF("Hello,"+userName);
                outputStream.flush();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
