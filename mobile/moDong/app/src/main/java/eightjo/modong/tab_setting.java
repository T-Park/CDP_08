package eightjo.modong;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class tab_setting extends Activity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_setting);
        loadDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_setting, menu);
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

    public void button_InfoModify(View v)
    {


    }

    public void button_Group(View v)
    {


    }

    public void button_Lock(View v)
    {


    }

    public void button_Logout(View v)
    {
        db.execSQL("DROP TABLE tempInfo2;");//DROP TABLE tempInfo2
        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
        finish();
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
