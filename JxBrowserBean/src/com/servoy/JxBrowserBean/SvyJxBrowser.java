package com.servoy.JxBrowserBean;
import java.awt.BorderLayout;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.Border;

import org.mozilla.javascript.annotations.JSFunction;
import org.mozilla.javascript.annotations.JSGetter;
import org.mozilla.javascript.annotations.JSSetter;

import com.servoy.j2db.dataprocessing.IRecord;
import com.servoy.j2db.dataui.IServoyAwareBean;
import com.servoy.j2db.documentation.ServoyDocumented;
import com.servoy.j2db.plugins.IClientPluginAccess;
import com.servoy.j2db.scripting.IScriptable;
import com.servoy.j2db.util.Debug;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSArray;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

@ServoyDocumented
public class SvyJxBrowser extends JPanel implements IServoyAwareBean, IScriptable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Border border;
	private boolean focusable = true;
	private String dataProviderID;
	private String toolTipMessage;
	private boolean isUrl = true;
	private String url;
	private String content;
	
	private String onDataChange;
	
	private Browser browser;
	private BrowserView browserView;
	private ServoyConnector servoyConnector;

	public SvyJxBrowser() {
		super();
		browser = new Browser();
		browserView = new BrowserView(browser);
 
		setLayout(new BorderLayout());
		add(browserView, BorderLayout.CENTER);
		Debug.log("bean created");
	}
	/**
	 * This is the method description.
	 *
	 * @sample
	 * var message = someMethod('someParameter');
	 *
	 * @param simpleParameterName this is a simple parameter name
	 *
	 * @return what the method returns
	 */
	@JSFunction
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setValidationEnabled(boolean arg0) {
		// TODO Auto-generated method stub

	}

	public boolean stopUIEditing(boolean arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void initialize(IClientPluginAccess _clientPluginAccess) {

		servoyConnector = new ServoyConnector(_clientPluginAccess);
		browser.addScriptContextListener(new ScriptContextAdapter() {
			@Override
		    public void onScriptContextCreated(ScriptContextEvent event) {
		        Browser browser = event.getBrowser();
		        JSValue window = browser.executeJavaScriptAndReturnValue("window");
	        window.asObject().setProperty("servoy", servoyConnector);
			}
		});
	}
	
	public void setSelectedRecord(IRecord record) {
		try
		{
			if(dataProviderID != null)
			{
				if(isUrl)
				{
					url = (String)record.getValue(dataProviderID);
					browser.loadURL(url);
				}
				else
				{
					content = (String)record.getValue(dataProviderID);
					browser.loadHTML(content);
				}
			}
		}
		catch(Exception e)
		{
			Debug.error(e.getMessage());
		}
	}
	
	public void executeJavaScript(String javaScript)
	{
		 browser.executeJavaScript(javaScript);
			
	}
	
	public Object executeJavaScriptAndWait(String javaScript)
	{
		try {
			JSValue _result = browser.executeJavaScriptAndReturnValue(javaScript);	
			if (_result.isArray())
			{
				JSArray _jsArray  = _result.asArray();
				int len = _jsArray.length();
				ArrayList<Object> conv_result = new ArrayList<Object>();
				for(int ind = 0; ind < len; ind++)
				{
					JSValue value = _jsArray.get(ind);
					if (value.isString())
					{
						conv_result.add(value.getStringValue());
					}
					else if (value.isNumber())
					{
						conv_result.add(value.getNumberValue());
					} 	
				}
				return conv_result.toArray();
			}
			else if (_result.isNumber())
			{
				return _result.getNumberValue();
			}
			else if (_result.isString())
			{
				return _result.getStringValue();
			}
			else if (_result.isBoolean())
			{
				return _result.getBooleanValue();
			}
			else
			{
				return _result;
			}
		} catch (Exception e) {
			Debug.error(e.getMessage());
			return null;
		}
	}
	
	
	@JSGetter
	public String getDataProvider() {
		return dataProviderID;
	}
	
	@JSSetter
	public void setDataProvider(final String dataProvider) {
		Debug.log(dataProvider);
		this.dataProviderID = dataProvider;
	}
	public Border getBorder() {
		return border;
	}
	public void setBorder(Border border) {
		this.border = border;
		this.setBorder(this.border);
	}
	public boolean isFocusable() {
		return focusable;
	}
	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
		setFocusable(this.focusable);
	}
	public String getDataProviderID() {
		return dataProviderID;
	}
	public void setDataProviderID(String dataProviderID) {
		this.dataProviderID = dataProviderID;
	}
	public String getToolTipMessage() {
		return toolTipMessage;
	}
	public void setToolTipMessage(String toolTipMessage) {
		this.toolTipMessage = toolTipMessage;
		setToolTipMessage(toolTipMessage);
	}
	public String getOnDataChange() {
		return onDataChange;
	}
	public void setOnDataChange(String onDataChange) {
		this.onDataChange = onDataChange;
	}
	public boolean isUrl() {
		return isUrl;
	}
	public void setUrl(boolean isUrl) {
		this.isUrl = isUrl;
	}
	public String getUrl() {
		return url;
	}
	public void loadUrl(String url) {
		this.url = url;
		try {
			browser.loadURL(this.url);
		} catch (Exception e) {
			Debug.error(e.getMessage());
		}
	}
	public String getContent() {
		return content;
	}
	public void loadContent(String content) {
		this.content = content;
		try {
			browser.loadHTML(this.content);
		} catch (Exception e) {
			Debug.error(e.getMessage());
		}
	}

}
