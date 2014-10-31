package ru.yandex.jackson.datatype.bolts.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ru.yandex.bolts.collection.Option;

public class OptionBean<T> {

    private final Option<T> value;

    @JsonCreator
    public OptionBean(@JsonProperty("value") Option<T> value) {
        this.value = value;
    }

    public Option<T> getValue() {
        return value;
    }
}
