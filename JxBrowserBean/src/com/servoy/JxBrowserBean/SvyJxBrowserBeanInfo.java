package com.servoy.JxBrowserBean;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

import com.servoy.j2db.dataui.PropertyEditorClass;
import com.servoy.j2db.dataui.PropertyEditorHint;
import com.servoy.j2db.util.Debug;

public class SvyJxBrowserBeanInfo extends SimpleBeanInfo {

	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		
		try {
	    	List<PropertyDescriptor> liste = new ArrayList<PropertyDescriptor>();
	    	PropertyDescriptor pd = new PropertyDescriptor("border", SvyJxBrowser.class);
        	pd.setDisplayName("borderType");
        	liste.add(pd);
	    	liste.add(new PropertyDescriptor("focusable", SvyJxBrowser.class));
	    	pd = new PropertyDescriptor("toolTipMessage", SvyJxBrowser.class);
	    	pd.setDisplayName("toolTipText");
	    	liste.add(pd);
	    	liste.add(new PropertyDescriptor("size", SvyJxBrowser.class));
	    	pd = new PropertyDescriptor("dataProviderID", SvyJxBrowser.class);
			PropertyEditorHint dpHint = new PropertyEditorHint(PropertyEditorClass.dataprovider);
			pd.setValue(PropertyEditorHint.PROPERTY_EDITOR_HINT, dpHint);
			liste.add(pd);
	    	liste.add(new PropertyDescriptor("isUrl", SvyJxBrowser.class));
			pd = new PropertyDescriptor("onDataChange", SvyJxBrowser.class);
			dpHint = new PropertyEditorHint(PropertyEditorClass.method);
			pd.setValue(PropertyEditorHint.PROPERTY_EDITOR_HINT, dpHint);
			liste.add(pd);
	    	
	    	PropertyDescriptor propdescriptors[] = liste.toArray(new PropertyDescriptor[0]);
	        return propdescriptors;
		}
	    catch(Exception e)
	    {
	        Debug.error((new StringBuilder()).append("SvyJxBrowserBean: unexpected exception: ").append(e).toString(), e);
	    }
	    return null;
	}

}
