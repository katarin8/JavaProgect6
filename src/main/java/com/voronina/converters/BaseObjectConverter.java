package com.voronina.converters;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseObjectConverter<IN, OUT> implements ObjectConverter<IN, OUT> {


    @Override
    public List<OUT> convertList(List<IN> objList) {
        return objList.stream().map(this::convert).collect(Collectors.toList());
    }
}
