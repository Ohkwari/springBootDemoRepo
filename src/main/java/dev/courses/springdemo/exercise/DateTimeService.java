package dev.courses.springdemo.exercise;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DateTimeService {
    public Instant getDateTimeNow() {
        return Instant.now();
    }
}
