package eightjo.modong;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_lock extends Activity {

    EditText editText_lock_pw;
    Button button_lock_clear;

    String lock_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_lock);

        editText_lock_pw = (EditText)findViewById(R.id.editText_lock_password);
        button_lock_clear = (Button)findViewById(R.id.button_lock_clear);

        //intent로 pw받기
        lock_pw = this.getIntent().getStringExtra("lock_pw");
        Toast.makeText(this,  lock_pw, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_lock, menu);
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



    public void onClickClear(View v)
    {//일치여부 확인
        String input = editText_lock_pw.getText().toString();
        if(input.equals(lock_pw))
        {
            //다음 activity로
            Intent intent = new Intent(this, tab_main.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(this, lock_pw + " : " + input  + " 비밀번호가 틀렸어요!", Toast.LENGTH_SHORT).show();
        }
    }
}
