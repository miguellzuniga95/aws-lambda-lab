package com.belatrix.demo.command;

import com.belatrix.demo.domain.ContentResponse;

@FunctionalInterface
public interface ServiceOperation {

    ContentResponse execute();

}
