package com.eugene.lysak.attractgrouptest.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class StatusInternetConnection {
	private static StatusInternetConnection status;
	  static ConnectivityManager connectionManager;
	  
	  public static StatusInternetConnection getInstance(Context Context)
	  {
	    connectionManager = (ConnectivityManager)Context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (status == null) {
	    	status = new StatusInternetConnection();
	    }
	    return status;
	  }
	  
	  public boolean WifiConnection()
	  {
	    NetworkInfo localNetworkInfo = connectionManager.getNetworkInfo(1);
	    return (localNetworkInfo != null) && (localNetworkInfo.isConnected());
	  }
	  
	  public boolean MobileConnection()
	  {
	    NetworkInfo localNetworkInfo = connectionManager.getNetworkInfo(0);
	    boolean bool = false;
	    if (localNetworkInfo != null)
	    {
	      boolean bool_check = localNetworkInfo.isConnected();
	      bool = false;
	      if (bool_check) {
	        bool = true;
	      }
	    }
	    return bool;
	  }
}
