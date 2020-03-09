package com.belatrix.demo.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.belatrix.demo.util.AwsEventHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LambdaHandlerTest {

    static LambdaHandler lambdaHandler;
    static Context context;
    static LambdaLogger lambdaLogger;

    @BeforeAll
    static void init() {
        lambdaHandler = new LambdaHandler();
        context = mock(Context.class);
        lambdaLogger = mock(LambdaLogger.class);
    }

    @Test
    void givenAwsEvent_whenEmpty() throws Exception {
        InputStream inputStream = new FileInputStream("src/test/resources/empty-event-test.json");
        OutputStream outputStream = new ByteArrayOutputStream();

        when(context.getLogger()).thenReturn(lambdaLogger);
        doNothing().when(lambdaLogger).log(anyString());
        lambdaHandler.handleRequest(inputStream, outputStream,context);

        Assertions.assertEquals("EMPTY",outputStream.toString());
    }

    @Test
    void givenSNS_whenOk() throws Exception {
        InputStream inputStream = new FileInputStream("src/test/resources/sns-event-test.json");
        OutputStream outputStream = new ByteArrayOutputStream();

        when(context.getLogger()).thenReturn(lambdaLogger);
        doNothing().when(lambdaLogger).log(anyString());
        lambdaHandler.handleRequest(inputStream, outputStream,context);

        Assertions.assertEquals("OK",outputStream.toString());
    }

    @Test
    void givenKinesis_whenOk() throws Exception {
        InputStream inputStream = new FileInputStream("src/test/resources/kinesis-event-test.json");
        OutputStream outputStream = new ByteArrayOutputStream();

        when(context.getLogger()).thenReturn(lambdaLogger);
        doNothing().when(lambdaLogger).log(anyString());
        lambdaHandler.handleRequest(inputStream, outputStream,context);

        Assertions.assertEquals("OK",outputStream.toString());
    }

    @Test
    void givenSQS_whenOk() throws Exception {
        InputStream inputStream = new FileInputStream("src/test/resources/sqs-event-test.json");
        OutputStream outputStream = new ByteArrayOutputStream();

        when(context.getLogger()).thenReturn(lambdaLogger);
        doNothing().when(lambdaLogger).log(anyString());
        lambdaHandler.handleRequest(inputStream, outputStream,context);

        Assertions.assertEquals("OK",outputStream.toString());
    }


}
