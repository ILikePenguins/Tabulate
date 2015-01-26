package parsing;

public class Parse 
{
	private String response;
	
	public Parse(String response)
	{
		this.response=URL(response);
	}
	
	public Parse()
	{
		
	}
	
	public void setString(String response)
	{
		this.response=URL(response);
	}
	public String[] Names()
	{
		if(response.contains("name")) //check for empty strings
		{
			response=response.replaceAll("\\bname\\b", "");
			crap();
			response=response.substring(1,response.length()-1);
			String tokens[]=response.split(",");
			return tokens;
		}
		return null;
		
		
	}
	
	public String id()
	{
		response=response.replaceAll("\"", "");
		return response;
	}
	
	public String[] NameDate()
	{
		response=response.replaceAll("\\bname\\b", "");
		response=response.replaceAll("\\bdate\\b", " ");
		response=response.replaceAll("\",\"", "");
		crap();
		
		response=response.substring(1,response.length()-1);
		String tokens[]=response.split(",");
		return tokens;
	}
	
	public void crap()
	{
		response= response.replaceAll("[{:}]", "");
		response=response.replaceAll("\"", "");
	}
	
	public String URL(String response)
	{
		//get rid of added url
		int spaceIndex = response.indexOf("*");
		if (spaceIndex != -1)
		{
			response = response.substring(0, spaceIndex);
		}
		return response;
	}
	
	
	public String beers()
	{
		if(response.contains("name")) //check for empty strings
		{
			response=response.replaceAll("\\bname\":\\b", "");
			//get rid of crap from response
			response= response.replaceAll("[{]", "");
			response=response.replaceAll("\",", "  ");
			response=response.replaceAll("\"", "");
			response=response.replaceAll("[}]", "");
			
			response=response.substring(1,response.length()-1);
			return response;
		}
		System.out.println("doesnt contain anything");
		return null;
	}
	
	public String sales ()
	{
		response=response.replaceAll("\\bpints\\b", "");
		response=response.replaceAll("\\bbottles\\b", "");
		response= response.replaceAll("[{:}]", "");
		response=response.replaceAll("\"", "");
		return response;
	}
	
	public int nthOccurrence(String str, char c, int n) 
	{
	    int pos = str.indexOf(c, 0);
	    while (n-- > 0 && pos != -1)
	        pos = str.indexOf(c, pos+1);
	    return pos;
	}
	
	
	
}
