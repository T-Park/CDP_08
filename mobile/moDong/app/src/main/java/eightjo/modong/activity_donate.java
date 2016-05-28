package eightjo.modong;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class activity_donate extends Activity {

    ListView myDonationList;
    donationListAdater donationListAdater;
    ArrayList<dItem> arrayList;
    TextView textView_choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_donate);

        myDonationList = (ListView)findViewById(R.id.listView_donation);
        textView_choice = (TextView)findViewById(R.id.textView_choice);
        arrayList = new ArrayList<dItem>();
        arrayList.add(new dItem("개발자1"));
        arrayList.add(new dItem("개발자2"));
        arrayList.add(new dItem("개발자3"));
        arrayList.add(new dItem("개발자4"));
        arrayList.add(new dItem("개발자5"));

        donationListAdater = new donationListAdater(this, R.layout.ditem, arrayList);
        myDonationList.setAdapter(donationListAdater);

        myDonationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                textView_choice.setText("'" + arrayList.get(position).getDname() + "'에" );
            }
        });

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

    public void onClick_cancel(View v)
    {
        finish();
    }

}
