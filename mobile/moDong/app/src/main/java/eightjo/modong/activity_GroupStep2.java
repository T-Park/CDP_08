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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class activity_GroupStep2 extends Activity {

    String groupName, myId;
    EditText editText_m1, editText_m2, editText_m3;
    TextView textView_myId;

    Context context;
    String getString;
    private static final int ERROR = -1;
    private static final int DATA = 2;
    private static final String ERROR_KEY = "_error";
    private static final String DATA_KEY = "_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__group_step2);

        Intent intent = getIntent();
        groupName = intent.getStringExtra("groupName");
        myId = intent.getStringExtra("myId");
        TextView textView_groupNameView = (TextView)findViewById(R.id.textView_groupNameView);
        textView_groupNameView.setText(groupName);

        editText_m1 = (EditText)findViewById(R.id.editText_member1);
        editText_m2 = (EditText)findViewById(R.id.editText_member2);
        editText_m3 = (EditText)findViewById(R.id.editText_member3);
        textView_myId = (TextView)findViewById(R.id.textView_myId);
        textView_myId.setText("+ " + myId);

        context = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__group_step2, menu);
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

    public void onClick_goNext(View v)
    {
        int n =1;
        String msg1 = "#ModongGroupIn%";
        String msg2 = "";
        String m1_id = editText_m1.getText().toString();
        String m2_id = editText_m2.getText().toString();
        String m3_id = editText_m3.getText().toString();

        if(m1_id.equals("요원1") &&
                m2_id.equals("요원2") &&
                    m3_id.equals("요원3"))
        {
            Toast.makeText(this, "한명이상 함께할 id를 입력해주세요~", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] ids = new String[3];
        ids[0] = ""; ids[1] = ""; ids[2] = "";
        if(!m1_id.equals("요원1"))
        {
            ids[n-1] = m1_id;
            n++;
            msg2 = msg2 + "%" + m1_id;
        }
        if(!m2_id.equals("요원2"))
        {
            ids[n-1] = m2_id;
            n++;
            msg2 = msg2 + "%" + m2_id;
        }
        if(!m3_id.equals("요원3"))
        {
            ids[n-1] = m2_id;
            n++;
            msg2 = msg2 + "%" + m3_id;
        }
       //서버로 메세지!
        try
        {
            String msg =  msg1 + n + "%" + myId
                    + msg2+ "%" + groupName;
            Log.i("msg : ", msg);
            SocketClient client = new SocketClient(this,msg, uiHandler);//155.230.86.190
            client.start();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "서버 연결 실패" , Toast.LENGTH_LONG);
        }


        //Toast.makeText(this, "그룹바코드 구현해야해!", Toast.LENGTH_SHORT).show();
    }

    Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == ERROR){
                Toast.makeText(context, "Error : \n"+msg.getData().getString(ERROR_KEY), Toast.LENGTH_SHORT).show();
            }else if(msg.what == DATA){
                String result =  msg.getData().getString(DATA_KEY).toString();
                if(result.startsWith("#"))
                {
                    Toast.makeText(context, "그룹이 만들어졌어요! 동기화를 눌러주세요.", Toast.LENGTH_SHORT).show();
                    activityFinish();
                }

            }
        }
    };
    public void activityFinish()
    {
        this.finish();
    }
}
