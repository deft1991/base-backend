package com.courier.communication.mapper;

public interface Mapper<S, T> {

    S convertToDto(T entity);

    T convertToEntity(S entity);
}
