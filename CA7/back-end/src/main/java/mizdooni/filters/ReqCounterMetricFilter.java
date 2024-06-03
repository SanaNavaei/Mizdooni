package mizdooni.filters;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class ReqCounterMetricFilter implements Filter {
    private LongCounter requestCounter = GlobalOpenTelemetry
            .getMeter("Mizdooni")
            .counterBuilder("total_requests_counter")
            .setDescription("Total number of requests")
            .build();

    private LongCounter okResponseCounter = GlobalOpenTelemetry
            .getMeter("Mizdooni")
            .counterBuilder("ok_responses_counter")
            .setDescription("Total number of OK responses")
            .build();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        requestCounter.add(1);
        chain.doFilter(request, response);
        if (res.getStatus() == HttpStatus.OK.value()) {
            okResponseCounter.add(1);
        }
    }
}
