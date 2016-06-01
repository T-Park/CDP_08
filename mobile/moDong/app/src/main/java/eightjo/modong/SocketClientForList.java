package eightjo.modong;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by apple on 16. 5. 30..
 */
public class SocketClientForList extends Thread {

    private static final int ERROR = -1;
    private static final int DATANUM = 0;
    //0번 : 횟수 1, 2, 3.. 데이터들..
    private static final String ERROR_KEY = "_error";
    private static final String DATA_KEY = "_data";

    boolean threadAlive;
    String ip;
    int port;
    Context context;
    String msg;
    String getString;
    Handler uiHandler;

    public SocketClientForList(Context context, String msg, Handler handler){
        threadAlive = true;
        this.ip = "20.20.3.188";
        this.port = 5555;
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
            String newMsg = new String(msg.getBytes("euc-kr"), "utf-8");
            out.println(newMsg);
            out.flush();

            if((len = bufStream.read((readBytes)))!= -1)
            {
                getString = new String(readBytes, 0, len);

                getString = getString.substring(0, getString.length()-2);//\n이 함께오는 현상!
                //Log.i("첫번째", nStr);
                int n = 0;
                try
                {
                    n = Integer.parseInt(getString.substring(1));
                }
                catch (Exception e)
                {

                }

                if(socket.isConnected())
                    if((len = bufStream.read((readBytes)))!= -1) {
                        String temp = new String(readBytes, 0, len);
                        getString = getString + temp;
                        Log.i("for : ", "받음" + " : " + getString + "\n");
                    }


/*
                Log.i("첫번째", n + "번 돌거라");
                for(int i=1; i<=n ;i++ )//서버가 보내준 횟수만큼 돌아서 여러번 받는다.
                {
                    if(socket.isConnected())
                    if((len = bufStream.read((readBytes)))!= -1) {
                        String temp = new String(readBytes, 0, len);
                        getString = getString + "%" + temp;
                        Log.i("for : ", i + " : " + getString + "\n");
                    }
                }
                */

                Message msg = Message.obtain();
                msg.what = DATANUM;
                Bundle bundle = new Bundle();
                bundle.putString(DATA_KEY, getString.substring(0, getString.length()-2));
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
