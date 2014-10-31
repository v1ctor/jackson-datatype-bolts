package ru.yandex.jackson.datatype.bolts.deser;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.MapType;

import ru.yandex.bolts.collection.Cf;
import ru.yandex.bolts.collection.MapF;

public class MapFDeserializer extends MapDeserializer<MapF<Object, Object>> {

    public MapFDeserializer(MapType type, KeyDeserializer keyDeser, TypeDeserializer typeDeser, JsonDeserializer<?> deser) {
        super(type, keyDeser, typeDeser, deser);
    }

    @Override
    public MapFDeserializer withResolved(KeyDeserializer keyDeser, TypeDeserializer typeDeser, JsonDeserializer<?> valueDeser) {
        return new MapFDeserializer(mapType, keyDeser, typeDeser, valueDeser);
    }

    @Override
    protected MapF<Object, Object> newInstance() {
        return Cf.hashMap();
    }

    @Override
    public MapF<Object, Object> getNullValue() {
        return Cf.map();
    }

    @Override
    public MapF<Object, Object> getEmptyValue() {
        return Cf.map();
    }

}
