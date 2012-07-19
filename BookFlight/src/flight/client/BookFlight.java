package flight.client;

import java.util.Calendar;
import java.util.Date;

import flight.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BookFlight implements EntryPoint {

	private TabPanel tpanel = new TabPanel();
	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();
	private FlexTable fTable = new FlexTable();
	private Image cal1 = new Image("cal.gif");
	private Image cal2 = new Image("cal.gif");
	private PopupPanel cal1pop = new PopupPanel();
	private PopupPanel cal2pop = new PopupPanel();
	private DatePicker startDate = new DatePicker();
	private DatePicker endDate = new DatePicker();
	private Button search = new Button("Find Flight");
	private Label passengers = new Label("Passengers");
	private ListBox pListBox = new ListBox();

	private Label fromBoxLabel = new Label("From Airport");
	private TextBox fromBox = new TextBox();

	private Label toBoxLabel = new Label("To Airport");
	private TextBox toBox = new TextBox();

	private Label leaveBoxLabel = new Label("Leave (yyyy-mm-dd)");
	private TextBox leaveBox = new TextBox();

	private Label returnBoxLabel = new Label("Return (yyyy-mm-dd)");
	private TextBox returnBox = new TextBox();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		fTable.setWidget(0, 0, fromBoxLabel);
		fTable.setWidget(0, 1, fromBox);
		fTable.setWidget(1, 0, toBoxLabel);
		fTable.setWidget(1, 1, toBox);
		fTable.setWidget(2, 0, leaveBoxLabel);
		fTable.setWidget(2, 1, leaveBox);
		fTable.setWidget(3, 0, returnBoxLabel);
		fTable.setWidget(3, 1, returnBox);

		cal1.setSize("20px", "20px");
		cal2.setSize("20px", "20px");
		fTable.setWidget(2, 2, cal1);
		fTable.setWidget(3, 2, cal2);

		fTable.setWidget(4, 0, passengers);
		pListBox.addItem("1", "1");
		pListBox.addItem("2", "2");
		pListBox.addItem("3", "3");
		pListBox.addItem("4", "4");
		pListBox.addItem("5", "5");
		pListBox.addItem("6", "6");
		pListBox.addItem("7", "7");
		pListBox.addItem("8", "8");
		pListBox.addItem("9", "9");
		pListBox.addItem("10", "10");
		fTable.setWidget(4, 1, pListBox);

		fTable.setWidget(5, 0, search);

		cal1pop.setWidget(startDate);
		cal2pop.setWidget(endDate);

		// add click handlers to images
		cal1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e) {
				if (!cal1pop.isShowing()) {
					cal1pop.setPopupPosition(cal1.getAbsoluteLeft() + 20,
							cal1.getAbsoluteTop() + 20);
					cal1pop.show();
					cal2pop.hide();
				} else {
					cal1pop.hide();
				}
			}
		});

		cal2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e) {
				if (!cal2pop.isShowing()) {
					cal2pop.setPopupPosition(cal2.getAbsoluteLeft() + 20,
							cal2.getAbsoluteTop() + 20);
					cal2pop.show();
					cal1pop.hide();
				} else {
					cal2pop.hide();
				}
			}
		});
		startDate.addValueChangeHandler(new ValueChangeHandler() {
			public void onValueChange(ValueChangeEvent event) {
				Date date = (Date) event.getValue();
				PredefinedFormat pdf = PredefinedFormat.DATE_SHORT;
				String dateString = DateTimeFormat.getFormat(pdf).format(date);
				// String dateString =
				// DateTimeFormat.getShortDateFormat().format(date);
				leaveBox.setText(dateString);
				cal1pop.hide();
			}
		});

		endDate.addValueChangeHandler(new ValueChangeHandler() {
			public void onValueChange(ValueChangeEvent event) {
				Date date = (Date) event.getValue();
				PredefinedFormat pdf = PredefinedFormat.DATE_SHORT;
				String dateString = DateTimeFormat.getFormat(pdf).format(date);
				// String dateString =
				// DateTimeFormat.getShortDateFormat().format(date);
				returnBox.setText(dateString);
				cal2pop.hide();
			}
		});

		hpanel.add(fTable);
		vpanel.add(hpanel);

		tpanel.setWidth("600px");
		tpanel.add(vpanel, "Book Flights");
		tpanel.add(new VerticalPanel(), "Check In");
		tpanel.selectTab(0);

		RootPanel.get("bookFlight").add(tpanel);

		search.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e) {
				String fromAirport = fromBox.getText().trim();
				String toAirport = toBox.getText().trim();

				String searchStart = leaveBox.getText().trim();
				String searchEnd = returnBox.getText().trim();

				if (fromAirport.length() == 0) {
					Window.alert("'From' airport is required");
				} else if (toAirport.length() == 0) {
					Window.alert("'To' airport is required");
				} else if (searchStart.length() == 0) {
					Window.alert("Departure date was not provided");
				} else if (searchEnd.length() == 0) {
					Window.alert("Arrival date was not provided");
				} else {
					// DateTimeFormat f =
					// DateTimeFormat.getFormat("YYYY/MM/dd");
					PredefinedFormat pdf = PredefinedFormat.DATE_SHORT;
					DateTimeFormat f = DateTimeFormat.getFormat(pdf);
					Date dstart = null;
					Date dend = null;
					try {
						dstart = f.parse(searchStart);
					} catch (Exception error) {
						Window.alert("Start date must be in the yyyy/MM/dd format");
					}
					try {
						dend = f.parse(searchEnd);
					} catch (Exception error) {
						Window.alert("Start date must be in the yyyy/MM/dd format");
					}
					if ((dstart != null) && (dend != null)) {
						if (dstart.after(dend)) {
							Window.alert("Departure date cannot be after arrival date");
						} else if (dstart.before(now())) {
							Window.alert("Departure date cannot be in the past");
						} else if (dend.before(now())) {
							Window.alert("Arrival date cannot be in the past");
						}
						else{
							bookFlight();
						}
					} 
				}
			}
		});

	}

	public static Date now() {

		Date date = new Date();

		return date;

	}

	private void bookFlight() {
		Window.alert("Your flight is booked.");
	}
}
