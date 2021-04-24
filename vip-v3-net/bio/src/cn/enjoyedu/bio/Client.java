package cn.enjoyedu.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com
 *
 *类说明：Bio通信的客户端
 */
public class Client {

    public static void main(String[] args) throws IOException {
        //客户端必备
        Socket socket = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        InetSocketAddress serverAddr = new InetSocketAddress("127.0.0.1",10001);

        try {
            socket = new Socket();
            socket.connect(serverAddr);

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.writeUTF("Lance");
            outputStream.flush();

            System.out.println(inputStream.readUTF());
        } finally {
            if(socket!= null) socket.close();
            if(outputStream!=null ) outputStream.close();
            if(inputStream!=null ) inputStream.close();
        }


    }

}
