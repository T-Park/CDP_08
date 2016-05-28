package eightjo.modong;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class activity_GroupStep1 extends Activity {

    EditText editText_groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__group_step1);

        editText_groupName = (EditText)findViewById(R.id.editText_groupName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__group_step1, menu);
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
        String groupName = editText_groupName.getText().toString();

        if(groupName.equals(""))
        {
            Toast.makeText(this, "그룹이름을 적어주세요!", Toast.LENGTH_SHORT).show();
            return;
        }


        //groupName 선 검사

       //이름이 겹치지 않는다면
        Intent intent =new Intent(this, activity_GroupStep2.class);
        intent.putExtra("groupName", groupName);
        startActivity(intent);
        finish();
    }

}
