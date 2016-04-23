package eightjo.modong;

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
public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        loadDB();
        Cursor c = db.rawQuery("SELECT * FROM tempInfo2;", null);
        if(c == null)//로그인 되어있지 않다.
        {
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        }
        else// if(c.getCount() > 0)//로그인 되어있는 앱.
        {    //Toast.makeText(this, c.getInt(6) + ", "+""+ "", Toast.LENGTH_SHORT).show();

            if(true)
            {
                Intent intent = new Intent(this, tab_main.class);
                // intent.putExtra("userName", c.getString(2));
                //  intent.putExtra("userPoint", c.getInt(5));
                // intent.putExtra("userGroup_flag", c.getInt(9));
                startActivity(intent);

            }
            else
            {

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

    public void loadDB()
    {
        db= openOrCreateDatabase(
                "userInfo.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS tempInfo2 " +
                "(" +
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
