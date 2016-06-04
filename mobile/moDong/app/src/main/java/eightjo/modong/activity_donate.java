package eightjo.modong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class activity_donate extends Activity {

    ListView myDonationList;
    donationListAdater donationListAdater;
    ArrayList<dItem> arrayList;
    TextView textView_choice, textView_current_point;
    EditText editText_donation_point;
    String choiceName;

    Context context;
    private static final int ERROR = -1;
    private static final int DATA = 2;
    private static final int DATANUM = 0;
    private static final String ERROR_KEY = "_error";
    private static final String DATA_KEY = "_data";

    String myId;
    int myPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_donate);

        Intent intent = getIntent();
        myId = intent.getStringExtra("id");
        myPoint = Integer.parseInt(intent.getStringExtra("point"));

        myDonationList = (ListView)findViewById(R.id.listView_donation);
        textView_choice = (TextView)findViewById(R.id.textView_choice);
        editText_donation_point = (EditText)findViewById(R.id.editText_donate_howMuch);
        textView_current_point = (TextView)findViewById(R.id.textView_donation_currentPoint);
        Log.i("current point ", myPoint + "/msg");
        textView_current_point.setText("현재나의 포인트 : " + myPoint);

        try
        {
            SocketClientForList client = new SocketClientForList(this, "#ModongLoadDonationList", uiHandler);//바꾸기
            client.start();
        }
        catch (Exception e)
        {

            Toast.makeText(this, "서버 연결 실패" , Toast.LENGTH_LONG).show();
        }




        context = this;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_donate, menu);
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

    Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == ERROR){
                Toast.makeText(context, "Error : \n" + msg.getData().getString(ERROR_KEY), Toast.LENGTH_SHORT).show();
            }else if(msg.what == DATA){
                String result = msg.getData().getString(DATA_KEY).toString();
                //기부성공
                if(result.startsWith("#"))
                {
                    Toast.makeText(context, "기부가 성공적으로 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    activityFinish();
                }
                else //실패
                {
                    Toast.makeText(context, "기부하기 : 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }else if(msg.what == DATANUM)//기부 리스트 불러오기
            {
                String result = msg.getData().getString(DATA_KEY).toString();
                if(result.startsWith("#"))
                {
                    String[] tokens = result.split("%");
                    int n = Integer.parseInt(tokens[0].substring(1));

                    arrayList = new ArrayList<dItem>();
                    for(int i=1; i<= n; i++)
                        arrayList.add(new dItem(tokens[i]));

                    donationListAdater = new donationListAdater(context, R.layout.ditem, arrayList);
                    myDonationList.setAdapter(donationListAdater);
                    myDonationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            choiceName = arrayList.get(position).getDname();
                            textView_choice.setText("'" + choiceName + "'에" );
                        }
                    });
                }
                else //실패
                {
                    Toast.makeText(context, "기부목록 불러오기 : 실패 하였습니다." + result, Toast.LENGTH_SHORT).show();
                }

            }

        }
    };

    public void onClick_donate(View v)
    {
        try
        {
            int point = Integer.parseInt(editText_donation_point.getText().toString());
            if(myPoint < point)
            {
                Toast.makeText(this, "포인트가 부족해요." , Toast.LENGTH_SHORT).show();
            }

            SocketClient client = new SocketClient(this, "#ModongDonation%" + myId +"%"+ choiceName + "%" + point, uiHandler);
            client.start();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "서버 연결 실패" , Toast.LENGTH_LONG).show();
        }

    }


    public void onClick_cancel(View v)
    {
        finish();
    }

    public void activityFinish()
    {
        this.finish();
    }

}
