package org.jenkins.plugins.statistics.gatherer.model.build;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class SourceObject extends HashMap<String, Object> {
    private static final ObjectMapper json = new ObjectMapper();

    private final Object source;

    private Map<String, Object> fields;

    public SourceObject(Object source) {
        this.source = source;
    }
}
