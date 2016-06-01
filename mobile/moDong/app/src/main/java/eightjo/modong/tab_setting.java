package eightjo.modong;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class tab_setting extends Activity {

    SQLiteDatabase db;

    private String user_id;
    private String user_pw;
    boolean group_flag;
    boolean lock_flag;

    Button button_modify, button_lock;
    Button button_save, button_cancel;//192.168.99.1
    EditText editText_dialog_password, editText_dialog_password_before;
    TextView textView_dialog1, textView_dialog2;
    Context mContext;
    String temp_pw;

    private static final int ERROR = -1;
    private static final int DATA = 2;
    private static final String ERROR_KEY = "_error";
    private static final String DATA_KEY = "_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_setting);
        loadLatestDB();

        button_modify = (Button)findViewById(R.id.button_Info_modify);
        button_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tokken =0;
                showDialog(0);
            }

        });

        button_lock = (Button)findViewById(R.id.button_lock);
        button_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tokken = 1;
                showDialog(3);
            }

        });
        mContext = this;



    }


    public void loadLatestDB()
    {
        loadDB();
        Cursor c = db.rawQuery("SELECT * FROM mdUser2 ;", null);
        c.moveToPosition(c.getCount() - 1);
        user_id =  c.getString(c.getColumnIndex("id"));
        user_pw =  c.getString(c.getColumnIndex("pw"));
        String group_code = c.getString(c.getColumnIndex("group_code"));
        if(group_code == null || group_code.equals("null") || group_code.equals("-1"))//고치자
            group_flag = false;
        else
            group_flag = true;
        int lockN = c.getInt(c.getColumnIndex("lock_flag"));
        if(lockN == 1)
        {
            lock_flag = true;
        }
        else
            lock_flag = false;

    }

    @Override
    public  void onResume()
    {
        super.onResume();
        //textView_dialog1.setText("");
        //textView_dialog2.setText("");
        loadLatestDB();
    }

    public void unLock()
    {
        if(db != null)
        db.execSQL("UPDATE mdUser2 SET " +
                "lock_flag=0" +
                "");
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        Dialog dialog = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = new Dialog(tab_setting.this);
        dialog.setContentView(R.layout.dialog_for_password);

        textView_dialog1 = (TextView)dialog.findViewById(R.id.textView_dialog_1);
        textView_dialog2 = (TextView)dialog.findViewById(R.id.textView_dialog_2);
        button_save = (Button)dialog.findViewById(R.id.button_dialog_save);
        button_cancel = (Button)dialog.findViewById(R.id.button_dialog_cancel);
        editText_dialog_password = (EditText)dialog.findViewById(R.id.editText_dialog_password);

        switch (id)
        {
            case 0 :

                //현재 id 입력필요
                loadLatestDB();
                dialog.setTitle("사용자 인증");
                textView_dialog1.setText("ID :" + user_id);

                button_save.setOnClickListener(saveOnClickListener);
                button_cancel.setOnClickListener(cancelOnClickListener);

                break;
            case 1 :
                dialog.setTitle("비밀번호 입력");
                textView_dialog1.setText("잠금을 위한");
                textView_dialog2.setText("비밀번호를 입력해 주세요.");

                editText_dialog_password_before = (EditText)dialog.findViewById(R.id.editText_dialog_password);
                editText_dialog_password_before.setText("");

                button_save.setOnClickListener(saveOnClickListenerForLock2);
                button_cancel.setOnClickListener(cancelOnClickListener1);

                break;
            case 2 :
                dialog.setTitle("비밀번호 확인");
                textView_dialog1.setText("비밀번호를");
                textView_dialog2.setText("한번 더 입력해 주세요.");

                button_save.setOnClickListener(saveOnClickListenerForLock3);
                button_cancel.setOnClickListener(cancelOnClickListener2);
                break;
            case 3 :
                //loadLatestDB();
                dialog.setTitle("사용자 인증");
                textView_dialog1.setText("ID :" + user_id);
                button_save.setOnClickListener(saveOnClickListenerForLock);
                button_cancel.setOnClickListener(cancelOnClickListener3);
                break;
            /*
            case 4:
                dialog.setContentView(R.layout.dialog_for_password);
                textView_dialog1 = (TextView)dialog.findViewById(R.id.textView_dialog_1);
                button_save = (Button)dialog.findViewById(R.id.button_dialog_save);
                button_cancel = (Button)dialog.findViewById(R.id.button_dialog_cancel);


                button_cancel.setOnClickListener(cancelOnClickListener4);*/
        }



        return dialog;
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


    public void button_Logout(View v)
    {
        loadDB();
        db.execSQL("DROP TABLE mdUser2;");//DROP TABLE tempInfo2
        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
        finish();
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
                " group_code INTEGER," +
                " group_name TEXT," +
                " group_barcode TEXT," +
                " lock_flag INTEGER," +
                " lock_pw TEXT" +
                ");");
    }



    private Button.OnClickListener cancelOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            dismissDialog(0);
        }

    };

    private Button.OnClickListener cancelOnClickListener1
            = new Button.OnClickListener(){

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            dismissDialog(1);
        }

    };

    private Button.OnClickListener cancelOnClickListener2
            = new Button.OnClickListener(){

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            dismissDialog(2);
        }

    };

    private Button.OnClickListener cancelOnClickListener3
            = new Button.OnClickListener(){

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            dismissDialog(3);
        }

    };

    /*
    private Button.OnClickListener cancelOnClickListener4
            = new Button.OnClickListener(){

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            dismissDialog(4);
        }

    };
*/
    private Button.OnClickListener saveOnClickListener  //MODIFY
            = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            String value= editText_dialog_password.getText().toString();


            //password가 일치한다면
            if(value.equals(user_pw))
            {
                try
                {
                    SocketClient client = new SocketClient( mContext, "#ModongGetUserInfo%" + user_id + "%" + user_pw, uiHandler);
                    client.start();//
                }
                catch (Exception e)
                {
                    Toast.makeText(mContext, "서버 연결 실패" , Toast.LENGTH_LONG);
                }
                dismissDialog(0);
            }
            else
            {
                Toast.makeText(mContext, value +" : "+ user_pw + " 비밀번호가 틀렸어요!", Toast.LENGTH_SHORT).show();
            }
           // editText_dialog_password.setText("");
        }

    };

    private Button.OnClickListener saveOnClickListenerForLock
            = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            String value= editText_dialog_password.getText().toString();

            //password가 일치한다면
            if(value.equals(user_pw))
            {
                if(lock_flag)
                {
                    unLock();
                    Toast.makeText(mContext, "잠금해제 되었습니다.", Toast.LENGTH_SHORT).show();
                    dismissDialog(3);
                }
                else{
                    showDialog(1);
                    dismissDialog(3);
                }

            }
            else
            {
                Toast.makeText(mContext, value +" : "+ user_pw + " 비밀번호가 틀렸어요!", Toast.LENGTH_SHORT).show();
            }
        }

    };

    private Button.OnClickListener saveOnClickListenerForLock2//비밀번호 설정1
            = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            temp_pw = editText_dialog_password_before.getText().toString();
            if(temp_pw.equals(""))
            {
                Toast.makeText(mContext, "공백은 불가능해요!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                showDialog(2);
                dismissDialog(1);
            }
        }

    };

    private Button.OnClickListener saveOnClickListenerForLock3
            = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            String value= editText_dialog_password.getText().toString();
            if(value.equals(temp_pw))
            {
                //db 처리
                db.execSQL("UPDATE mdUser2 SET " +
                        "lock_pw='"+ value + "',"+
                        "lock_flag=1" +
                        "");
                Toast.makeText(mContext, "일치합니다. 비밀번호 : "+ value + " == "+ temp_pw, Toast.LENGTH_SHORT).show();
                //loadLatestDB();
            }
            else
            {
                Toast.makeText(mContext, "이전의 비밀번호와 일치하지 않습니다."+ value + " != "+ temp_pw, Toast.LENGTH_SHORT).show();
            }

            dismissDialog(2);
        }

    };

    public void onClick_goNext(View v)
    {
        if(group_flag)
        {
            Toast.makeText(this, "이미 그룹안에 있어요!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent =new Intent(this, activity_GroupStep1.class);
        intent.putExtra("myId", user_id);
        startActivity(intent);
    }



    Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == ERROR){
                Toast.makeText(mContext, "Error : \n"+msg.getData().getString(ERROR_KEY), Toast.LENGTH_SHORT).show();
            }else if(msg.what == DATA){
                String result =  msg.getData().getString(DATA_KEY).toString();
                // textView_view_name.setText(result);
                if(result.startsWith("#"))
                {
                    String[] tokens = result.split("%");
                    //#info%id%pw%name%job%age%tel
                    Intent intent = new Intent(mContext, activity_Info_modify.class);
                    intent.putExtra("id", tokens[1]);
                    intent.putExtra("pw", tokens[2]);
                    intent.putExtra("name", tokens[3]);
                    intent.putExtra("job", tokens[4]);
                    intent.putExtra("age", tokens[5]);
                    intent.putExtra("tel", tokens[6]);

                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(mContext, "fail : " + result, Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

}
