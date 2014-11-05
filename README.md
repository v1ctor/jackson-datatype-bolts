[Jackson](http://jackson.codehaus.org) module to support JSON serializtion and deserializtion of [Yandex Bolts](https://bitbucket.org/stepancheg/bolts) collection types.

## Usage
### Maven dependency
```xml
<dependency>
  <groupId>ru.yandex</groupId>
  <artifactId>jackson-datatype-bolts</artifactId>
  <version>0.1</version>
</dependency>
```

### Registering module
Like all standard Jackson modules (libraries that implement Module interface), registration is done as follows:

```java
ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new BoltsModule());
```
after which functionality is available for all normal Jackson operations.
