package eightjo.modong;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
//forAppInfo : appInfo : 1. user_id 2. user_pw 3. name 4. job 5. age 6. phone 7. lock_flag 8. lock_pw 9. point 10. type 11. login_flag 12. group_flag 13. bacode
public class tab_setting extends Activity {

    SQLiteDatabase db;

    private String temp_user_id;
    private String temp_user_pw;

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

        /*
        loadDB();
        Cursor c = db.rawQuery("SELECT * FROM appInfo where login_flag =1;", null);
        c.moveToPosition(c.getCount() - 1);
        temp_user_id =  c.getString(c.getColumnIndex("user_id"));
        temp_user_pw =  c.getString(c.getColumnIndex("user_pw"));
        */
    }

    /*
    public void loadLatestDB()
    {
        loadDB();
        Cursor c = db.rawQuery("SELECT * FROM appInfo where login_flag =1;", null);
        c.moveToPosition(c.getCount() - 1);
        temp_user_id =  c.getString(c.getColumnIndex("user_id"));
        temp_user_pw =  c.getString(c.getColumnIndex("user_pw"));
    }
*/
    @Override
    public  void onResume()
    {
        super.onResume();
        //loadLatestDB();
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
                //loadLatestDB();
                dialog.setTitle("사용자 인증");
                textView_dialog1.setText("ID :" + temp_user_id);
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
        Cursor c = db.rawQuery("SELECT * FROM appInfo where login_flag =1;", null);

        //startManagingCursor(c);
        if(c.getCount() > 0)
        {
            c.moveToPosition(c.getCount()-1);//int a =  c.getPosition();
            String result =  c.getString(c.getColumnIndex("name"));
            Toast.makeText(this,result + "입니다" , Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"데이터가없숑" , Toast.LENGTH_SHORT).show();
        }


    }

    public void button_Lock(View v)
    {

    }

    public void button_Logout(View v)
    {
        //db.execSQL("DROP TABLE appInfo;");//DROP TABLE tempInfo2
        //Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
        finish();
    }

    //forAppInfo : appInfo : 1. user_id 2. user_pw 3. name 4. job 5. age 6. phone 7. lock_flag 8. lock_pw 9. point 10. type 11. login_flag 12. group_flag 13. bacode
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
                " lock_pw TEXT,"+
                " point INTEGER," +
                " type TEXT,"+
                " login_flag INTEGER," +
                " group_flag INTEGER," +
                " bacode TEXT"+
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

    private Button.OnClickListener saveOnClickListener  //MODIFY
            = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            String value= editText_dialog_password.getText().toString();


            //password가 일치한다면
            if(value.equals(temp_user_pw))
            {
                Intent intent = new Intent(mContext, activity_Info_modify.class);
                startActivity(intent);
                dismissDialog(0);
            }
            else
            {
                Toast.makeText(mContext, value +" : "+ temp_user_pw + " 비밀번호가 틀렸어요!", Toast.LENGTH_SHORT).show();
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
            if(value.equals(temp_user_pw))
            {
                showDialog(1);
                dismissDialog(3);
            }
            else
            {
                Toast.makeText(mContext, value +" : "+ temp_user_pw + " 비밀번호가 틀렸어요!", Toast.LENGTH_SHORT).show();
            }
        }

    };

    private Button.OnClickListener saveOnClickListenerForLock2//비밀번호 설정1
            = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            tempPw = editText_dialog_password_before.getText().toString();
            if(tempPw.equals(""))
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
            if(value.equals(tempPw))
            {
                //db 처리
                /*
                db.execSQL("UPDATE appInfo SET " +
                        "lock_pw='"+ value + "',"+
                        "lock_flag="+ 1 + "" +
                        " WHERE login_flag =1;");
                Toast.makeText(mContext, "일치합니다. 비밀번호 : "+ value + " == "+ tempPw, Toast.LENGTH_SHORT).show();
 */               //loadLatestDB();
            }
            else
            {
                Toast.makeText(mContext, "이전의 비밀번호와 일치하지 않습니다."+ value + " != "+ tempPw, Toast.LENGTH_SHORT).show();
            }

            dismissDialog(2);
        }

    };

    public void onClick_goNext(View v)
    {
        Intent intent =new Intent(this, activity_GroupStep1.class);
        startActivity(intent);
    }

}
