package ru.yandex.jackson.datatype.bolts.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;

import ru.yandex.bolts.collection.CollectionF;
import ru.yandex.bolts.collection.ListF;
import ru.yandex.bolts.collection.MapF;
import ru.yandex.bolts.collection.Option;
import ru.yandex.bolts.collection.SetF;
import ru.yandex.bolts.collection.Tuple2;

public class BoltsDeserializers extends Deserializers.Base {

    @Override
    public JsonDeserializer<?> findCollectionDeserializer(CollectionType type, DeserializationConfig config, BeanDescription beanDesc,
            TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException
    {
        Class<?> raw = type.getRawClass();
        if (CollectionF.class.isAssignableFrom(raw)) {
            if (Option.class.isAssignableFrom(raw)) {
                return new OptionDeserializer(type, elementTypeDeserializer, elementDeserializer);
            }
            if (ListF.class.isAssignableFrom(raw)) {
                return new ListFDeserializer(type, elementTypeDeserializer, elementDeserializer);
            }
            if (SetF.class.isAssignableFrom(raw)) {
                return new SetFDeserializer(type, elementTypeDeserializer, elementDeserializer);
            }
            return new ListFDeserializer(type, elementTypeDeserializer, elementDeserializer);
        }
        return null;
    }

    @Override
    public JsonDeserializer<?> findMapDeserializer(MapType type, DeserializationConfig config, BeanDescription beanDesc,
            KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer)
            throws JsonMappingException
    {
        if (MapF.class.isAssignableFrom(type.getRawClass())) {
            return new MapFDeserializer(type, keyDeserializer, elementTypeDeserializer, elementDeserializer);
        }
        return null;
    }

    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config,
            BeanDescription beanDesc) throws JsonMappingException
    {
        if (Tuple2.class.isAssignableFrom(type.getRawClass())) {
            SimpleType simple = (SimpleType) type;
            return new Tuple2Deserializer(simple.containedType(0), simple.containedType(1));
        }
        return null;
    }
}
