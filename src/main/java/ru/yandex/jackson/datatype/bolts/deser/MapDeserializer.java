package ru.yandex.jackson.datatype.bolts.deser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.MapType;

import ru.yandex.bolts.collection.MapF;

public abstract class MapDeserializer<T> extends JsonDeserializer<T> implements ContextualDeserializer {

    protected final MapType mapType;
    protected KeyDeserializer keyDeserializer;
    protected JsonDeserializer<?> valueDeserializer;
    protected final TypeDeserializer typeDeserializerForValue;

    protected MapDeserializer(MapType type, KeyDeserializer keyDeser, TypeDeserializer typeDeser,
            JsonDeserializer<?> deser)
    {
        mapType = type;
        keyDeserializer = keyDeser;
        typeDeserializerForValue = typeDeser;
        valueDeserializer = deser;
    }

    public abstract MapDeserializer<T> withResolved(KeyDeserializer keyDeser, TypeDeserializer typeDeser,
            JsonDeserializer<?> valueDeser);

    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        KeyDeserializer keyDeser = keyDeserializer;
        JsonDeserializer<?> deser = valueDeserializer;
        TypeDeserializer typeDeser = typeDeserializerForValue;

        if ((keyDeser != null) && (deser != null) && (typeDeser == null)) {
            return this;
        }
        if (keyDeser == null) {
            keyDeser = ctxt.findKeyDeserializer(mapType.getKeyType(), property);
        }
        if (deser == null) {
            deser = ctxt.findContextualValueDeserializer(mapType.getContentType(), property);
        }
        if (typeDeser != null) {
            typeDeser = typeDeser.forProperty(property);
        }
        return withResolved(keyDeser, typeDeser, deser);
    }

    @Override
    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jp, ctxt);
    }

    @Override
    public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
            if (t != JsonToken.FIELD_NAME && t != JsonToken.END_OBJECT) {
                throw ctxt.mappingException(mapType.getRawClass());
            }
        } else if (t != JsonToken.FIELD_NAME) {
            throw ctxt.mappingException(mapType.getRawClass());
        }
        return _deserializeEntries(jp, ctxt);
    }

    protected abstract MapF<Object, Object> newInstance();

    @SuppressWarnings("unchecked")
    protected T _deserializeEntries(JsonParser jp, DeserializationContext ctxt) throws IOException {
        final KeyDeserializer keyDes = keyDeserializer;
        final JsonDeserializer<?> valueDes = valueDeserializer;
        final TypeDeserializer typeDeser = typeDeserializerForValue;

        MapF<Object, Object> map = newInstance();

        for (; jp.getCurrentToken() == JsonToken.FIELD_NAME; jp.nextToken()) {
            String fieldName = jp.getCurrentName();
            Object key = (keyDes == null) ? fieldName : keyDes.deserializeKey(fieldName, ctxt);
            JsonToken t = jp.nextToken();
            Object value;
            if (t == JsonToken.VALUE_NULL) {
                value = null;
            } else if (typeDeser == null) {
                value = valueDes.deserialize(jp, ctxt);
            } else {
                value = valueDes.deserializeWithType(jp, ctxt, typeDeser);
            }
            map.put(key, value);
        }
        return (T) map.unmodifiable();
    }

}
