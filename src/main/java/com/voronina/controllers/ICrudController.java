package com.voronina.controllers;

import java.util.List;

public interface ICrudController<T> {

    public T read(Integer id);

    public List<T> readAll();

    public void create(T t);

    public void update(T t, T newT);

    public void delete(T t);
}
