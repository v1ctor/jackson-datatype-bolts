package ru.yandex.jackson.datatype.bolts.deser;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.Assert;
import org.junit.Test;

import ru.yandex.bolts.collection.Option;
import ru.yandex.jackson.datatype.bolts.BoltsTestBase;
import ru.yandex.jackson.datatype.bolts.model.OptionBean;

public class OptionDeserializerTest extends BoltsTestBase {

    @Test
    public void optionEmptyDeserializeTest() throws IOException {
        ObjectReader reader = mapper().reader(Option.class);
        Option<Integer> result = reader.readValue("null");
        Assert.assertEquals(result, Option.<Integer>none());
    }

    @Test
    public void optionDeserializeTest() throws IOException {
        ObjectReader reader = mapper().reader(Option.class);
        Option<Integer> result = reader.readValue("1");
        Assert.assertEquals(result, Option.some(1));
    }

    @Test
    public void optionEmptyBeanDeserializeTest() throws IOException {
        ObjectReader reader = mapper().reader(OptionBean.class);
        OptionBean<String> result = reader.readValue("{}");
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getValue());
        Assert.assertEquals(result.getValue(), Option.<String>none());
    }

    @Test
    public void optionBeanDeserializeTest() throws IOException {
        ObjectReader reader = mapper().reader(OptionBean.class);
        OptionBean<Integer> result = reader.readValue("{\"value\":1}");
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getValue());
        Assert.assertEquals(result.getValue(), Option.some(1));
    }
}
