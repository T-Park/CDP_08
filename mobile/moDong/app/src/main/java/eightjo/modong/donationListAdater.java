package eightjo.modong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by apple on 16. 5. 21..
 */
public class donationListAdater extends BaseAdapter {
    Context context;
    LayoutInflater inflater;

    ArrayList<dItem> arrayList;
    TextView textView_dname;
    private int layout;


    public donationListAdater(Context context, int layout, ArrayList<dItem> arrayList) {
        this.context = context;
        this.layout = layout;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
    }

    @Override
    public int getCount()
    {
        return  arrayList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final  int finalPosition = position;
        if(convertView == null)
        {
            convertView = inflater.inflate(layout, parent, false);
        }

        textView_dname = (TextView)convertView.findViewById(R.id.textView_dname);


        textView_dname.setText(arrayList.get(finalPosition).getDname());

        return convertView;
    }



}
