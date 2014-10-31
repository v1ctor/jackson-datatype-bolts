package ru.yandex.jackson.datatype.bolts.deser;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

import ru.yandex.bolts.collection.Tuple2;

public class Tuple2Deserializer extends StdDeserializer<Tuple2<Object, Object>> implements ContextualDeserializer {

    private final JavaType firstType;
    private final JavaType secondType;
    private final JsonDeserializer<?> firstDeserializer;
    private final JsonDeserializer<?> secondDeserializer;
    private final TypeDeserializer firstTypeDeserializer;
    private final TypeDeserializer secondTypeDeserializer;

    public Tuple2Deserializer(JavaType firstType, JavaType secondType,
            JsonDeserializer<?> firstDeserializer, JsonDeserializer<?> secondDeserializer,
            TypeDeserializer firstTypeDeserializer, TypeDeserializer secondTypeDeserializer)
    {
        super(Tuple2.class);
        this.firstType = firstType;
        this.secondType = secondType;
        this.firstDeserializer = firstDeserializer;
        this.secondDeserializer = secondDeserializer;
        this.firstTypeDeserializer = firstTypeDeserializer;
        this.secondTypeDeserializer = secondTypeDeserializer;
    }

    public Tuple2Deserializer(JavaType firstType, JavaType secondType) {
        this(firstType, secondType, null, null, null, null);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext context, BeanProperty property)
            throws JsonMappingException
    {
        JsonDeserializer<?> firstDeserializer = this.firstDeserializer;
        JsonDeserializer<?> secondDeserializer = this.secondDeserializer;
        TypeDeserializer firstTypeDeserializer = this.firstTypeDeserializer;
        TypeDeserializer secondTypeDeserializer = this.secondTypeDeserializer;

        if (firstDeserializer == null) {
            firstDeserializer = context.findContextualValueDeserializer(firstType, property);
        }

        if (secondDeserializer == null) {
            secondDeserializer = context.findContextualValueDeserializer(secondType, property);
        }
        if (firstTypeDeserializer != null) {
            firstTypeDeserializer = firstTypeDeserializer.forProperty(property);
        }
        if (secondTypeDeserializer != null) {
            secondTypeDeserializer = secondTypeDeserializer.forProperty(property);
        }
        if (firstDeserializer != this.firstDeserializer
                || secondDeserializer != this.secondDeserializer
                || firstTypeDeserializer != this.firstTypeDeserializer
                || secondTypeDeserializer != this.secondTypeDeserializer)
        {
            return new Tuple2Deserializer(firstType, secondType, firstDeserializer, secondDeserializer,
                    firstTypeDeserializer, secondTypeDeserializer);
        }
        return this;
    }

    @Override
    public Object deserializeWithType(JsonParser parser, DeserializationContext context,
            TypeDeserializer typeDeserializer) throws IOException
    {
        return typeDeserializer.deserializeTypedFromArray(parser, context);
    }

    @Override
    public Tuple2<Object, Object> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        parser.nextToken();

        Object first = deserializeElement(firstDeserializer, firstTypeDeserializer, parser, context);
        parser.nextToken();

        Object second = deserializeElement(secondDeserializer, secondTypeDeserializer, parser, context);
        parser.nextToken();

        if (parser.getCurrentToken() != JsonToken.END_ARRAY) {
            throw context.wrongTokenException(parser, JsonToken.END_ARRAY,
                    "Tuple2 can only be deserialized from Object[2]");
        }
        return Tuple2.tuple(first, second);
    }

    private Object deserializeElement(JsonDeserializer<?> deserializer, TypeDeserializer typeDeserializer,
            JsonParser parser, DeserializationContext context) throws IOException
    {
        if (parser.getCurrentToken() == JsonToken.VALUE_NULL) {
            return deserializer.getNullValue();
        } else if (typeDeserializer == null) {
            return deserializer.deserialize(parser, context);
        }
        return deserializer.deserializeWithType(parser, context, typeDeserializer);
    }

}
