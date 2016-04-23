package eightjo.modong;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by apple on 16. 3. 30..
 */
public class forBuyList extends LinearLayout{

    TextView textView_date;
    TextView textView_name;
    TextView textView_amount;

    public forBuyList(Context context) {
        super(context);
    }

    public forBuyList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(Context context)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_item, this);

        textView_date = (TextView) findViewById(R.id.textView_date);
        textView_name = (TextView) findViewById(R.id.textView_storeName);
        textView_amount = (TextView) findViewById(R.id.textView_amount);

    }

}
