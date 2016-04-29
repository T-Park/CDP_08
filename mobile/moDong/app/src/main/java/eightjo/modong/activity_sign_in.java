package eightjo.modong;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//임시로 회원가입시 tempInfo 테이블에 정보가 들어감.
public class activity_sign_in extends Activity {

    EditText editText_userId;
    EditText editText_password;
    EditText editText_name;
    EditText editText_job;
    EditText editText_age;
    EditText editText_phone;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_sign_in);

        editText_userId = (EditText)findViewById(R.id.editText_userId);
        editText_userId.setText("");
        editText_password = (EditText)findViewById(R.id.editText_password);
        editText_password.setText("");
        editText_name = (EditText)findViewById(R.id.editText_name);
        editText_name.setText("");
        editText_job = (EditText)findViewById(R.id.editText_job);
        editText_age = (EditText)findViewById(R.id.editText_age);
        editText_phone = (EditText)findViewById(R.id.editText_phone);

        loadDB();
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
        int int_age;
        String phone = editText_phone.getText().toString();

        //forAppInfo : appInfo : 1. user_id 2. user_pw 3. name 4. job 5. age 6. phone 7. lock_flag 8. lock_pw 9. point 10. type 11. login_flag 12. group_flag 13. bacode
        if(!user_pw.equals("") && !userId.equals("") && !name.equals(""))
        {
            db.execSQL("INSERT INTO appInfo (user_id, user_pw, name, job, age, phone, lock_flag, lock_pw , point, type, login_flag, group_flag, bacode) " +
                    "VALUES (" +
                    "'" + userId + "'," +
                    "'" + user_pw + "'," +
                    "'" + name + "', " +
                    "'" + job + "', " +
                     age +","+
                    "'" + phone + "', " +
                    "0,"+
                    "null," +
                    "0,"+
                    "'" + "Z" + "'," +
                    "1,"+
                    "0,"+
                    "null" +
                    ");");
            Toast.makeText(this, "name, userId pw : "+ name +"," + userId + "," + user_pw, Toast.LENGTH_SHORT).show();
            finish();
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

}
