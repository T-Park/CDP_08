package eightjo.modong;

import android.app.Activity;
//<<<<<<< Updated upstream
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//=//======
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
//>>>>>>> Stashed changes
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//<<<<<<< Updated upstream
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class tab_cardViewer extends Activity {

    SQLiteDatabase db;
    String user_name;
    int group_flag, point;
    String user_id;
    String bacode;
    String gBacode;
    TextView textView_view_name, textView_view_group, textView_view_point;

    Switch switch_bacode;
    ImageView imageView_bacode;
//>>>>>>> Stashed changes

    Context context;
    String getString;
    private static final int ERROR = -1;
    private static final int DATA = 2;
    private static final String ERROR_KEY = "_error";
    private static final String DATA_KEY = "_data";
    private static int port = 5555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_card_viewer);
        //loadLatestDB();

        textView_view_name = (TextView)findViewById(R.id.textView_view_name);
        textView_view_group = (TextView)findViewById(R.id.textView_view_group);
        textView_view_point = (TextView)findViewById(R.id.textView_view_point);

        textView_view_name.setText(user_name);
        if(group_flag == 0)
            textView_view_group.setText("그룹이 없어요.");
        else
            textView_view_group.setText("그룹 안에 있어요");
        textView_view_point.setText(point + "P");

        imageView_bacode = (ImageView) findViewById(R.id.imageView_bacode);
        String bacode = "12345678910";
        setBacode(bacode);

        switch_bacode = (Switch)findViewById(R.id.switch_bacode);
        switch_bacode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    switch_bacode.setText("그룹 바코드");
                    setBacode("123123");
                }
                else
                {
                    switch_bacode.setText("나의 바코드");
                    setBacode("12345678910");
                }
            }
        });

        context = this;
        try
        {
            SocketClient client = new SocketClient("20.20.3.188", 5555, this, "#ModongSyn%" + "asd", uiHandler);
            client.start();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "서버 연결 실패" , Toast.LENGTH_LONG);
        }

    }

    public void setBacode(String bacode)
    {
        Bitmap bacode2 = createBarcode(bacode);

        imageView_bacode.setImageBitmap(bacode2);
        imageView_bacode.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_card_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public  void onResume()
    {
        super.onResume();
        //loadLatestDB();
    }

    public void loadDB()
    {
        if(db != null)
        {
            db.close();
        }

        db= openOrCreateDatabase(
                "forAppInfo.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS appInfo " +
                "(" +
                " user_id TEXT," +
                " user_pw TEXT," +
                " name TEXT," +
                " job TEXT," +
                " age INTEGER," +
                " phone TEXT," +
                " lock_flag INTEGER," +
                " lock_pw TEXT," +
                " point INTEGER," +
                " type TEXT," +
                " login_flag INTEGER," +
                " group_flag INTEGER," +
                " bacode TEXT" +
                ");");
    }

    public void loadLatestDB()
    {
        loadDB();
        Cursor c = db.rawQuery("SELECT * FROM appInfo where login_flag =1;", null);
        c.moveToPosition(c.getCount() - 1);
        user_name =  c.getString(c.getColumnIndex("name"));
        group_flag =  c.getInt(c.getColumnIndex("group_flag"));
        point = c.getInt(c.getColumnIndex("point"));
        user_id = c.getString(c.getColumnIndex("user_id"));//
    }

    public void onClick_goToDonation(View v)
    {
        Intent intent = new Intent(this, activity_donate.class);
        startActivity(intent);
    }

    public void nClick_goToGivePointo(View v) {
        Intent intent = new Intent(this, activity_givePoint.class);
        startActivity(intent);
    }

    public Bitmap createBarcode(String code){


        Bitmap bitmap =null;
        MultiFormatWriter gen = new MultiFormatWriter();
        try {
            final int WIDTH = 840;
            final int HEIGHT = 500;
            BitMatrix bytemap = gen.encode(code, BarcodeFormat.CODE_128, WIDTH, HEIGHT);
            bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
            for (int i = 0 ; i < WIDTH ; ++i)
                for (int j = 0 ; j < HEIGHT ; ++j) {
                    bitmap.setPixel(i, j, bytemap.get(i,j) ? Color.BLACK : Color.WHITE);
                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public void onClick_syn(View v)
    {

        //서버에게 userid를 보내어 동기화 신청
        //값을 받은 후 포인트, 그룹 생성

        try
        {
            SocketClient client = new SocketClient("20.20.3.188", 5555, this, "#ModongSyn%" + "asd", uiHandler);
            client.start();//
        }
        catch (Exception e)
        {
            Toast.makeText(this, "서버 연결 실패" , Toast.LENGTH_LONG);
        }
    }



    Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == ERROR){
                Toast.makeText(context, "Error : \n"+msg.getData().getString(ERROR_KEY), Toast.LENGTH_SHORT).show();
            }else if(msg.what == DATA){
                textView_view_name.setText( msg.getData().getString(DATA_KEY));
            }
        }
    };


}
