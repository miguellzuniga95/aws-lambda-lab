package com.belatrix.demo.command;

import com.belatrix.demo.domain.ContentResponse;
import com.belatrix.demo.receiver.ContentService;
import lombok.RequiredArgsConstructor;

/**
 * Command class
 * @author mzuniga
 */
@RequiredArgsConstructor
public class ContentServiceOperation implements ServiceOperation {

    /**
     * Receiver object passed by constructor to execute the call to the service
     * Proceed to {@link com.belatrix.demo.receiver.ContentService} class for more info
     */
    private final ContentService contentService;

    @Override
    public ContentResponse execute() {
        return contentService.callService();
    }
}
