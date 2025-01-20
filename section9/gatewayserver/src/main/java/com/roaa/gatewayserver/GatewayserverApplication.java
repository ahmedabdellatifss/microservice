package com.roaa.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}


	@Bean
	public RouteLocator roaaBankRouteConfig(RouteLocatorBuilder builder) {

		return builder.routes()
				.route(p -> p
						.path("/roaabank/accounts/**")
						.filters(f -> f.rewritePath("/roaabank/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/roaabank/cards/**")
						.filters(f -> f.rewritePath("/roaabank/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.route(p -> p
						.path("/roaabank/loans/**")
						.filters(f -> f.rewritePath("/roaabank/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://LOANS")).build();
	}
}
