package eu.loxon.reactiveSpringMeetup.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import eu.loxon.reactiveSpringMeetup.dto.OperationDto;
import eu.loxon.reactiveSpringMeetup.service.CalculationASyncService;
import reactor.core.publisher.Flux;

@Configuration
public class ReactiveRoutes {

	@Bean
	RouterFunction<?> routes(CalculationASyncService calcService) {
		return RouterFunctions.route(RequestPredicates.POST("/api/calculate/new"), r -> {
			Flux<OperationDto> body = r.bodyToFlux(OperationDto.class);
			Flux<Double> calculate = calcService.calculate(body);
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(calculate, Double.class);
		});
	}

}
