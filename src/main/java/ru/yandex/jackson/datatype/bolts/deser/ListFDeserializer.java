package ru.yandex.jackson.datatype.bolts.deser;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;

import ru.yandex.bolts.collection.Cf;
import ru.yandex.bolts.collection.CollectionF;
import ru.yandex.bolts.collection.ListF;

public class ListFDeserializer extends CollectionDeserializer<ListF<Object>> {

    public ListFDeserializer(CollectionType type, TypeDeserializer typeDeser, JsonDeserializer<?> deser) {
        super(type, typeDeser, deser);
    }

    @Override
    public ListFDeserializer withResolved(TypeDeserializer typeDeser, JsonDeserializer<?> valueDeser) {
        return new ListFDeserializer(containerType, typeDeser, valueDeser);
    }

    @Override
    protected CollectionF<Object> newInstance() {
        return Cf.arrayList();
    }

    @Override
    public ListF<Object> getNullValue() {
        return Cf.list();
    }

    @Override
    public ListF<Object> getEmptyValue() {
        return Cf.list();
    }

}
