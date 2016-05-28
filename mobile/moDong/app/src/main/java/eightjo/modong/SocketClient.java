package eightjo.modong;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by apple on 16. 5. 28..
 */
public class SocketClient extends Thread {

    private static final int ERROR = -1;
    private static final int DATA = 2;
    private static final String ERROR_KEY = "_error";
    private static final String DATA_KEY = "_data";

    boolean threadAlive;
    String ip;
    int port;
    Context context;
    String msg;
    String getString;
    Handler uiHandler;


    public SocketClient(String ip, int port, Context context, String msg, Handler handler){
        threadAlive = true;
        this.ip = ip;
        this.port = port;
        this.context = context;
        this.msg = msg;
        uiHandler = handler;
    }

    @Override
    public void run() {
        Socket socket = null;
        InputStream inputStream = null;
        BufferedInputStream bufStream = null;
        OutputStream outputStream = null;
        BufferedOutputStream bufferedOutputStream = null;


        try {
            socket = new Socket(ip,port);
            inputStream = socket.getInputStream();
            bufStream = new BufferedInputStream(inputStream);
            outputStream = socket.getOutputStream();
            bufferedOutputStream = new BufferedOutputStream(outputStream);

            int len = 0;
            byte[] readBytes = new byte[4096];

            PrintWriter out = new PrintWriter(bufferedOutputStream, true);
            out.println(msg);
            out.flush();

            if((len = bufStream.read((readBytes)))!= -1)
            {
                getString = new String(readBytes, 0, len);
                Message msg = Message.obtain();
                msg.what = DATA;
                Bundle bundle = new Bundle();
                bundle.putString(DATA_KEY, getString);
                msg.setData(bundle);
                uiHandler.sendMessage(msg);

            }

        } catch (Exception e) {
            Message msg = Message.obtain();
            msg.what = ERROR;
            Bundle bundle = new Bundle();
            bundle.putString(ERROR_KEY, e.toString());
            msg.setData(bundle);
            uiHandler.sendMessage(msg);
        } finally{
            try {
                if(bufStream != null)
                    bufStream.close();
                if(inputStream != null)
                    inputStream.close();
                if(socket != null)
                    socket.close();
            } catch (IOException e) {
                Message msg = Message.obtain();
                msg.what = ERROR;
                Bundle bundle = new Bundle();
                bundle.putString(ERROR_KEY, e.toString());
                msg.setData(bundle);
                uiHandler.sendMessage(msg);
            }
        }
    }

}
