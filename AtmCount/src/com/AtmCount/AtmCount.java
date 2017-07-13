package com.AtmCount;

import java.io.Serializable;

public class AtmCount implements Serializable
{
	   private static final long serialVersionUID = 1L;
	   private int id;
	   String reportTime;
	   String ApplicationId;
	   String BankId;
	   String startTime;
	   String endTime;
	   String nodeId;
	   
	   int successTransaction;
	   int timeoutTransaction;
	   int businessFailure;
	   int totalTransaction;
	   
	   public AtmCount()
	   {
		   super();
	   }
	    
	   public AtmCount(int id,String reportTime,String ApplicationId,String BankId,String startTime,String endTime,int successTransaction,int timeoutTransaction,int businessFailure,int totalTransaction,String nodeId)
	   {
		  super();
		  this.id = id;
	
	      this.ApplicationId = ApplicationId;
	      this.BankId = BankId;
	      this.reportTime=reportTime;
	      this.startTime=startTime;
	      this.endTime=endTime;
	      this.successTransaction=successTransaction;
	      this.timeoutTransaction=timeoutTransaction;
	      this.businessFailure=businessFailure;
	      this.totalTransaction=totalTransaction;
	      this.nodeId=nodeId;
       }
	   public int getId() 
	   { 
		      return id; 
	   }  
	
	   public void setId(int id)
	   { 
		      this.id = id; 
	   } 
		   
	   public String getApplicationId()
	   { 
	      return ApplicationId; 
	   } 
	   
	   public void setApplicationId(String ApplicationId) 
	   { 
	      this.ApplicationId = ApplicationId; 
	   } 
	   
	   public String getreportTime()
	   { 
	      return reportTime; 
	   } 
	   
	   public void setreportTime(String reportTime) 
	   { 
	      this.reportTime = reportTime; 
	   } 
	   
	   public String getBankId() 
	   { 
	      return BankId; 
	   } 
	   
	   public void setBankId(String BankId) 
	   { 
	      this.BankId = BankId; 
	   }
	   
	   public String getstartTime() 
	   { 
		      return startTime; 
	   } 
	   
	   public void setstartTime(String startTime) 
	   { 
		      this.startTime = startTime; 
	   }
	   public String getendTime() 
	   { 
		      return endTime; 
	   } 
	   
	   public void setendTime(String endTime) 
	   { 
		      this.endTime = endTime; 
	   }
	   public int getsuccessTransaction() 
	   { 
		      return successTransaction; 
	   } 
	   
	   public void setsuccessTransaction(int successTransaction) 
	   { 
		      this.successTransaction = successTransaction; 
	   }
	   public int gettimeoutTransaction() 
	   { 
		      return timeoutTransaction; 
	   } 
	   
	   public void settimeoutTransaction(int timeoutTransaction) 
	   { 
		      this.timeoutTransaction = timeoutTransaction; 
	   }
	   public int getbusinessFailure() 
	   { 
		      return businessFailure; 
	   } 
	   
	   public void setbusinessFailure(int businessFailure) 
	   { 
		      this.businessFailure = businessFailure; 
	   }
	   public int gettotalTransaction() 
	   { 
		      return totalTransaction; 
	   } 
	   
	   public void settotalTransaction(int totalTransaction) 
	   { 
		      this.totalTransaction = totalTransaction; 
	   }
	   public String getnodeId()
	   { 
		      return nodeId; 
	   } 
	   
	   public void setnodeId(String nodeId) 
	   { 
		      this.nodeId = nodeId; 
	   }
}