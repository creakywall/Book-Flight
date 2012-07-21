package flight.server;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class GetAirports extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String airport = "";

		FileReader fr = new FileReader("airports.txt");
		BufferedReader bfr = new BufferedReader(fr);

		String line = null;
		while ((line = bfr.readLine()) != null) {
			airport = airport + line.trim() + "\n";
		}

		PrintWriter out = response.getWriter();
		out.print(airport);
	}

}
