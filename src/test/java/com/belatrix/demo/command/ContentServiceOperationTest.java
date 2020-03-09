package com.belatrix.demo.command;

import com.belatrix.demo.domain.ContentResponse;
import com.belatrix.demo.receiver.ContentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContentServiceOperationTest {


    private static ContentServiceOperation contentServiceOperation;
    private static ContentService contentService;

    @BeforeAll
    static void init() {
        contentService = mock(ContentService.class);
        contentServiceOperation = new ContentServiceOperation(contentService);
    }

    @Test
    void givenInstance_whenExecuteMethod() {
        ContentResponse response = new ContentResponse();
        when(contentService.callService()).thenReturn(response);

        Assertions.assertEquals(response,contentService.callService());
    }


}
