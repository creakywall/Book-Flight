package flight.client;

import flight.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.google.gwt.core.client.EntryPoint;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BookFlight implements EntryPoint {
	
	private TabPanel tpanel = new TabPanel();
	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();
	private FlexTable inputtable = new FlexTable();
	private Image cal1 = new Image("cal.gif");
	private Image cal2 = new Image("cal.gif");

	
	private TextBox fromBox = new TextBox();
	private Label fromBoxLabel = new Label("From Airport");
	
	private TextBox toBox = new TextBox();
	private Label toBoxLabel = new Label("To Airport");
	
	private TextBox leaveBox = new TextBox();
	private Label leaveBoxLabel = new Label("Leave (mm/dd/yyyy)");
	
	private TextBox returnBox = new TextBox();
	private Label returnBoxLabel = new Label("Return (mm/dd/yyyy)");
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

	}
}

