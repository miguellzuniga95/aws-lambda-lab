package com.belatrix.demo.util;

import com.belatrix.demo.domain.ContentRequest;
import com.belatrix.demo.domain.GenericEvent;
import com.belatrix.demo.domain.GenericRecord;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.IntStream;

public class AwsEventHelper {

    /**
     * Method called by the handler to obtain the list of messages inside the Json inputstream
     * @param inputStream Json from AWS Event
     * @return list of request to send the content endpoint
     * @throws IOException
     */
    public static List<ContentRequest> parseEvent(InputStream inputStream) throws IOException {
        List<ContentRequest> messageList = new ArrayList<>();
        String standardInput = getStringFromInputStream(inputStream);
        Gson gson = new Gson();

        GenericEvent genericEvent = gson.fromJson(standardInput,GenericEvent.class);
        IntStream.range(0,genericEvent.getRecords().size()).forEach(index -> {
            GenericRecord record = genericEvent.getRecords().get(index);
            messageList.add(getBodyFromEvent(standardInput,record,index));
        });
        return messageList;
    }

    /**
     * Utility method to obtain a String from Json inputstream
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String getStringFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder textBuilder = new StringBuilder();
        Reader reader = new BufferedReader(new InputStreamReader(inputStream));
        int c = 0;
        while ((c = reader.read()) != -1) {
            textBuilder.append((char) c);
        }
        String input = textBuilder.toString().replace("EventSource","eventSource");
        return input;
    }

    /**
     * Used to get the data needed from the Json input
     * @param standardInput String representation of Json from event input
     * @param record Object used to check what kind of event was received by the handler
     * @param index to iterate over the list of Records
     * @return
     */
    public static ContentRequest getBodyFromEvent(String standardInput, GenericRecord record, int index) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(standardInput).getAsJsonObject();
        ContentRequest contentRequest = null;
        if (record.getEventSource().equals(AwsEvents.AWS_SNS.getEventType())){
            JsonElement messageElement = json.getAsJsonArray("Records").get(index).getAsJsonObject().get("Sns").getAsJsonObject().get("Message");
            contentRequest = gson.fromJson(messageElement.getAsString(),ContentRequest.class);
        } else if (record.getEventSource().equals(AwsEvents.AWS_KINESIS.getEventType())) {
            JsonElement dataElement = json.getAsJsonArray("Records").get(index).getAsJsonObject().get("kinesis").getAsJsonObject().get("data");
            byte[] decodedBytes = Base64.getDecoder().decode(dataElement.getAsString());
            String dataStr = new String(decodedBytes);
            contentRequest = gson.fromJson(dataStr,ContentRequest.class);
        } else if (record.getEventSource().equals(AwsEvents.AWS_SQS.getEventType())) {
            JsonElement bodyElement = json.getAsJsonArray("Records").get(index).getAsJsonObject().get("body");
            contentRequest = gson.fromJson(bodyElement.getAsString(),ContentRequest.class);
        }
        return contentRequest;
    }

}
