package com.servoy.JxBrowserBean;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.servoy.j2db.plugins.IClientPluginAccess;
import com.teamdev.jxbrowser.chromium.JSArray;
import com.teamdev.jxbrowser.chromium.JSValue;

public class ServoyConnector {
	
	private IClientPluginAccess clientConnector;
	private final static Logger LOGGER = Logger.getLogger(ServoyConnector.class.getName()); 
	
	public ServoyConnector(IClientPluginAccess clientCon)
	{
		LOGGER.info("creating servoy connector");
		clientConnector = clientCon;
	}
	
	public ServoyConnector()
	{
		LOGGER.info("creating servoy connector");
	}
	
	public Object runServoyMethod(String _context, String _methodName, JSArray args)
	{
		return runServoyMethod(_context,_methodName,args,false);
	}
	
	public Object runServoyMethod(String _context, String _methodName, JSArray args, boolean assync)
	{
		ArrayList<Object> conv_args = new ArrayList<Object>();
		
		int l = args.length();
		
		for(int ind = 0; ind < l; ind++)
		{
			JSValue value = args.get(ind);
			if (value.isString())
			{
				conv_args.add(value.getStringValue());
			}
			else if (value.isNumber())
			{
				conv_args.add(value.getNumberValue());
			} 	
		}
		
		try {
			return clientConnector.executeMethod(_context, _methodName , conv_args.toArray(),assync);
		} catch (Exception e) {
			clientConnector.handleException(null, e);
			return null;
		}
	}
	
	public Object testMethod(Object i)  
	{
		Object[] args = {i};
		try {
			return clientConnector.executeMethod("formula_dtl_copy", "onSortFoundset" , args,false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}
}
