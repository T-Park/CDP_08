package eightjo.modong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginPage extends Activity {

    SQLiteDatabase db;
    EditText editText_id;
    EditText editText_pw;

    Context context;
    private static final int ERROR = -1;
    private static final int DATA = 2;
    private static final String ERROR_KEY = "_error";
    private static final String DATA_KEY = "_data";
    private static int port = 5555;
    String getString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        editText_id = (EditText)findViewById(R.id.editText_login_id);
        editText_pw = (EditText)findViewById(R.id.editText_login_pw);

        //임시!
        loadDB();

        context = this;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
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

    public void goToSignIn(View v)
    {
        Intent intent = new Intent(this, activity_sign_in.class);
        startActivity(intent);
    }

    public void goToService_(View v)
    {

        Cursor c = db.rawQuery("SELECT * FROM appInfo where login_flag =1;", null);

        startManagingCursor(c);
        if(c.getCount() > 0)
        {
            Intent intent = new Intent(this, tab_main.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "가입해주세요 ", Toast.LENGTH_SHORT).show();
        }

    }

    //forAppInfo : appInfo : 1. user_id 2. user_pw 3. name 4. job 5. age 6. phone 7. lock_flag 8. lock_pw 9. point 10. type 11. login_flag 12. group_flag 13. bacode
    public void loadDB()
    {
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

    public void goToService(View v)
    {
        String id = editText_id.getText().toString();
        String pw = editText_pw.getText().toString();


        SocketClient client = new SocketClient("192.168.56.1", 5555, this, "#ModongLogin%" + id + "%" + pw, uiHandler);
        client.start();
    }



    Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == ERROR){
                Toast.makeText(context, "Error : \n" + msg.getData().getString(ERROR_KEY), Toast.LENGTH_SHORT);
            }else if(msg.what == DATA){
                String result = msg.getData().getString(DATA_KEY).toString();
                //서버에 메세지 보낸후 성공메세지 받으면 이동
                if(result.startsWith("#"))
                {
                    Toast.makeText(context, "log-in 성공!.", Toast.LENGTH_SHORT).show();
                    goToMain();
                }
                else
                    Toast.makeText(context, "log-in 실패..", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void goToMain()
    {
        Intent intent = new Intent(this, tab_main.class);
        startActivity(intent);
        this.finish();
    }

}
