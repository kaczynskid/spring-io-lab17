package io.spring.lab.demo;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

class GreetingFailureAnalyzer extends AbstractFailureAnalyzer<MissingGreetTemplate> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, MissingGreetTemplate cause) {
        return new FailureAnalysis(
                "Greeting template undefined.",
                "Define greet.template property.",
                rootFailure);
    }
}
