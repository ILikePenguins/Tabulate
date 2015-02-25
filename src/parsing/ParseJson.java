package parsing;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.Customers;
import android.widget.TextView;

public class ParseJson 
{
	private JSONArray jArray;
	private JSONObject json_data;
	private String[] columnNames;
	private HashMap<Integer,Customers> customer_id;
	private TextView tv;
	private LinkedHashMap<String,Boolean>  customers;
	private int count;
public int getCount() {
		return count;
	}
public LinkedHashMap<String, Boolean> getCustomers() {
		return customers;
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
	public ParseJson(String output, String[] columnNames)
	{
		this.columnNames=columnNames;
		customers=new LinkedHashMap<String,Boolean>();
		try 
		{
			jArray = new JSONArray(output);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		customer_id=new HashMap<Integer,Customers>();
	}
	public void AddToAdapter()
	{	count=0;
		String str="";
		 for(int i=0;i<jArray.length();i++)
	        {
			 str="";
			 String id="";
			 boolean paid=false;
			 try 
			 {
				 //retrieve values for each column
				 Customers customer=new Customers();
				json_data = jArray.getJSONObject(i);
				for(String s: columnNames)
            	{
					if(s.equals("id"))
					{
						id=String.valueOf(json_data.getString(s));
						customer.setCustomer_id(id);
					}
					else if(s.equals("paid"))
					{
						if(!json_data.isNull(s))
						{
							int p= Integer.valueOf(json_data.getInt(s));
							if(p==1)
								paid=true;
						}
						
						//System.out.println("paid "+ p);
					}
					else //name
						str= String.valueOf(json_data.getString(s));
					
            	}
				customer.setName(str);
				//add position and id to hashmap
				customer_id.put(count, customer);
				//System.out.println(str+" "+count+ " "+id );
				//add name to adapter
				//adapter.add(str);
				customers.put(str,paid);
				count++;
				//System.out.println(str);
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
	public HashMap<Integer, Customers> getCustomer_id() 
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
