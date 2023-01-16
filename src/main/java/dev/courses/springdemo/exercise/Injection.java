package dev.courses.springdemo.exercise;

import dev.courses.springdemo.exercise.AppLogger;
import dev.courses.springdemo.exercise.DateTimeService;
import dev.courses.springdemo.exercise.OutputAggregator;
import dev.courses.springdemo.exercise.TextFormatter;

public class Injection {

    public static void main(String[] args) {
        // call OutputAggregator.printInput("Hello world") method with an input
        OutputAggregator outputAg = new OutputAggregator(
                new AppLogger(),
                new TextFormatter(new DateTimeService())
        );
        outputAg.printInput("Hello world");
    }

}
