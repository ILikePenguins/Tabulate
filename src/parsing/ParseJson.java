package parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ParseJson 
{
	private JSONArray jArray;
	private JSONObject json_data;
	private ArrayAdapter<String> adapter;
	private String[] columnNames;
	private HashMap<Integer,String> customer_id;
	private TextView tv;
	private ArrayList<String> names;
	
	public ArrayList<String> getNames() {
		return names;
	}
	public ParseJson(String output, TextView tv,String[] columnNames)
	{
		this.tv=tv;
		this.columnNames=columnNames;
		try 
		{
			jArray = new JSONArray(output);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public ParseJson(String output, ArrayAdapter<String> adapter,String[] columnNames)
	{
		this.adapter=adapter;
		this.columnNames=columnNames;
		names= new ArrayList<String>();
		try 
		{
			jArray = new JSONArray(output);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		customer_id=new HashMap<Integer,String>();
	}
	public void AddToAdapter()
	{	int count=0;
		String str="";
		 for(int i=0;i<jArray.length();i++)
	        {
			 str="";
			 String id="";
			 try 
			 {
				 //retrieve values for each column
				json_data = jArray.getJSONObject(i);
				for(String s: columnNames)
            	{
					if(s.equals("id"))
						id=String.valueOf(json_data.getString(s));
					else
						str= String.valueOf(json_data.getString(s));
					
            	}
				//add position and id to hashmap
				customer_id.put(count, id);
				//add name to adapter
				//adapter.add(str);
				names.add(str);
				count++;
				System.out.println(str);
			 } catch (JSONException e) 
			 {
				e.printStackTrace();
			 }
	        }
//		    for (Entry<Integer, String> entry : customer_id.entrySet()) 
//		    {
//			    Integer key = entry.getKey();
//			    String value = entry.getValue();
//			    System.out.println("key: "+key+"val: "+value);
//			}
	}
	public HashMap<Integer, String> getCustomer_id() 
	{
		return customer_id;
	}
	
	public void changeTextView()
	{
		try 
		{
			json_data = jArray.getJSONObject(0);
			tv.setText(String.valueOf(json_data.getString(columnNames[0])));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


}
