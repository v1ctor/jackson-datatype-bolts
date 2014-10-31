package ru.yandex.jackson.datatype.bolts.ser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.Test;

import ru.yandex.bolts.collection.Cf;
import ru.yandex.jackson.datatype.bolts.BoltsTestBase;

public class ListFSerializeTest extends BoltsTestBase {

    @Test
    public void listFEmptySerializeTest() throws JsonProcessingException {
        ObjectWriter writer = mapper().writer();
        String result = writer.writeValueAsString(Cf.list());
        Assert.assertEquals("[]", result);
    }

    @Test
    public void listFSerializeTest() throws JsonProcessingException {
        ObjectWriter writer = mapper().writer();
        String result = writer.writeValueAsString(Cf.list(1, 2, 3, 4));
        Assert.assertEquals("[1,2,3,4]", result);
    }

}
