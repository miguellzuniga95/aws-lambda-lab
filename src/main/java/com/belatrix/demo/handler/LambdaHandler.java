package com.belatrix.demo.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.belatrix.demo.command.ContentServiceOperation;
import com.belatrix.demo.command.ServiceOperation;
import com.belatrix.demo.domain.ContentRequest;
import com.belatrix.demo.domain.ContentResponse;
import com.belatrix.demo.invoker.ContentServiceOperationExecutor;
import com.belatrix.demo.receiver.ContentService;
import com.belatrix.demo.util.AwsEventHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementing Command pattern
 * @author mzuniga
 */
public class LambdaHandler implements RequestStreamHandler {

    /**
     * Method to manage input from different AWS Events
     * @param inputStream AWS Event, either SNS, Kinesis or SQS
     * @param outputStream Lambda output
     * @param context AWS Context used to log CloudWatch
     */
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        List<ContentRequest> listRequest = new ArrayList<>();
        List<ContentResponse> listResponse = new ArrayList<>();

        try {
            listRequest = AwsEventHelper.parseEvent(inputStream);
        } catch (IOException e) {
            context.getLogger().log("Failed to call service!!");
            outputStream.flush();
            outputStream.write(new String("ERROR").getBytes());
        }

        if (!listRequest.isEmpty()) {
            outputStream.write(new String("OK").getBytes());
            listResponse = listRequest.stream().map(request -> {
                ServiceOperation serviceOperation = new ContentServiceOperation(new ContentService(request.getId(), context));
                ContentServiceOperationExecutor executor = new ContentServiceOperationExecutor(serviceOperation);
                ContentResponse response = executor.makeCall();
                return response;
            }).collect(Collectors.toList());
        } else {
            outputStream.flush();
            outputStream.write(new String("EMPTY").getBytes());
        }

        listResponse.forEach(response -> {
            context.getLogger().log(response.toString());
        });
    }
}



