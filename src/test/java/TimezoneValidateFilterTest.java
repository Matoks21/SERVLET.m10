
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;

class TimezoneValidateFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    private TimezoneValidateFilter filter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new TimezoneValidateFilter();

        when(request.getParameter("timezone")).thenReturn("");
        when(request.getParameter("timezone")).thenReturn("InvalidTimezone");     }

    @Test
    void testDoFilterWithValidTimezone() throws Exception {

        when(request.getParameter("timezone")).thenReturn("UTC+2");


        filter.doFilter(request, response, chain);


        verify(chain).doFilter(request, response);
    }

    @Test
    void testDoFilterWithEmptyTimezone() throws Exception {

        when(request.getParameter("timezone")).thenReturn("");


        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);


        filter.doFilter(request, response, chain);


        verify(response).setContentType("text/html");
        verify(writer).println("Invalid timezone");
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void testDoFilterWithInvalidTimezoneFormat() throws Exception {

        when(request.getParameter("timezone")).thenReturn("InvalidTimezone");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        filter.doFilter(request, response, chain);

        verify(response).setContentType("text/html");
        verify(writer).println("Invalid timezone format");
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
