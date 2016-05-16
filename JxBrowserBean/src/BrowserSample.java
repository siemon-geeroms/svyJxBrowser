import com.servoy.JxBrowserBean.ServoyConnector;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
 







import javax.swing.*;

import java.awt.*;
 
public class BrowserSample {
   public static void main(String[] args) {
       Browser browser = new Browser();
       BrowserView browserView = new BrowserView(browser);
 
       JPanel pane = new JPanel();
       pane.setLayout(new BorderLayout());
       pane.add(browserView, BorderLayout.CENTER);

       JFrame frame = new JFrame();
       frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       frame.setContentPane(pane);
       frame.setSize(700, 500);
       frame.setLocationRelativeTo(null);
       frame.setVisible(true);
       browser.addScriptContextListener(new ScriptContextAdapter() {
			@Override
		    public void onScriptContextCreated(ScriptContextEvent event) {
		        Browser browser = event.getBrowser();
		        JSValue window = browser.executeJavaScriptAndReturnValue("window");
	        Object servoyConnector = new ServoyConnector();
			window.asObject().setProperty("servoy", servoyConnector);
			}
		});
       browser.loadURL("file:///C:/test.html");
       System.out.println("Bean created");
   }
}
