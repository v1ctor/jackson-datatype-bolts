package ru.yandex.jackson.datatype.bolts.deser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;

import ru.yandex.bolts.collection.CollectionF;

public abstract class CollectionDeserializer<T> extends StdDeserializer<T> implements ContextualDeserializer {

    protected final CollectionType containerType;
    protected final JsonDeserializer<?> valueDeserializer;
    protected final TypeDeserializer typeDeserializerForValue;

    protected CollectionDeserializer(CollectionType type, TypeDeserializer typeDeser, JsonDeserializer<?> deser) {
        super(type);
        containerType = type;
        typeDeserializerForValue = typeDeser;
        valueDeserializer = deser;
    }

    public abstract CollectionDeserializer<T> withResolved(TypeDeserializer typeDeser, JsonDeserializer<?> valueDeser);

    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        JsonDeserializer<?> deser = valueDeserializer;
        TypeDeserializer typeDeser = typeDeserializerForValue;
        if (deser == null) {
            deser = ctxt.findContextualValueDeserializer(containerType.getContentType(), property);
        }
        if (typeDeser != null) {
            typeDeser = typeDeser.forProperty(property);
        }
        if (deser == valueDeserializer && typeDeser == typeDeserializerForValue) {
            return this;
        }
        return withResolved(typeDeser, deser);
    }

    @Override
    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jp, ctxt);
    }

    @Override
    public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.getCurrentToken() != JsonToken.START_ARRAY) {
            if (ctxt.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
                return _deserializeSingle(jp, ctxt);
            }
            throw ctxt.mappingException(containerType.getRawClass());
        }
        return _deserializeContents(jp, ctxt);
    }

    protected abstract CollectionF<Object> newInstance();

    @SuppressWarnings("unchecked")
    protected T _deserializeContents(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonDeserializer<?> valueDes = valueDeserializer;
        JsonToken t;
        final TypeDeserializer typeDeser = typeDeserializerForValue;
        CollectionF<Object> collection = newInstance();

        while ((t = jp.nextToken()) != JsonToken.END_ARRAY) {
            Object value;

            if (t == JsonToken.VALUE_NULL) {
                value = valueDes.getNullValue();
            } else if (typeDeser == null) {
                value = valueDes.deserialize(jp, ctxt);
            } else {
                value = valueDes.deserializeWithType(jp, ctxt, typeDeser);
            }
            collection.add(value);
        }
        return (T) collection.unmodifiable();
    }

    @SuppressWarnings("unchecked")
    protected T _deserializeSingle(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonDeserializer<?> valueDes = valueDeserializer;
        final TypeDeserializer typeDeser = typeDeserializerForValue;
        CollectionF<Object> collection = newInstance();

        Object value;

        if (jp.getCurrentToken() == JsonToken.VALUE_NULL) {
            value = valueDes.getNullValue();
        } else if (typeDeser == null) {
            value = valueDes.deserialize(jp, ctxt);
        } else {
            value = valueDes.deserializeWithType(jp, ctxt, typeDeser);
        }
        collection.add(value);
        return (T) collection.unmodifiable();
    }

}
