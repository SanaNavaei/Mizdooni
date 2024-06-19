package mizdooni.config;

import mizdooni.filters.LoggerFilter;
import mizdooni.filters.ReqCounterMetricFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<ReqCounterMetricFilter> reqCounterMetricFilter() {
        FilterRegistrationBean<ReqCounterMetricFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new ReqCounterMetricFilter());
        reg.setOrder(1);
        return reg;
    }

    @Bean
    public FilterRegistrationBean<LoggerFilter> loggerFilter() {
        FilterRegistrationBean<LoggerFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new LoggerFilter());
        reg.setOrder(2);
        return reg;
    }
}
