package ru.yandex.jackson.datatype.bolts.ser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.CollectionType;

import ru.yandex.bolts.collection.Option;
import ru.yandex.bolts.collection.Tuple2;

public class BoltsSerializers extends Serializers.Base {

    @Override
    public JsonSerializer<?> findCollectionSerializer(SerializationConfig config, CollectionType type,
            BeanDescription beanDesc, TypeSerializer elementTypeSerializer,
            JsonSerializer<Object> elementValueSerializer)
    {

        if (Option.class.isAssignableFrom(type.getRawClass())) {
            return new OptionSerializer(type);
        }

        return super.findCollectionSerializer(config, type, beanDesc, elementTypeSerializer, elementValueSerializer);
    }

    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
        if (Tuple2.class.isAssignableFrom(type.getRawClass())) {
            return new Tuple2Serializer(type);
        }
        return super.findSerializer(config, type, beanDesc);
    }
}
