package ru.yandex.jackson.datatype.bolts;

import com.fasterxml.jackson.databind.module.SimpleModule;

import ru.yandex.jackson.datatype.bolts.deser.BoltsDeserializers;
import ru.yandex.jackson.datatype.bolts.ser.BoltsBeanSerializerModifier;
import ru.yandex.jackson.datatype.bolts.ser.BoltsSerializers;

public class BoltsModule extends SimpleModule {

    public BoltsModule() {
        super(BoltsModule.class.getName());
    }

    @Override
    public void setupModule(SetupContext context) {
        context.addSerializers(new BoltsSerializers());
        context.addDeserializers(new BoltsDeserializers());
        context.addBeanSerializerModifier(new BoltsBeanSerializerModifier());
    }
}
