package xyz.arunangshu.expensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import xyz.arunangshu.expensetracker.filters.AuthFilter;

@SpringBootApplication
public class ExpenseTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApiApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> filterFilterRegistrationBean() {
		FilterRegistrationBean<AuthFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		filterFilterRegistrationBean.setFilter(authFilter);
		filterFilterRegistrationBean.addUrlPatterns("/api/categories/*");
		return filterFilterRegistrationBean;
	}
}
