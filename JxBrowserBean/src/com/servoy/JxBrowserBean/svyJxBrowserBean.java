package com.servoy.JxBrowserBean;
import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.servoy.j2db.dataprocessing.IRecord;
import com.servoy.j2db.dataui.IServoyAwareBean;
import com.servoy.j2db.plugins.IClientPluginAccess;
import com.servoy.j2db.util.Debug;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;


public class svyJxBrowserBean extends JPanel implements IServoyAwareBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Browser browser;
	private BrowserView browserView;
	private ServoyConnector servoyConnector;

	public svyJxBrowserBean() {
		super();
		browser = new Browser();
		browserView = new BrowserView(browser);
 
		setLayout(new BorderLayout());
		add(browserView, BorderLayout.CENTER);
		Debug.log("bean created");
	}
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
		browser.loadURL("file:///C:/test.html");
	}

	public void setSelectedRecord(IRecord arg0) {
		// TODO Auto-generated method stub

	}

}
