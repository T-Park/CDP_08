package eightjo.modong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


//임시로 회원가입시 tempInfo 테이블에 정보가 들어감.
public class activity_sign_in extends Activity {

    EditText editText_userId;
    EditText editText_password;
    EditText editText_name;
    EditText editText_job;
    EditText editText_age;
    EditText editText_phone;

    boolean id_flag;

    Context context;
    private static final int ERROR = -1;
    private static final int DATA = 2;
    private static final String ERROR_KEY = "_error";
    private static final String DATA_KEY = "_data";


    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_sign_in);

        id_flag = false;
        editText_userId = (EditText)findViewById(R.id.editText_userId);
        editText_password = (EditText)findViewById(R.id.editText_password);
        editText_name = (EditText)findViewById(R.id.editText_name);
        editText_job = (EditText)findViewById(R.id.editText_job);
        editText_age = (EditText)findViewById(R.id.editText_age);
        editText_phone = (EditText)findViewById(R.id.editText_phone);

        loadDB();

        context = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_sign_in, menu);
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

    public void onClick_confirmId(View v)
    {
        String userId = editText_userId.getText().toString();
        try
        {
            SocketClient client = new SocketClient("20.20.3.188", 5555, this, "#ModongExistId%" + userId, uiHandler);
            client.start();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "서버 연결 실패" , Toast.LENGTH_LONG);
        }
    }

    public void DoSignIn2(View v)
    {

    }


    public void DoSignIn(View v)
    {

        String userId = editText_userId.getText().toString();
        String name = editText_name.getText().toString();
        String user_pw = editText_password.getText().toString();
        String job = editText_job.getText().toString();
        String age = editText_age.getText().toString();
        String phone = editText_phone.getText().toString();

        if(!id_flag)
        {
            Toast.makeText(this, "id중복확인을 해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        //forAppInfo : appInfo : 1. user_id 2. user_pw 3. name 4. job 5. age 6. phone 7. lock_flag 8. lock_pw 9. point 10. type 11. login_flag 12. group_flag 13. bacode
        if(!user_pw.equals("비밀번호") && !userId.equals("아이디") && !name.equals("이름") && !age.equals("나이"))
        {

            db.execSQL("INSERT INTO appInfo (user_id, user_pw, name, job, age, phone, lock_flag, lock_pw , point, type, login_flag, group_flag, bacode) " +
                    "VALUES (" +
                    "'" + userId + "'," +
                    "'" + user_pw + "'," +
                    "'" + name + "', " +
                    "'" + job + "', " +
                    age + "," +
                    "'" + phone + "', " +
                    "0," +
                    "null," +
                    "0," +
                    "'" + "Z" + "'," +
                    "1," +
                    "0," +
                    "null" +
                    ");");


            SocketClient client = new SocketClient("20.20.3.188", 5555, this, "#ModongJoin%"
                   + userId + "%"
                   + user_pw + "%"
                   + name + "%"
                    + job + "%"
                    + age + "%"
                    + phone, uiHandler);
            client.start();
        }
        else
        {
            Toast.makeText(this,"ID, 비밀번호, 이름란을 꼭 채워주세요. " , Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public  void onResume()
    {
        super.onResume();
        loadDB();
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
                " lock_pw TEXT,"+
                " point INTEGER," +
                " type TEXT,"+
                " login_flag INTEGER," +
                " group_flag INTEGER," +
                " bacode TEXT"+
                ");");
    }

    Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == ERROR){
                Toast.makeText(context, "Error : \n" + msg.getData().getString(ERROR_KEY), Toast.LENGTH_SHORT).show();
            }else if(msg.what == DATA){
                String result = msg.getData().getString(DATA_KEY).toString();

               //아이디 중복확인
                if(result.startsWith("#id"))
                {
                    Toast.makeText(context, "사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    id_flag = true;
                }
                else if( result.startsWith("#sign"))//회원가입 성공.
                {
                    Toast.makeText(context, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                    activityFinish();
                }
                else //실패
                {
                    Toast.makeText(context, "실패 하였습니다." + result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    public void activityFinish()
    {
        this.finish();
    }

}
