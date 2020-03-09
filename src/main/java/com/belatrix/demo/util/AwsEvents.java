package com.belatrix.demo.util;

import lombok.AllArgsConstructor;

public enum AwsEvents {

    AWS_SNS("aws:sns"),
    AWS_SQS("aws:sqs"),
    AWS_KINESIS("aws:kinesis");

    private String eventType;

    AwsEvents(final String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }
}
