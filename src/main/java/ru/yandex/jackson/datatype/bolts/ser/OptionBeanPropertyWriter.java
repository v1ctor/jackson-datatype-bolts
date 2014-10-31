package ru.yandex.jackson.datatype.bolts.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

import ru.yandex.bolts.collection.Option;

public class OptionBeanPropertyWriter extends BeanPropertyWriter {

    protected OptionBeanPropertyWriter(BeanPropertyWriter base) {
        super(base);
    }

    @Override
    public void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider prov) throws Exception {
        if ((get(bean) == null || Option.none().equals(get(bean))) && _nullSerializer == null) {
            return;
        }
        super.serializeAsField(bean, jgen, prov);
    }

    @Override
    public JsonSerializer<Object> getSerializer() {
        return super.getSerializer();
    }
}
