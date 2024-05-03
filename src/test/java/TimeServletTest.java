import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

class TimeServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private TimeServlet timeServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        timeServlet = new TimeServlet();
    }

    @Test
    void testDoGetWithValidTimeZone() throws Exception {

        when(request.getParameter("timezone")).thenReturn("UTC+2");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        timeServlet.doGet(request, response);
        writer.flush();

        verify(response).setContentType("text/html");
        verify(response).getWriter();

    }

}
