package ru.yandex.jackson.datatype.bolts.ser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.Test;

import ru.yandex.bolts.collection.Option;
import ru.yandex.bolts.collection.Tuple2;
import ru.yandex.jackson.datatype.bolts.BoltsTestBase;

public class Tuple2SerializerTest extends BoltsTestBase {

    @Test
    public void tuple2SerializeTest() throws JsonProcessingException {
        ObjectWriter writer = mapper().writer();
        String result = writer.writeValueAsString(Tuple2.tuple(1, 2));
        Assert.assertEquals("[1,2]", result);
    }

    @Test
    public void tuple2OptionSerializeTest() throws JsonProcessingException {
        ObjectWriter writer = mapper().writer();
        String result = writer.writeValueAsString(Tuple2.tuple(Option.some(1), Option.some(2)));
        Assert.assertEquals("[1,2]", result);
    }
}
