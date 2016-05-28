package eightjo.modong;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class tab_donationList extends Activity {

    SQLiteDatabase db;
    String user_id;

    TextView textView_sum_dPoint;
    ListView listView_dlist;
    ArrayList<Item> arrayList;
    useListAdapter useListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_donation_list);

        textView_sum_dPoint = (TextView)findViewById(R.id.textView_sum_dPoint);
        listView_dlist = (ListView)findViewById(R.id.listView_dlist);
        loadUser_id();

        arrayList = new ArrayList<Item>();
        arrayList.add(new Item("2016/05/14","개발자1","+1000"));
        arrayList.add(new Item("2013/05/14","개발자2","+400"));
        arrayList.add(new Item("2016/04/14","개발자2","+2500"));
        arrayList.add(new Item("2016/05/12","개발자1","+1500"));

        useListAdapter = new useListAdapter(this, R.layout.item, arrayList);
        listView_dlist.setAdapter(useListAdapter);

        textView_sum_dPoint.setText("나의 기부 포인트 : 5400 Point");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_donation_list, menu);
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

    public void loadDB() {
        if (db != null) {
            db.close();
        }

        db = openOrCreateDatabase(
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

    public void loadUser_id()
    {
        loadDB();
        Cursor c = db.rawQuery("SELECT * FROM appInfo where login_flag =1;", null);
        c.moveToPosition(c.getCount() - 1);
        user_id =  c.getString(c.getColumnIndex("user_id"));

    }

}
