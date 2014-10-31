package ru.yandex.jackson.datatype.bolts.deser;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;

import ru.yandex.bolts.collection.Cf;
import ru.yandex.bolts.collection.CollectionF;
import ru.yandex.bolts.collection.SetF;

public class SetFDeserializer extends CollectionDeserializer<SetF<Object>> {

    public SetFDeserializer(CollectionType type, TypeDeserializer typeDeser, JsonDeserializer<?> deser) {
        super(type, typeDeser, deser);
    }

    @Override
    public SetFDeserializer withResolved(TypeDeserializer typeDeser, JsonDeserializer<?> valueDeser) {
        return new SetFDeserializer(containerType, typeDeser, valueDeser);
    }

    @Override
    protected CollectionF<Object> newInstance() {
        return Cf.hashSet();
    }

    @Override
    public SetF<Object> getNullValue() {
        return Cf.set();
    }

    @Override
    public SetF<Object> getEmptyValue() {
        return Cf.set();
    }

}
