package com.voronina.converters;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Converter {

    private final Map<ConvertingKey, ObjectConverter<?, ?>> mapperMap;

    public Converter(List<ObjectConverter<?,?>> mappers) {
        var tmpMappers = new HashMap<ConvertingKey, ObjectConverter<?,?>>();
        for (var mapper : mappers) {
            tmpMappers.put(new ConvertingKey(mapper.getInClass(), mapper.getOutClass()), mapper);
        }
        this.mapperMap = Collections.unmodifiableMap(tmpMappers);
    }

    @SuppressWarnings("unchecked")
    public <IN, OUT> OUT convert(IN obj, Class<OUT> destClass) {
        if (obj == null) {
            return null;
        }
        var mapper = (ObjectConverter<IN, OUT>)mapperMap.get(new ConvertingKey(obj.getClass(), destClass));
        if (mapper == null) {
            throw new RuntimeException("Unsupported mapper. " + obj.getClass() + " -> " + destClass);
        }
        return mapper.convert(obj);
    }

    @SuppressWarnings("unchecked")
    public <IN, OUT> List<OUT> convertList(List<IN> objList, Class<OUT> destClass) {
        if (objList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        var mapper = (ObjectConverter<IN, OUT>)mapperMap.get(new ConvertingKey(objList.get(0).getClass(), destClass));
        if (mapper == null) {
            throw new RuntimeException("Unsupported mapper. " + objList.getClass() + " -> " + destClass);
        }
        return mapper.convertList(objList);
    }
}
