package eightjo.modong;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__info_modify);

        loadDB();
        textView_modify_id = (TextView)findViewById(R.id.textView_modify_id);
        editText_modify_pw = (EditText)findViewById(R.id.editText_modify_pw);
        textView_modify_name = (TextView)findViewById(R.id.textView_modify_name);
        editText_modify_job = (EditText)findViewById(R.id.editText_modify_job);
        editText_modify_age = (EditText)findViewById(R.id.editText_modify_age);
        editText_modify_phone = (EditText)findViewById(R.id.editText_modify_phone);

        //forAppInfo : appInfo : 1. user_id 2. user_pw 3. name 4. job 5. age 6. phone 7. lock_flag 8. lock_pw 9. point 10. type 11. login_flag 12. group_flag 13. bacode
        Cursor c = db.rawQuery("SELECT * FROM appInfo where login_flag =1;", null);
        c.moveToPosition(c.getCount() - 1);
        textView_modify_id.setText(c.getString(c.getColumnIndex("user_id")));
        editText_modify_pw.setText(c.getString(c.getColumnIndex("user_pw")));
        textView_modify_name.setText(c.getString(c.getColumnIndex("name")));
        editText_modify_job.setText(c.getString(c.getColumnIndex("job")));
        editText_modify_age.setText(c.getString(c.getColumnIndex("age")));
        editText_modify_phone.setText(c.getString(c.getColumnIndex("phone")));

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
        db.execSQL("UPDATE appInfo SET " +
                "user_pw='"+ editText_modify_pw.getText().toString() + "',"+
                "job='"+ editText_modify_job.getText().toString() + "'," +
                "age="+ editText_modify_age.getText().toString() + ","+
                "phone='"+ editText_modify_phone.getText().toString() + "'"+
                " WHERE login_flag =1;");

        //Toast.makeText(this, editText_modify_pw.getText().toString(),Toast.LENGTH_SHORT).show();
        finish();
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
}
