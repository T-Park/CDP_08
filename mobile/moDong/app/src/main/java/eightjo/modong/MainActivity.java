package eightjo.modong;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//tempInfo : 1. id  2. name 3. user_id 4. password 5. point 6. type 7. login_flag 8. lock_flag 9. group_flag 10. bacode
//tempInfo2 :1. name 2. user_id 3. password 4. point 5. type 6. login_flag 7. lock_flag 8. group_flag 9. bacode
//testInfo : tempInfo2 : 1. name 2. user_id 3. user_pw 4. lock_pw 5. lock_flag
//forAppInfo : appInfo : 1. user_id 2. user_pw 3. name 4. lock_flag 5. lock_pw 6. point 7. type 8. login_flag 9. group_flag 10. bacode
//forAppInfo : appInfo : 1. user_id 2. user_pw 3. name 4. job 5. age 6. phone 7. lock_flag 8. lock_pw 9. point 10. type 11. login_flag 12. group_flag 13. bacode
public class MainActivity extends Activity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadDB();
        Cursor c = db.rawQuery("SELECT * FROM appInfo;", null);//Toast.makeText(this, c.toString(), Toast.LENGTH_SHORT).show();
        if( c.getCount() == 0 )//로그인 되어있지 않다.
        {
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        }
        else// if(c.getCount() > 0)//로그인 되어있는 앱.
        {    //Toast.makeText(this, c.getInt(6) + ", "+""+ "", Toast.LENGTH_SHORT).show();
            c.moveToPosition(c.getCount()-1);
            if(c.getInt(c.getColumnIndex("lock_flag")) == 0)//잠겨있지 않다면
            {
                Intent intent = new Intent(this, tab_main.class);
                // intent.putExtra("userName", c.getString(2));
                //  intent.putExtra("userPoint", c.getInt(5));
                // intent.putExtra("userGroup_flag", c.getInt(9));
                startActivity(intent);

            }
            else//잠겨있다면
            {
                Intent intent = new Intent(this, activity_lock.class);
                intent.putExtra("lock_pw",c.getString(c.getColumnIndex("lock_pw")));
                startActivity(intent);
            }

        }
        if(db!= null) {
            db.close();
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


