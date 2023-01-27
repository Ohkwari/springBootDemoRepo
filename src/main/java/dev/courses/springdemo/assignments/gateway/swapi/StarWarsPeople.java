package dev.courses.springdemo.assignments.gateway.swapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.courses.springdemo.assignments.gateway.swapi.utils.SWDateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StarWarsPeople {
    private String name;
    @JsonProperty(value = "birth_year")
    private String birthYear;
    private String gender;
    private Integer height;
    private Integer mass;

    Integer getAge(String birthYear) {
    return SWDateUtils.getAgeFromDateOfBirth(birthYear);
    }

}
