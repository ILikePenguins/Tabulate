package adapter;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tabulate.R;
public class NameAdapter extends BaseAdapter
{
	private ArrayList<String>  names;
	private HashMap<String,Integer> text_ids;
	private Activity activity;
	static int ID=0;
	public NameAdapter (ArrayList<String> names, Activity activity)
	{
		this.names=names;
		this.activity=activity;
		text_ids= new HashMap<String,Integer>();
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
                     System.out.println("1111");
             }
			//View.generateViewId();
           TextView nameTextView = (TextView) convertView.findViewById(R.id.name_view);
			// TextView nameTextView= new TextView(activity);
           nameTextView.setId(ID);
            nameTextView.setText(names.get(position));
			 
            System.out.println("name "+ names.get(position));
            //set the id for the textview
            //nameTextView.setId(View.generateViewId());
            System.out.println("id "+ nameTextView.getId());
            //add name and id to hashmap
            text_ids.put(names.get(position), convertView.getId());
            
            ID++;
    }
		return convertView;
	}
	public HashMap<String, Integer> getText_ids() {
		return text_ids;
	}


}
