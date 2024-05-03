import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {

        String timeZoneParam = req.getParameter("timezone");
        timeZoneParam = URLDecoder.decode(timeZoneParam, StandardCharsets.UTF_8);
        timeZoneParam = timeZoneParam.replace(" ", "+");
        System.out.println("timeZoneParam = " + timeZoneParam);
        if (!timeZoneParam.isEmpty()) {
            try {
                ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZoneParam));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDateTime = zonedDateTime.format(formatter);

                resp.setContentType("text/html");
                PrintWriter out = resp.getWriter();
                out.println("<html><body>");
                out.println("Поточна дата та час для " + " [" + timeZoneParam + "] "+ formattedDateTime);
                out.println("</body></html>");

            } catch (Exception e) {
             e.printStackTrace();
            }
        }
    }
}


