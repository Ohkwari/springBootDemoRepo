package dev.courses.springdemo.assignments.gateway.swapi;

import dev.courses.springdemo.assignments.controller.error.BadGatewayException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class StarWarsGateway {

    @Value("${application.star-wars-api.url}")
    private String starWarsApiUrl;

    public StarWarsPeople getPeopleById(Long peopleId) {
        try {
            return new RestTemplateBuilder().build().getForObject(
                    starWarsApiUrl + "/people/{peopleId}",
                    StarWarsPeople.class,
                    Map.of("peopleId", peopleId)
            );
        } catch (HttpClientErrorException ex) {
            throw new BadGatewayException("Error when calling Star Wars API", ex);
        }
    }
}
