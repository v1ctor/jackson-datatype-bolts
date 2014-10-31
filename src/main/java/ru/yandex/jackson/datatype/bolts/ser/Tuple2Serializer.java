package ru.yandex.jackson.datatype.bolts.ser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ru.yandex.bolts.collection.Cf;
import ru.yandex.bolts.collection.Tuple2;

public class Tuple2Serializer extends StdSerializer<Tuple2<Object, Object>> {

    protected Tuple2Serializer(JavaType type) {
        super(type);
    }

    @Override
    public void serialize(Tuple2<Object, Object> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        provider.defaultSerializeValue(Cf.list(value._1, value._2), jgen);
    }
}
