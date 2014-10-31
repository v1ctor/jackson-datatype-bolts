package ru.yandex.jackson.datatype.bolts.ser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.Test;

import ru.yandex.bolts.collection.Cf;
import ru.yandex.jackson.datatype.bolts.BoltsTestBase;

public class MapFSerializeTest extends BoltsTestBase {

    @Test
    public void mapFEmptySerializeTest() throws JsonProcessingException {
        ObjectWriter writer = mapper().writer();
        String result = writer.writeValueAsString(Cf.map());
        Assert.assertEquals("{}", result);
    }

    @Test
    public void mapFSerializeTest() throws JsonProcessingException {
        ObjectWriter writer = mapper().writer();
        String result = writer.writeValueAsString(Cf.map(1, 2, 3, 4));
        Assert.assertEquals("{\"1\":2,\"3\":4}", result);
    }
}
