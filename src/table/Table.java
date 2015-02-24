package table;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.tabulate.InventoryActivity;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Table 
{
	private JSONArray jArray;
	private TableLayout tl;
	private Activity activity;
	private int flag;
	private String[] colNames;
	private String[] rowNames;
	private int rowNum;
	private Row row;
	private HashMap<Integer,Row> rows;


	public Table(String output, TableLayout tl,Activity activity, String[] colNames, String[] rowNames)
	{
		try 
		{
			jArray = new JSONArray(output);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.tl=tl;
		this.activity=activity;
		tl.removeAllViewsInLayout();
		flag=1;
		this.colNames=colNames;
		this.rowNames=rowNames;
		rows=new HashMap<Integer,Row>();
		rowNum=0;
	}
	
	public void buildTable()
	{
	    for(int i=-1;i<jArray.length();i++)
        {

            TableRow tr=new TableRow(activity);
            tr.setLayoutParams(new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT));
            tr.setOnClickListener(new InventoryActivity.RowListener(activity));
            // this will be executed once
            if(flag==1)
            {
            	//set column titles for table
            	for(String s: colNames)
            	{
            		addColumn(tr,s);
            	}
            	//add the to view
                tl.addView(tr);

                final View vline = new View(activity);
                vline.setLayoutParams(new       
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
                vline.setBackgroundColor(Color.BLUE);
                tl.addView(vline); // add line below heading
                flag=0;
            }
            else 
            {
         	   // populate rows
                JSONObject json_data;
				try 
				{
					json_data = jArray.getJSONObject(i);
					row=new Row();
					for(String s: rowNames)
	            	{
						addRow(s,tr,json_data);

	            	}
				rows.put(rowNum, row);
                tl.addView(tr);
                rowNum++;
                //draw lines between rows
                final View vline1 = new View(activity);
                vline1.setLayoutParams(new                
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                vline1.setBackgroundColor(Color.DKGRAY);
                tl.addView(vline1);  // add line below each row   
                
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }
        }
	}
	
	public void addRow(String s,TableRow tr,JSONObject json_data)
	{

		if(s.equals("type"))
		{
            //type 
            TextView b3=new TextView(activity);
            b3.setPadding(10, 0, 0, 0);
            int type=-1;
			try {
				type = json_data.getInt("type");
			} catch (JSONException e) {
				e.printStackTrace();
			}
            String str3="";
            if(type==1)
            {
         	   str3= "Keg";
         	 //  b3.setTextColor(Color.CYAN);
            }
            else
            {
         	   str3="Bottle";
         	   //b3.setTextColor(Color.DKGRAY);
            }
            b3.setText(str3);
            b3.setTextSize(15);
            tr.addView(b3);
		}
		else
			getRow(tr,json_data,s);
	}

	
	public void addColumn(TableRow tr, String colName)
	{
		  TextView tv=new TextView(activity);
		  tv.setPadding(10, 0, 0, 0);
		  tv.setText(colName);
		  tv.setTextColor(Color.BLUE);
		  tv.setTextSize(15);
	      tr.addView(tv);
	}

	public void getRow(TableRow tr, JSONObject json_data, String colName)
	{
		//get rows from the json array to populate table
		TextView tv=new TextView(activity);
	    String str;
		try {
			str = String.valueOf(json_data.getString(colName));

	    tv.setText(str);
	    //check if keg or bottle
	   // checkType(tv,json_data);
	    tv.setTextSize(15);
	    tr.setId(rowNum);
	    if(colName.equals("name"))
	    	row.setName(str);
	    if(colName.equals("id"))
	    	row.setProductId(str);
	    else
	    	tr.addView(tv);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	public HashMap<Integer, Row> getRows() {
		return rows;
	}

	public void setRows(HashMap<Integer, Row> rows) {
		this.rows = rows;
	}

	
}
