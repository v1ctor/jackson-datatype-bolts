package ru.yandex.jackson.datatype.bolts.ser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.Test;

import ru.yandex.bolts.collection.Option;
import ru.yandex.jackson.datatype.bolts.BoltsTestBase;
import ru.yandex.jackson.datatype.bolts.model.OptionBean;

public class OptionSerializerTest extends BoltsTestBase {

    @Test
    public void optionSomeSerializeTest() throws JsonProcessingException {
        ObjectWriter writer = mapper().writer();
        String result = writer.writeValueAsString(Option.some(1));
        Assert.assertEquals("1", result);
    }

    @Test
    public void optionNoneSerializeTest() throws JsonProcessingException {
        ObjectWriter writer = mapper().writer();
        String result = writer.writeValueAsString(Option.none());
        Assert.assertEquals("null", result);
    }

    @Test
    public void optionBeanSerializeTest() throws JsonProcessingException {
        ObjectWriter writer = mapper().writer();
        String result = writer.writeValueAsString(new OptionBean<String>(Option.some("abc")));
        Assert.assertEquals("{\"value\":\"abc\"}", result);
    }

    @Test
    public void optionEmptyBeanSerializeTest() throws JsonProcessingException {
        ObjectMapper mapper = mapper();
        mapper = mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        ObjectWriter writer = mapper.writer();
        String result = writer.writeValueAsString(new OptionBean<String>(Option.<String>none()));
        Assert.assertEquals("{}", result);
    }

    @Test
    public void optionNullBeanEmptySerializeTest() throws JsonProcessingException {
        ObjectMapper mapper = mapper();
        mapper = mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        ObjectWriter writer = mapper.writer();
        String result = writer.writeValueAsString(new OptionBean<String>(null));
        Assert.assertEquals("{}", result);
    }
}
