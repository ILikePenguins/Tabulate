package adapter;


import java.util.HashMap;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tabulate.R;
public class NameAdapter extends BaseAdapter
{
	private HashMap<String,Boolean>  customers;
	private String[] keys;
	private  int id=0;
	public NameAdapter (HashMap<String,Boolean> customers)
	{
		this.customers=customers;
		keys = customers.keySet().toArray(new String[customers.size()]);
	}
	public int getCount() {
		return customers.size();
	}

	public Object getItem(int index) {
		return customers.get(keys[index]);
	}

	public long getItemId(int position) {
		return position;
	}
	

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		{
			 //if (convertView == null) 
             //{
                     LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                     convertView = inflater.inflate(R.layout.list_names, parent, false);
             //}
			 TextView nameTextView = (TextView) convertView.findViewById(R.id.name_view);
			 
			 nameTextView.setText(keys[position]);
			 //set id according to position added in list
			 nameTextView.setId(id);
			 System.out.println(keys[position]);
			 if(customers.get(keys[position]))
				 nameTextView.setPaintFlags(nameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			 id++;
    }
		return convertView;
	}



}
