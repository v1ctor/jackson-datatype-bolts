package ru.yandex.jackson.datatype.bolts.deser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;

import ru.yandex.bolts.collection.CollectionF;
import ru.yandex.bolts.collection.Option;

public final class OptionDeserializer extends CollectionDeserializer<Option<Object>> {

    protected OptionDeserializer(CollectionType type, TypeDeserializer typeDeser, JsonDeserializer<?> deser) {
        super(type, typeDeser, deser);
    }

    @Override
    public CollectionDeserializer<Option<Object>> withResolved(TypeDeserializer typeDeser, JsonDeserializer<?> valueDeser) {
        return new OptionDeserializer(containerType, typeDeser, valueDeser);
    }

    @Override
    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jp, ctxt);
    }

    @Override
    public Option<Object> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        Object value;
        if (typeDeserializerForValue == null) {
            value = valueDeserializer.deserialize(jp, ctxt);
        } else {
            value = valueDeserializer.deserializeWithType(jp, ctxt, typeDeserializerForValue);
        }
        return Option.notNull(value).uncheckedCast();
    }

    @Override
    public Option<Object> getNullValue() {
        return Option.none();
    }

    @Override
    public Option<Object> getEmptyValue() {
        return Option.none();
    }

    @Override
    protected CollectionF<Object> newInstance() {
        return Option.none();
    }

}
