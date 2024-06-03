package mizdooni.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import mizdooni.utils.MizdooniMetrics;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class ReqCounterMetricFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        MizdooniMetrics.newRequest();
        chain.doFilter(request, response);
        if (res.getStatus() == HttpStatus.OK.value()) {
            MizdooniMetrics.newOkResponse();
        }
    }
}
