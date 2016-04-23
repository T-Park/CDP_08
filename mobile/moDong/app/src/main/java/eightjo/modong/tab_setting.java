package eightjo.modong;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class tab_setting extends Activity {

    SQLiteDatabase db;
    Button button_modify, button_lock;
    Button button_save, button_cancel;
    EditText editText_dialog_password, editText_dialog_password_before;
    TextView textView_dialog1, textView_dialog2;
    Context mContext;
    String tempPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_setting);

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

        loadDB();
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
                dialog.setTitle("사용자 인증");
                button_save.setOnClickListener(saveOnClickListener);
                button_cancel.setOnClickListener(cancelOnClickListener);

                break;
            case 1 :
                dialog.setTitle("비밀번호 입력");
                textView_dialog1.setText("잠금을 위한");
                textView_dialog2.setText("비밀번호를 입력해 주세요.");

                editText_dialog_password_before = (EditText)dialog.findViewById(R.id.editText_dialog_password);

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
                dialog.setTitle("사용자 인증");
                button_save.setOnClickListener(saveOnClickListenerForLock);
                button_cancel.setOnClickListener(cancelOnClickListener3);
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

    private Button.OnClickListener saveOnClickListener
            = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            String value= editText_dialog_password.getText().toString();

            //password가 일치한다면
                Intent intent = new Intent(mContext, activity_Info_modify.class);
                startActivity(intent);
                dismissDialog(0);

        }

    };

    private Button.OnClickListener saveOnClickListenerForLock
            = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            String value= editText_dialog_password.getText().toString();

            //password가 일치한다면
            showDialog(1);
            dismissDialog(3);
        }

    };

    private Button.OnClickListener saveOnClickListenerForLock2
            = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            tempPw = editText_dialog_password_before.getText().toString();
            showDialog(2);
            dismissDialog(1);
        }

    };

    private Button.OnClickListener saveOnClickListenerForLock3
            = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            String value= editText_dialog_password.getText().toString();
            if(value.equals(tempPw))
            {
                Toast.makeText(mContext, "일치합니다."+ value + " == "+ tempPw, Toast.LENGTH_SHORT).show();
                //db 처리
            }
            else
            {
                Toast.makeText(mContext, "이전의 비밀번호와 일치하지 않습니다."+ value + " != "+ tempPw, Toast.LENGTH_SHORT).show();
            }

            dismissDialog(2);
        }

    };

}
