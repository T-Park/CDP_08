package eightjo.modong;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class tab_useList extends Activity {

    SQLiteDatabase db;
    String user_id;

    ListView myBuyList;
    useListAdapter useListAdapter;
    ArrayList<Item> arrayList;

    Cursor c;

    Context context;
    private static final int ERROR = -1;
    private static final int DATA = 2;
    private static final int DATANUM = 0;
    private static final String ERROR_KEY = "_error";
    private static final String DATA_KEY = "_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_use_list);
        loadUser_id();
        loadMyUseList();
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

    @Override
    public void onResume()
    {
        super.onResume();
        loadMyUseList();
    }

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

        if(db != null)
        {
            db.close();
        }
    }

    public void loadMyUseList() {
        //user id 로 사용내역 불러오기 메소드
        try
        {
            SocketClient client = new SocketClient(this, "#ModongUseList%" + user_id, uiHandler);
            client.start();//
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
            }
            else if(msg.what == DATANUM)//사용 리스트 불러오기
            {
                String result = msg.getData().getString(DATA_KEY).toString();
                if(result.startsWith("#"))
                {
                    //Toast.makeText(context, "기부가 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    String[] tokens = result.split("%");
                    int n = Integer.parseInt(tokens[0].substring(1));

                    arrayList = new ArrayList<Item>();
                    for(int i=1; i<= n; i++)
                    {
                        String[] itemTokens = tokens[i].split("@");
                        arrayList.add(new Item(itemTokens[0], itemTokens[1], itemTokens[2]));
                    }

                    useListAdapter = new useListAdapter(context, R.layout.item, arrayList);
                    myBuyList.setAdapter(useListAdapter);
                }
                else //실패
                {
                    Toast.makeText(context, "사용목록 불러오기 : 실패 하였습니다." + result, Toast.LENGTH_SHORT).show();
                }

            }

        }
    };

}
