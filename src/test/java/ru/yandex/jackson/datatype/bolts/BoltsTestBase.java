package ru.yandex.jackson.datatype.bolts;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BoltsTestBase {

    protected ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new BoltsModule());
        return mapper;
    }
}
