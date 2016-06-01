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
import android.widget.TextView;
import android.widget.Toast;

public class activity_Info_modify extends Activity {

    SQLiteDatabase db;
    TextView textView_modify_id;
    EditText editText_modify_pw;
    TextView textView_modify_name;
    EditText editText_modify_job;
    EditText editText_modify_age;
    EditText editText_modify_phone;

    Context context;
    String id, pw, name, job, age, tel;
    private static final int ERROR = -1;
    private static final int DATA = 2;
    private static final String ERROR_KEY = "_error";
    private static final String DATA_KEY = "_data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__info_modify);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        pw = intent.getStringExtra("pw");
        name = intent.getStringExtra("name");
        job = intent.getStringExtra("job");
        age = intent.getStringExtra("age");
        tel = intent.getStringExtra("tel");

        loadDB();
        textView_modify_id = (TextView)findViewById(R.id.textView_modify_id);
        editText_modify_pw = (EditText)findViewById(R.id.editText_modify_pw);
        textView_modify_name = (TextView)findViewById(R.id.textView_modify_name);
        editText_modify_job = (EditText)findViewById(R.id.editText_modify_job);
        editText_modify_age = (EditText)findViewById(R.id.editText_modify_age);
        editText_modify_phone = (EditText)findViewById(R.id.editText_modify_phone);

        textView_modify_id.setText(id);
        editText_modify_pw.setText(pw);
        textView_modify_name.setText(name);
        editText_modify_job.setText(job);
        editText_modify_age.setText(age);
        editText_modify_phone.setText(tel);

        context = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__info_modify, menu);
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

    public void onClick_save(View v)
    {
        db.execSQL("UPDATE mdUser2 SET " +
                "pw='"+ editText_modify_pw.getText().toString() + "'"+
                " ;");

        //Toast.makeText(this, editText_modify_pw.getText().toString(),Toast.LENGTH_SHORT).show();
        finish();
    }

    //forAppInfo : appInfo : 1. user_id 2. user_pw 3. name 4. job 5. age 6. phone 7. lock_flag 8. lock_pw 9. point 10. type 11. login_flag 12. group_flag 13. bacode
    public void loadDB()
    {
        if(db != null)
        {
            db.close();
        }

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
                " group_code INTEGER," +
                " group_name TEXT," +
                " group_barcode TEXT," +
                " lock_flag INTEGER," +
                " lock_pw TEXT" +
                ");");
    }

    public void sendToServer(View v)
    {
          id =  textView_modify_id.getText().toString();
          pw =  editText_modify_pw.getText().toString();
          name = textView_modify_name.getText().toString();
          job = editText_modify_job.getText().toString();
          age = editText_modify_age.getText().toString();
          tel =editText_modify_phone.getText().toString();

        try
        {
            Toast.makeText(context,  "#ModongModify%" +
                    id+ "%" + pw + "%" + name + "%" + job + "%" + age + "%" + tel, Toast.LENGTH_SHORT).show();
            SocketClient client = new SocketClient( context, "#ModongModify%" +
                    id+ "%" + pw + "%" + name + "%" + job + "%" + age + "%" + tel, uiHandler);
            client.start();//
        }
        catch (Exception e)
        {
            Toast.makeText( context, "서버 연결 실패" , Toast.LENGTH_LONG);
        }

    }

    Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == ERROR){
                Toast.makeText(context, "Error : \n"+msg.getData().getString(ERROR_KEY), Toast.LENGTH_SHORT).show();
            }else if(msg.what == DATA){
                String result =  msg.getData().getString(DATA_KEY).toString();
                if(result.startsWith("#"))
                {
                    Toast.makeText(context, "성공적으로 수정 했어요!", Toast.LENGTH_SHORT).show();
                    activityFinish();
                }
                else
                {
                    Toast.makeText(context, "fail : " + result, Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    public void activityFinish()
    {
        this.finish();
    }
}
