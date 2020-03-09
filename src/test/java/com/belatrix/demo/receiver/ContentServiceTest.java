package com.belatrix.demo.receiver;

import com.amazonaws.services.lambda.runtime.Context;
import com.belatrix.demo.domain.ContentResponse;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ContentServiceTest {

    static ContentService contentService;
    static ContentApi api;
    static Context context;

    @BeforeAll
    static void init() {
        contentService = new ContentService("1",context);
        api = mock(ContentApi.class);
    }

    @Test
    void givenInstance_whenCalledService() throws IOException {
        JsonReader reader = new JsonReader(new FileReader("src/test/resources/content-test.json"));
        ContentResponse contentResponse = new Gson().fromJson(reader, ContentResponse.class);

        assertAll("contentResponse",
                () -> assertNotNull(contentResponse.getData()),
                () -> assertNotNull(contentResponse.getStatus()),
                () -> assertFalse(contentResponse.getVersion().isEmpty()),
                () -> assertFalse(contentResponse.getData().getId().isEmpty())
        );
    }

}
