package eightjo.modong;

import android.app.Activity;
import android.content.Intent;
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
        editText_password = (EditText)findViewById(R.id.editText_password);
        editText_name = (EditText)findViewById(R.id.editText_name);
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

    public void DoSignIn(View v)
    {
        //임시 메소드
        String userId = editText_userId.getText().toString();
        db.execSQL("INSERT INTO tempInfo2 (name, user_id, password, point , type,  login_flag, lock_flag, group_flag) " +
                "VALUES ('"+ editText_name.getText().toString() +"', " +
                "'"+ editText_userId.getText().toString() + "'," +
                "'"+ editText_password.getText().toString() + "'," +
                "424,"+
                "'"+"type"+"',"+
                "0," +
                "0," +
                "0);");

       // Toast.makeText(this, "user name : "+ editText_name.getText().toString() +"", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);

        //서버 통신

        finish();
    }

    public void loadDB()
    {
        db= openOrCreateDatabase(
                "userInfo.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS tempInfo " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name TEXT," +
                " user_id TEXT," +
                " password TEXT," +
                " point INTEGER," +
                " type TEXT," +
                " login_flag INTEGER," +
                " lock_flag INTEGER," +
                " group_flag INTEGER," +
                " bacode TEXT);");
    }

}
