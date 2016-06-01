package eightjo.modong;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class tab_donationList extends Activity {

    SQLiteDatabase db;
    String user_id;
    int sumPoint;

    TextView textView_sum_dPoint;
    ListView listView_dlist;
    ArrayList<Item> arrayList;
    useListAdapter useListAdapter;

    Context context;
    private static final int ERROR = -1;
    private static final int DATA = 2;
    private static final int DATANUM = 0;
    private static final String ERROR_KEY = "_error";
    private static final String DATA_KEY = "_data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_donation_list);

        textView_sum_dPoint = (TextView)findViewById(R.id.textView_sum_dPoint);
        listView_dlist = (ListView)findViewById(R.id.listView_dlist);
        context = this;

        loadMyDonationList();
    }

    @Override
    public  void onResume()
    {
        super.onResume();
        loadMyDonationList();
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

    public void loadMyDonationList()
    {
        try
        {
            loadUser_id();
            SocketClientForList client = new SocketClientForList( this, "#ModongDonationList%" + user_id, uiHandler);//test
            client.start();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "서버 연결 실패" , Toast.LENGTH_LONG);
        }
    }

    Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == ERROR){
                Toast.makeText(context, "Error : \n" + msg.getData().getString(ERROR_KEY), Toast.LENGTH_SHORT).show();
            }else if(msg.what == DATANUM){
                String result = msg.getData().getString(DATA_KEY).toString();
                sumPoint = 0;
                //기부 리스트 불러오기
                if(result.startsWith("#"))
                {
                    //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                    arrayList = new ArrayList<Item>();
                    String[] token = result.split("%");
                    int n = Integer.parseInt(token[0].substring(1));
                    for(int i=1; i<=n; i++)
                    {

                        String[] inToken = token[i].split("@");
                        String when = inToken[0];
                        String where = inToken[1];
                        String tempPoint = inToken[2];

                        int point = Integer.parseInt(tempPoint);
                        Log.i("inToken",i + " : " + point);
                        String pointStr = "+" + point;
                        arrayList.add(new Item(when, where, pointStr));

                        sumPoint = sumPoint + point;
                    }


                    useListAdapter = new useListAdapter(context, R.layout.item, arrayList);
                    listView_dlist.setAdapter(useListAdapter);
                    textView_sum_dPoint.setText("나의 기부 포인트 : " +sumPoint + " Point");
                }
                else //실패
                {
                    Toast.makeText(context, "실패 하였습니다." + result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

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
                " group_code INTEGER default -1," +
                " group_name TEXT," +
                " group_barcode TEXT," +
                " lock_flag INTEGER default 0," +
                " lock_pw TEXT" +
                ");");
    }

    public void loadUser_id()
    {
        loadDB();
        Cursor c = db.rawQuery("SELECT * FROM mdUser2;", null);
        c.moveToPosition(c.getCount() - 1);
        user_id =  c.getString(c.getColumnIndex("id"));
    }
}
