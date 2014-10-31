package ru.yandex.jackson.datatype.bolts.ser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ru.yandex.bolts.collection.Option;

public class OptionSerializer extends StdSerializer<Option<?>> {

    public OptionSerializer(JavaType type) {
        super(type);
    }

    @Override
    public void serialize(Option<?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (value.isDefined()) {
            provider.defaultSerializeValue(value.get(), jgen);
        } else {
            provider.defaultSerializeNull(jgen);
        }
    }
}
