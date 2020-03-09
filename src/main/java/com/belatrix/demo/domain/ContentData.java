package com.belatrix.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentData implements Serializable {

    private String id;
    private Map<String,String> section;
    private String date;
    private String type;
    private String source;
    private String title;
    private String caption;
    private String description;
    private String alt;
}
