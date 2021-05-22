package com.voronina.converters;

import java.util.List;

public interface ObjectConverter<IN, OUT> {

    OUT convert(IN obj);

    List<OUT> convertList(List<IN> objList);

    Class<IN> getInClass();

    Class<OUT> getOutClass();
}
