package eightjo.modong;

import android.app.Activity;
//<<<<<<< Updated upstream
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//=//======
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
//>>>>>>> Stashed changes
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//<<<<<<< Updated upstream
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class tab_cardViewer extends Activity {

    SQLiteDatabase db;
    String user_name;
    int group_flag, point;
    //Button button_donation, button_give;

    ImageView imageView_bacode;
//>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_card_viewer);
        loadLatestDB();

        TextView textView_view_name, textView_view_group, textView_view_point;

        textView_view_name = (TextView)findViewById(R.id.textView_view_name);
        textView_view_group = (TextView)findViewById(R.id.textView_view_group);
        textView_view_point = (TextView)findViewById(R.id.textView_view_point);

        textView_view_name.setText(user_name);
        if(group_flag == 0)
            textView_view_group.setText("그룹이 없어요.");
        else
            textView_view_group.setText("그룹 안에 있어요");
        textView_view_point.setText(point + "P");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_card_viewer, menu);
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
        loadLatestDB();
    }

    public void loadDB()
    {
        if(db != null)
        {
            db.close();
        }

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

    public void loadLatestDB()
    {
        loadDB();
        Cursor c = db.rawQuery("SELECT * FROM appInfo where login_flag =1;", null);
        c.moveToPosition(c.getCount() - 1);
        user_name =  c.getString(c.getColumnIndex("name"));
        group_flag =  c.getInt(c.getColumnIndex("group_flag"));
        point = c.getInt(c.getColumnIndex("point"));

    }

    public void onClick_goToDonation(View v)
    {
        Intent intent = new Intent(this, activity_donate.class);
        startActivity(intent);
    }

    public void nClick_goToGivePointo(View v) {
        Intent intent = new Intent(this, activity_givePoint.class);
        startActivity(intent);
    }
}
