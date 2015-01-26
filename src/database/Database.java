package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.os.AsyncTask;

public class Database extends AsyncTask<String, String, String>
{

	List<NameValuePair> params = new ArrayList<NameValuePair>();
	LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
	 JSONParser jsonParser = new JSONParser();//**********************//
	 String url="http://192.168.1.10:8080/android_connect/";
	 String response;
	 public AsyncResponse delegate;
	 private boolean postexec;
	 String request;
	public Database (LinkedHashMap<String,String> map, String url) 
	{
		this.map=map;
		this.url+=url;
		postexec=false;
		this.request=url;
	}
	
	public Database ( String url, AsyncResponse delegate) 
	{
		this.url+=url;
		this.delegate=delegate;
		postexec=true;
		this.request=url;
	}
	
	public Database ( LinkedHashMap<String,String> map,String url, AsyncResponse delegate) 
	{
		this.map=map;
		this.url+=url;
		this.delegate=delegate;
		postexec=true;
		this.request=url;
	}

	public void buildParameters()
	{
		Iterator it = map.entrySet().iterator();
	    while (it.hasNext())
	    {
	        Map.Entry pairs = (Map.Entry)it.next();
	        //add to parameters
	        params.add(new BasicNameValuePair((String)pairs.getKey(), (String) pairs.getValue()));
	        System.out.println("key:" +pairs.getKey() + " = " + pairs.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	protected String doInBackground(String... arg0) 
	{
		//System.out.println("0");
		buildParameters();
		//System.out.println("asdasd");
        // getting JSON Object
        // Note that create product url accepts POST method
         response = jsonParser.makeHttpRequest(url,
                "POST", params);
//        int success = Integer.parseInt(response);
//
//		if (success == 1) {
//		    // successfully created product			
//			//add person to list ////////////
//				System.out.println("success!");
//		} else {
//		    // failed to create product
//			System.out.println("fail");
//		}
         //parseNames();
         response+="*"+request;
        // System.out.println(response);
		return response;
	}

	   protected void onPostExecute(String result) 
	   {
		   
		   if(postexec)
		   {
		      delegate.processFinish(result);
		   }
	   }
		   
	
	
	

}
