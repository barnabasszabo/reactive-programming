package eu.loxon.reactiveSpringMeetup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.loxon.reactiveSpringMeetup.dto.OperationDto;
import eu.loxon.reactiveSpringMeetup.service.CalculationASyncService;
import eu.loxon.reactiveSpringMeetup.service.CalculationSyncService;
import eu.loxon.reactiveSpringMeetup.service.SyncLoopType;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/calculate")
public class CalculateController {

	@Autowired
	private CalculationSyncService syncService;

	@Autowired
	private CalculationASyncService asyncService;

	@PostMapping("/sync")
	public List<Double> indexSync(@RequestBody final List<OperationDto> calcRequest, @RequestParam(name = "type", required = false, defaultValue="PARALLEL_STREAM") final SyncLoopType loopType) {
		return syncService.calculate(calcRequest, loopType);
	}

	@PostMapping
	public Flux<Double> indexReact(@RequestParam(name = "sync", required = false) final boolean isSync,
			@RequestBody final Flux<OperationDto> calcRequest) {
		return asyncService.calculate(calcRequest);
	}

}
