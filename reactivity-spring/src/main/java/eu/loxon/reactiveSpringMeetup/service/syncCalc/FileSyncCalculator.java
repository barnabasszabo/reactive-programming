package eu.loxon.reactiveSpringMeetup.service.syncCalc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import eu.loxon.reactiveSpringMeetup.dto.Operation;
import eu.loxon.reactiveSpringMeetup.service.GeneralCalculator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileSyncCalculator extends GeneralCalculator implements SyncCalculator {

	@Value("${calculation.filePath}")
	private String fileUri;

	private Path filePath;

	@PostConstruct
	public void init() {
		filePath = Paths.get(fileUri);
	}

	@Override
	public Operation operationCode() {
		return Operation.READFILE;
	}

	@Override
	public double calculate(final Double leftValue, final Double rightValue) {
		double result = 0d;
		int skipLines = leftValue.intValue();
		try (Stream<String> lines = Files.lines(filePath)) {
			String line = lines.skip(skipLines).findFirst().get();
			result = line.length();
		} catch (Exception e) {
			log.warn("Invalid line number!");
		}
		return result;
	}

}
