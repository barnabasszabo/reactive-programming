package eu.loxon.reactiveSpringMeetup.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.loxon.reactiveSpringMeetup.dto.Operation;
import eu.loxon.reactiveSpringMeetup.dto.OperationDto;
import eu.loxon.reactiveSpringMeetup.dto.OperationDto.OperationDtoBuilder;

@RestController
@RequestMapping("/api/generate")
public class GenerateRequestController {

	private static final SecureRandom RANDOM = new SecureRandom();

	@GetMapping
	public List<OperationDto> generate(
			@RequestParam(value = "num", required = false, defaultValue = "100") final Integer num,
			@RequestParam(value = "min", required = false, defaultValue = "1") final Integer minDeep,
			@RequestParam(value = "max", required = false, defaultValue = "100") final Integer maxDeep) {
		List<OperationDto> result = new ArrayList<>(num);
		for (int i = 0; i < num; i++) {
			result.add(genOperation(0, minDeep,
					minDeep >= maxDeep ? maxDeep : ThreadLocalRandom.current().nextInt(minDeep, maxDeep)));
		}
		return result;
	}

	private OperationDto genOperation(final int deepLevel, final int min, final int max) {
		Operation randomOperation = randomOperation();
		OperationDtoBuilder operationDtoBuilder = OperationDto.builder().operation(randomOperation);

		switch (randomOperation) {
		case READFILE:
		case PI:
		case READDB:
			operationDtoBuilder.leftValue((double) RANDOM.nextInt(3));
			break;
		default:
			// Left
			if (deepLevel < min) {
				operationDtoBuilder.leftOperation(genOperation(deepLevel + 1, min, max));
				operationDtoBuilder.rightOperation(genOperation(deepLevel + 1, min, max));
			} else {
				// Left
				if (((RANDOM.nextInt() % 5) != 0) && (deepLevel <= max)) {
					operationDtoBuilder.leftOperation(genOperation(deepLevel + 1, min, max));
				} else {
					operationDtoBuilder.leftValue(RANDOM.nextDouble());
				}

				// Right
				if (((RANDOM.nextInt() % 5) != 0) && (deepLevel <= max)) {
					operationDtoBuilder.rightOperation(genOperation(deepLevel + 1, min, max));
				} else {
					operationDtoBuilder.rightValue(RANDOM.nextDouble());
				}
			}

			break;
		}

		return operationDtoBuilder.build();
	}

	private Operation randomOperation() {
		int x = RANDOM.nextInt(Operation.class.getEnumConstants().length);
		return Operation.class.getEnumConstants()[x];
	}
}
