package com.AtmCount;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import java.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Path("/atmcountservice")
public class AtmCountService
{
 	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
 	  @Path("/{App_Code}")
 	  public Response GetAtmCount(@PathParam("App_Code") String App_Code) throws JSONException, NamingException, SQLException, IOException
	  {
 		  
 	 	Properties prop = new Properties();
 	 	InputStream input = null; 
 	  	String dbuser = null;
 	 	String dbpassword = null;
 	 	String url=null;
 	 	String driver=null;
 	 	int interval=0;
 		
 	 	try 
  		{ 			
  			input = getClass().getClassLoader().getResourceAsStream("config.properties");

  			// load a properties file
  			prop.load(input);

  			// get the property value and print it out
  			dbuser=prop.getProperty("dbuser");
  			dbpassword=prop.getProperty("dbpassword");
  			url=prop.getProperty("url");
  			driver=prop.getProperty("driver");
  			interval=Integer.parseInt(prop.getProperty("interval"));
  			System.out.println(dbuser);
  			System.out.println(dbpassword);
  			System.out.println(url);
  			System.out.println(driver);
  			System.out.println(interval);
  		} 
  		catch (IOException ex) 
  		{
  			ex.printStackTrace();
  		} 
  		finally 
  		{
  			if (input != null) 
  			{
  				try 
  				{
  					input.close();
  				} 
  				catch (IOException e) 
  				{
  					e.printStackTrace();
  				}
  			}
  		}
 		
		try 
	 	{
	 		Class.forName(driver);
	 	} 
	 	catch (ClassNotFoundException e) 
	 	{
	 		System.out.println("Where is your MariaDB JDBC Driver?");
	 		e.printStackTrace();
	 		System.exit(1);
	 	}

	 	System.out.println("MariaDB JDBC Driver Registered!");
	 		
	 	Connection conn = null;

	 	try 
	 	{
	 		conn = DriverManager.getConnection(url,dbuser,dbpassword);

	 	} 
	 	catch (SQLException e) 
	 	{
	 		System.out.println("Connection Failed! Check output console");
	 		e.printStackTrace();
	 		System.exit(1);
	 	}

	 	if (conn != null) 
	 	{
	 		System.out.println("You made it, take control your database now!");
	 	} 
	 	else 
	 	{
	 		System.out.println("Failed to make connection!");
	 		System.exit(1);
	 	}
   	    
   	    Statement Qstmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    	    
        List<AtmCount> atmcountList = new ArrayList <AtmCount>();
  
        JSONArray jsonlist = new JSONArray();
        
        GregorianCalendar startdate = new GregorianCalendar();
        
        int startday = startdate.get(Calendar.DAY_OF_MONTH);
        int startmonth = startdate.get(Calendar.MONTH);
        int startyear = startdate.get(Calendar.YEAR);
   
        int startsecond = startdate.get(Calendar.SECOND);
        int startminute = startdate.get(Calendar.MINUTE);
        int starthour = startdate.get(Calendar.HOUR);
   
        System.out.println("Start date is  "+startday+"/"+(startmonth+1)+"/"+startyear);
        System.out.println("Start time is  "+starthour+" : "+startminute+" : "+startsecond);
 
        try
        {
        	   int id=0;
        	   Timestamp pl_reportTime_tmp=null;
        	   String pl_reportTime=null;
        	   String pl_ApplicationId=null;
        	   String pl_BankId=null;
        	   Timestamp pl_StartTime_tmp=null;
        	   Timestamp pl_EndTime_tmp=null;
        	   String pl_StartTime=null;
        	   String pl_EndTime=null;
         	   String pl_NodeId=null;
         	   Timestamp pl_prev_reportTime=null;
         	   Timestamp pl_curr_reportTime=null;
         	   
         	   int pl_SuccessTransaction=0;
         	   int pl_TimeOutTransaction=0;
         	   int pl_BusinessFailure=0;
         	   int pl_TotalTransaction=0;
        	   int totalsuccess=0;
        	   int totaltimeout=0;
        	   int totalrequest=0;
        	   int totalbusfail=0;
        	   int rec_cnt=0;
         	   
         	   String in_app_code = '"' + App_Code + '"';
       	         	   
        	   String AtmCountQry = "select a.reportTime,a.applicationid,a.bankid,a.startTime,a.endTime,a.successTransaction,a.timeoutTransaction,a.businessValidationFailure,a.totalTransaction, a.nodeId  " + 
        	                        "from (select * from atmcountstat b where b.reportTime > SUBDATE(now(), INTERVAL 12 HOUR) and " +  
        	                        "b.applicationid=" + in_app_code + " ORDER BY b.reportTime DESC LIMIT 16) a ORDER BY a.reportTime ASC"; 

        	   ResultSet AtmCountDat = Qstmt.executeQuery(AtmCountQry);
        	   
        	   while (AtmCountDat.next())
        	   {
        		   pl_reportTime_tmp=AtmCountDat.getTimestamp("a.reportTime");
        		   pl_curr_reportTime=pl_reportTime_tmp;
        		   
        		   if (rec_cnt == 0)
        		   {
        		      pl_prev_reportTime=pl_reportTime_tmp;
        		   }
        		   
        		   if (pl_curr_reportTime.compareTo(pl_prev_reportTime) != 0)
        		   {
        			   pl_SuccessTransaction=totalsuccess;
        			   pl_TimeOutTransaction=totaltimeout;
        			   pl_BusinessFailure=totalbusfail;
        			   pl_TotalTransaction=totalrequest;
             		   SimpleDateFormat df1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
             		   pl_reportTime = df1.format(pl_reportTime_tmp);
        			   id++;
                 	   AtmCount AtmCountDao = new AtmCount(id,pl_reportTime,pl_ApplicationId,pl_BankId,pl_StartTime,pl_EndTime,pl_SuccessTransaction,pl_TimeOutTransaction,pl_BusinessFailure,pl_TotalTransaction,pl_NodeId);
                 	   atmcountList.add(AtmCountDao);
                 	   pl_prev_reportTime=pl_reportTime_tmp;
                 	   totalsuccess = 0;
                 	   totaltimeout = 0;
                 	   totalbusfail = 0;
                 	   totalrequest = 0;
        		   }
        		   
        		   totalsuccess+=AtmCountDat.getInt("a.successTransaction");
         		   totaltimeout+=AtmCountDat.getInt("a.timeoutTransaction");
         		   totalbusfail+=AtmCountDat.getInt("a.businessValidationFailure");
         		   totalrequest+=AtmCountDat.getInt("a.totalTransaction");
         		   pl_ApplicationId=AtmCountDat.getString("a.applicationid");               	          	  
        		   pl_BankId=AtmCountDat.getString("a.bankid");
        		   pl_StartTime_tmp=AtmCountDat.getTimestamp("a.startTime");
        		   pl_EndTime_tmp=AtmCountDat.getTimestamp("a.endTime");
        		   SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
         		   pl_StartTime = df2.format(pl_StartTime_tmp);
         		   SimpleDateFormat df3 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
         		   pl_EndTime = df3.format(pl_EndTime_tmp);
        		   rec_cnt++;
        	   }
        	   pl_SuccessTransaction=totalsuccess;
			   pl_TimeOutTransaction=totaltimeout;
			   pl_BusinessFailure=totalbusfail;
			   pl_TotalTransaction=totalrequest;
			   id++;
         	   AtmCount AtmCountDao = new AtmCount(id,pl_reportTime,pl_ApplicationId,pl_BankId,pl_StartTime,pl_EndTime,pl_SuccessTransaction,pl_TimeOutTransaction,pl_BusinessFailure,pl_TotalTransaction,pl_NodeId);
         	   atmcountList.add(AtmCountDao);
        }
        catch (SQLException e) 
        {
             System.err.println(e.getMessage());
        } 
             
        for (int i = 0; i < atmcountList.size(); i++) 
        {
            LinkedHashMap<String, Object> lHashMap = new LinkedHashMap<String,Object>();
            AtmCount AtmCounttmp=atmcountList.get(i);

            lHashMap.put("reportTime", AtmCounttmp.getreportTime());
            lHashMap.put("applicationId", AtmCounttmp.getApplicationId());
            lHashMap.put("bankId", AtmCounttmp.getBankId());
            lHashMap.put("startTime", AtmCounttmp.getstartTime());
            lHashMap.put("endTime", AtmCounttmp.getendTime());
            lHashMap.put("successTransaction", AtmCounttmp.getsuccessTransaction());
            lHashMap.put("timeOutTransaction", AtmCounttmp.gettimeoutTransaction());
            lHashMap.put("businessValidationFailure", AtmCounttmp.getbusinessFailure());
            lHashMap.put("totalTransaction", AtmCounttmp.gettotalTransaction());
            JSONObject jsonObject = new JSONObject(lHashMap);
            jsonlist.put(jsonObject);
        }
        
        String result = jsonlist.toString();
        
        GregorianCalendar enddate = new GregorianCalendar();
        
        int endday = enddate.get(Calendar.DAY_OF_MONTH);
        int endmonth = enddate.get(Calendar.MONTH);
        int endyear = enddate.get(Calendar.YEAR);
   
        int endsecond = enddate.get(Calendar.SECOND);
        int endminute = enddate.get(Calendar.MINUTE);
        int endhour = enddate.get(Calendar.HOUR);
   
        System.out.println("End date is  "+endday+"/"+(endmonth+1)+"/"+endyear);
        System.out.println("End time is  "+endhour+" : "+endminute+" : "+endsecond);
        return Response.ok(result, MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
	  }
}