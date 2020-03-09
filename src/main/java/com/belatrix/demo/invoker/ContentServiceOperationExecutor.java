package com.belatrix.demo.invoker;

import com.belatrix.demo.domain.ContentResponse;
import com.belatrix.demo.command.ServiceOperation;
import lombok.RequiredArgsConstructor;

/**
 * Invoker class
 * @author mzuniga
 */
@RequiredArgsConstructor
public class ContentServiceOperationExecutor {

    /**
     * Command to be executed by the Invoker. Performs the main operation
     * Proceed to {@link com.belatrix.demo.command.ServiceOperation} class for more info
     */
    private final ServiceOperation serviceOperation;

    public ContentResponse makeCall() {
        return serviceOperation.execute();
    }

}
