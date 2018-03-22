package eu.loxon.reactiveSpringMeetup.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto implements Serializable {

	private static final long serialVersionUID = 6810715238561822736L;

	private Operation operation;
	
	private Double leftValue;
	private Double rightValue;
	
	private OperationDto leftOperation;
	private OperationDto rightOperation;
	
}
