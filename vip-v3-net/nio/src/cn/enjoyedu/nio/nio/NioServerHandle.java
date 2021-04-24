package cn.enjoyedu.nio.nio;

import cn.enjoyedu.nio.Const;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static cn.enjoyedu.nio.Const.response;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：nio通信服务端处理器
 */
public class NioServerHandle implements Runnable{

    private volatile boolean started;
    private ServerSocketChannel serverChannel;
    private Selector selector;



    /**
     * 构造方法
     * @param port 指定要监听的端口号
     */
    public NioServerHandle(int port) {

        try {
            //创建一个Selector的实例
            selector = Selector.open();
            //创建一个ServerSocketChannel的实例
            serverChannel = ServerSocketChannel.open();
            //设置为false，表示将当前的通道设置为非阻塞模式
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port));
            //注册OP_ACCEPT事件，表明serverChannel对客户端连接事件感兴趣
            serverChannel.register(selector,SelectionKey.OP_ACCEPT);
            started = true;
            System.out.println("服务器已启动，端口号："+port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(started){
            try {
                //等待1S被唤醒一次，如果有事件产生，也会被唤醒
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key  = null;
                while(it.hasNext()){
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        if(key!=null){
                            key.cancel();
                            if(key.channel()!=null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //退出循环的时候，selector也要关闭
        if(selector!=null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            //当前有连接进来了，我需要处理
            if(key.isAcceptable()){
                ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                //和客户端通信的socket
                SocketChannel sc =  ssc.accept();
                System.out.println("有客户端连接");
                sc.configureBlocking(false);
                sc.register(selector,SelectionKey.OP_READ);
            }
            //读数据
            if(key.isReadable()){
                SocketChannel sc = (SocketChannel)key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                //返回值表示我们从Channel读取到了多少数据
                int readBytes =  sc.read(buffer);
                if(readBytes>0){
                    //因为channel写入了buffer，所以我们读的时候，要进行模式的切换
                    buffer.flip();
                    //读取数据做业务处理
                    byte[] bytes = new byte[readBytes];
                    buffer.get(bytes);
                    String message = new String(bytes,"UTF-8");
                    System.out.println("服务器收到消息："+message);
                    String result = response(message);
                    //发送应答消息
                    doWrite(sc,result);
                }
                //链路已经关闭，释放资源
                else if(readBytes<0){
                    key.cancel();
                    sc.close();
                }
            }

        }

    }

    private void doWrite(SocketChannel sc, String result) throws IOException {
        byte[] bytes = result.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        //将字节数组复制到writeBuffer
        writeBuffer.put(bytes);
        writeBuffer.flip();
        sc.write(writeBuffer);

    }

    public void stop(){
        started = false;
    }

}
