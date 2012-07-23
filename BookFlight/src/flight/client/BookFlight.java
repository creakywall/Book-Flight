package flight.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import flight.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
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
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
@SuppressWarnings("unused")
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
	private Label aPassengers = new Label("Adult Passengers");
	private ListBox aPListBox = new ListBox();

	private Label fromBoxLabel = new Label("From Airport");

	private Label toBoxLabel = new Label("To Airport");
	private TextBox toBox = new TextBox();

	private Label leaveBoxLabel = new Label("Leave (yyyy-mm-dd)");
	private TextBox leaveBox = new TextBox();

	private Label returnBoxLabel = new Label("Return (yyyy-mm-dd)");
	private TextBox returnBox = new TextBox();

	private SuggestBox airportSuggestBoxFrom = new SuggestBox();
	private SuggestBox airportSuggestBoxTo = new SuggestBox();

	/**
	 * This is the entry point method.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onModuleLoad() {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				"/bookflight/GetAirports");

		try {
			// send the request
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					// handle error
				}

				public void onResponseReceived(Request request,
						Response response) {
					// check the status code
					int statusCode = response.getStatusCode();
					if (statusCode == 200) {
						String[] airportList = response.getText().split("\n");

						MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
						oracle.addAll(Arrays.asList(airportList));

						airportSuggestBoxFrom = new SuggestBox(oracle);
						airportSuggestBoxTo = new SuggestBox(oracle);
						fTable.setWidget(0, 1, airportSuggestBoxFrom);
						fTable.setWidget(1, 1, airportSuggestBoxTo);
					}
				}
			});
		} catch (RequestException e) {
			// handle error
		}
		fTable.setWidget(0, 0, fromBoxLabel);
		fTable.setWidget(1, 0, toBoxLabel);
		fTable.setWidget(2, 0, leaveBoxLabel);
		fTable.setWidget(2, 1, leaveBox);
		fTable.setWidget(3, 0, returnBoxLabel);
		fTable.setWidget(3, 1, returnBox);

		cal1.setSize("20px", "20px");
		cal2.setSize("20px", "20px");
		fTable.setWidget(2, 2, cal1);
		fTable.setWidget(3, 2, cal2);

		fTable.setWidget(4, 0, aPassengers);
		aPListBox.addItem("1", "1");
		aPListBox.addItem("2", "2");
		aPListBox.addItem("3", "3");
		aPListBox.addItem("4", "4");
		aPListBox.addItem("5", "5");
		aPListBox.addItem("6", "6");
		aPListBox.addItem("7", "7");
		aPListBox.addItem("8", "8");
		aPListBox.addItem("9", "9");
		aPListBox.addItem("10", "10");
		fTable.setWidget(4, 1, aPListBox);

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
				leaveBox.setText(dateString);
				cal1pop.hide();
			}
		});

		endDate.addValueChangeHandler(new ValueChangeHandler() {
			public void onValueChange(ValueChangeEvent event) {
				Date date = (Date) event.getValue();
				PredefinedFormat pdf = PredefinedFormat.DATE_SHORT;
				String dateString = DateTimeFormat.getFormat(pdf).format(date);
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

		airportSuggestBoxFrom.setFocus(true);

		search.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e) {
				String fromAirport = airportSuggestBoxFrom.getText().trim();
				String toAirport = airportSuggestBoxTo.getText().trim();

				String searchStart = leaveBox.getText().trim();
				String searchEnd = returnBox.getText().trim();

				int aInd = aPListBox.getSelectedIndex();
				String adult = aPListBox.getValue(aInd);

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
						} else {
							bookFlight(fromAirport, toAirport, searchStart,
									searchEnd, adult);
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

	private void bookFlight(String from, String to, String searchStart,
			String searchEnd, String adult) {
		Window.alert("Your flight is booked.");
		Window.open("http://www.kayak.com/#/flights/" + from + "-" + to + "/"
				+ searchStart + "/" + searchEnd + "/" + adult + "adults",
				"_blank", null);
	}
}
