package eightjo.modong;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class tab_main extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);

        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;


        intent = new Intent().setClass(this, tab_cardViewer.class);

        spec = tabHost.newTabSpec("home").setIndicator("메인").setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent().setClass(this, tab_useList.class);

        spec = tabHost.newTabSpec("buyList").setIndicator("나의 사용내역").setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent().setClass(this, tab_donationList.class);

        spec = tabHost.newTabSpec("donationList").setIndicator("나의 기부내역").setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent().setClass(this, tab_setting.class);

        spec = tabHost.newTabSpec("settings").setIndicator("설정하기", getResources().getDrawable(R.drawable.temp1)).setContent(intent);
        tabHost.addTab(spec);


        //크기조절,,?
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++)
        {
            tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (70*(getResources( ).getDisplayMetrics( ).density));
            //tabHost.getTabWidget().getChildAt(i).getLayoutParams().width = (int) (60*(getResources( ).getDisplayMetrics( ).density));;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__tab_main, menu);
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
}
