package parsing;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonValue 
{
	private JSONArray jArray;
	private JSONObject json_data;
	private String columnName;
	
	public JsonValue(String output, String columnName)
	{
		this.columnName=columnName;
		try 
		{
			jArray = new JSONArray(output);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public int getIntValue()
	{
		int value=0;
		try {
			json_data = jArray.getJSONObject(0);
			value= Integer.valueOf(json_data.getInt(columnName));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return value;
	}

}
