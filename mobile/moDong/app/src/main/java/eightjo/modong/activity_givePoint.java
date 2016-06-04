package eightjo.modong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class activity_givePoint extends Activity {

    EditText editText_toId, editText_point;
    TextView textView_currentPoint;
    String fromId; int currentPoint;

    Context context;
    private static final int ERROR = -1;
    private static final int DATA = 2;
    private static final String ERROR_KEY = "_error";
    private static final String DATA_KEY = "_data";
    boolean id_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_give_point);

        context = this;
        Intent intent = getIntent();
        fromId = intent.getStringExtra("id");
        String strPoint = intent.getStringExtra("point");

        if(strPoint == null)
            currentPoint = 0;
        else
            currentPoint = Integer.parseInt(strPoint);


        editText_toId = (EditText)findViewById(R.id.editText_toId);
        editText_point = (EditText)findViewById(R.id.editText_point);
        textView_currentPoint = (TextView)findViewById(R.id.textView_currentPoint);
        textView_currentPoint.setText("현재 포인트 : " + currentPoint);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_give_point, menu);
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

                //존재하는 아이디인가
                if(result.startsWith("#id"))
                {
                    Toast.makeText(context, "존재하지 않는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                }
                else if( result.startsWith("#g"))//선물하기 성공.
                {
                    Toast.makeText(context, "선물하기 성공!", Toast.LENGTH_SHORT).show();
                    activityFinish();
                }
                else if(result.startsWith("f"))
                {
                    Toast.makeText(context, "존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    id_flag = true;
                }
            }
        }
    };

    public void onClick_givePoint(View v)
    {
        String toId = editText_toId.getText().toString();
        String point = editText_point.getText().toString();

        if(currentPoint < Integer.parseInt(point))
        {
            Toast.makeText(this, "포인트가 부족해요!", Toast.LENGTH_SHORT).show();
            return;
        }

        try
        {
            SocketClient client = new SocketClient( this, "#ModongGivePoint%" + fromId + "%" + toId+ "%" +point , uiHandler);
            client.start();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "서버 연결 실패" , Toast.LENGTH_LONG).show();
        }

    }

    public void onClick_confirmId(View v)
    {
        String userId = editText_toId.getText().toString();
        try
        {
            SocketClient client = new SocketClient( this, "#ModongExistId%" + userId, uiHandler);
            client.start();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "서버 연결 실패" , Toast.LENGTH_LONG);
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
