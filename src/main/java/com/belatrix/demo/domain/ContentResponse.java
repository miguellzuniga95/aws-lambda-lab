package com.belatrix.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponse implements Serializable {

    private String timestamp;
    private String version;
    private ContentData data;
    private Map<String, String> status;

}
