package adapter;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tabulate.R;
public class NameAdapter extends BaseAdapter
{
	private ArrayList<String>  names;
	private Activity activity;
	static int ID=0;
	public NameAdapter (ArrayList<String> names, Activity activity)
	{
		this.names=names;
		this.activity=activity;
	}
	public int getCount() {
		return names.size();
	}

	public Object getItem(int index) {
		return names.get(index);
	}

	public long getItemId(int position) {
		return position;
	}
	
	public void addToList()
	{
		for(String name: names)
		{
			TextView nameTextView= new TextView(activity);
            nameTextView.setText(name);
		}
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		{
			 if (convertView == null) 
             {
                     LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                     convertView = inflater.inflate(R.layout.list_names, parent, false);
             }
			 TextView nameTextView = (TextView) convertView.findViewById(R.id.name_view);
			 nameTextView.setId(ID);
			 nameTextView.setText(names.get(position));
			 //add strike though text if they paid
			 if(nameTextView.getText().toString().contains("t"))
				 nameTextView.setPaintFlags(nameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            ID++;
    }
		return convertView;
	}



}
