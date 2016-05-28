package eightjo.modong;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class tab_useList extends Activity {

    ListView myBuyList;
    useListAdapter useListAdapter;
    ArrayList<Item> arrayList;

    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_buy_list);

        Item item1 = new Item("2016/05/14","학생식당","+500");
        Item item2 = new Item("2016/05/12","gs편의점","-40");

        myBuyList = (ListView) findViewById(R.id.listView_buyList);
        arrayList = new ArrayList<Item>();

        arrayList.add(item1);
        arrayList.add(item2);
        useListAdapter = new useListAdapter(this, R.layout.item, arrayList);
        myBuyList.setAdapter(useListAdapter);

        myBuyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_buy_list, menu);
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
}
