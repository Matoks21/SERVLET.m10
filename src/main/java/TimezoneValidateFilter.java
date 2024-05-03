import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebFilter("/time")
public class TimezoneValidateFilter extends HttpFilter {
    private static final String TIMEZONE_REGEX = "^(?:UTC[+-](?:1[0-2]|[0]?[0-9])|GMT)$";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String timeZoneParam = request.getParameter("timezone");
        timeZoneParam = URLDecoder.decode(timeZoneParam, StandardCharsets.UTF_8);
        timeZoneParam = timeZoneParam.replace(" ", "+");

        if (timeZoneParam.isEmpty()) {
            response.setContentType("text/html");

            PrintWriter out = response.getWriter();
            if (out != null) {
                out.println("Invalid timezone");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            if (isValidTimezoneFormat(timeZoneParam)) {
                chain.doFilter(request, response);
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                if (out != null) {
                    out.println("Invalid timezone format");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        }
    }


    public boolean isValidTimezoneFormat(String timezone) {
        Pattern pattern = Pattern.compile(TIMEZONE_REGEX);
        Matcher matcher = pattern.matcher(timezone);
        return matcher.matches();

    }
}
