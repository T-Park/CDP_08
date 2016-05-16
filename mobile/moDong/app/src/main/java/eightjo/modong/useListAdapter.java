package eightjo.modong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by apple on 16. 5. 16..
 */
public class useListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;

    ArrayList<Item> arrayList;
    TextView textView_where, textView_when, textView_point;
    private int layout;


    public useListAdapter(Context context, int layout, ArrayList<Item> arrayList) {
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

        textView_when = (TextView)convertView.findViewById(R.id.textView_date);
        textView_where = (TextView)convertView.findViewById(R.id.textView_storeName);
        textView_point = (TextView)convertView.findViewById(R.id.textView_point);

        textView_when.setText(arrayList.get(finalPosition).getWhen());
        textView_where.setText(arrayList.get(finalPosition).getWhere());
        textView_point.setText(arrayList.get(finalPosition).getPoint());

        return convertView;
    }



}
