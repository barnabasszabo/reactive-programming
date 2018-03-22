package eu.loxon.reactiveSpringMeetup.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Document(collection="data")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Data {
	
	@Id
	private Integer id;
	private Double value;
	
}
