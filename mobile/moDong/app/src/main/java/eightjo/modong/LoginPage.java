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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends Activity {

    EditText editText_id;
    EditText editText_pw;

    Context context;
    private static final int ERROR = -1;
    private static final int DATA = 2;
    private static final String ERROR_KEY = "_error";
    private static final String DATA_KEY = "_data";
    String getString, id, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        editText_id = (EditText)findViewById(R.id.editText_login_id);
        editText_pw = (EditText)findViewById(R.id.editText_login_pw);
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

    public void goToService(View v)
    {
        requestLogin();
    }

    public void requestLogin()
    {
        //Toast.makeText(this, "55555" , Toast.LENGTH_LONG).show();
        try
        {
            id = editText_id.getText().toString();
            pw = editText_pw.getText().toString();

            Toast.makeText(this, id + "  " + pw , Toast.LENGTH_LONG).show();

            SocketClient client = new SocketClient(this, "#ModongLogin%" + id + "%" + pw, uiHandler);
            client.start();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "서버 연결 실패" , Toast.LENGTH_LONG).show();
        }
    }


    Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == ERROR){
                Toast.makeText(context, "Error : \n" + msg.getData().getString(ERROR_KEY), Toast.LENGTH_SHORT).show();
            }else if(msg.what == DATA){
                String result = msg.getData().getString(DATA_KEY).toString();
                //서버에 메세지 보낸후 성공메세지 받으면 이동
                if(result.startsWith("#"))
                {
                    Toast.makeText(context, "log-in 성공!.", Toast.LENGTH_SHORT).show();
                    insertUserInfo(result);
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


    public void insertUserInfo(String msg)
    {
        SQLiteDatabase db;

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        String[] tokens = msg.split("%");

        db= openOrCreateDatabase(
                "userInfo.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS mdUser2 " +
                "(" +
                " id TEXT," +
                " pw TEXT," +
                " name TEXT," +
                " barcode TEXT," +
                " group_code INTEGER default -1," +
                " group_name TEXT," +
                " group_barcode TEXT," +
                " lock_flag INTEGER default 0," +
                " lock_pw TEXT" +
                ");");

        db.execSQL("INSERT INTO mdUser2 (id, pw, name, barcode, " +
                "group_code, group_name, group_barcode, " +
                "lock_flag, lock_pw) " +
                "VALUES (" +
                "'" + id + "'," +
                "'" + pw + "'," +
                "'" + tokens[2].toString() + "', " +
                "'" + tokens[3].toString() + "' ," +
                tokens[4] + "," +
                "'" + tokens[5] + "'," +
                "'" + tokens[6] + "'," +
                "0," +
                "'null'" +
                ");");

        Log.i("GROUP_CODE ", tokens[4]);

        if(db != null)
        db.close();
    }

}
