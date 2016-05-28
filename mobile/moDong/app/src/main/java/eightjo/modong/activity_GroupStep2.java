package eightjo.modong;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class activity_GroupStep2 extends Activity {

    String groupName;
    EditText editText_m1, editText_m2, editText_m3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__group_step2);

        Intent intent = getIntent();
        groupName = intent.getStringExtra("groupName");
        TextView textView_groupNameView = (TextView)findViewById(R.id.textView_groupNameView);
        textView_groupNameView.setText(groupName);

        editText_m1 = (EditText)findViewById(R.id.editText_member1);
        editText_m2 = (EditText)findViewById(R.id.editText_member2);
        editText_m3 = (EditText)findViewById(R.id.editText_member3);
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

       //서버로 메세지!

        Toast.makeText(this, "그룹바코드 구현해야해!", Toast.LENGTH_SHORT).show();
        finish();
    }

}
