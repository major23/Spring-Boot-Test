package com.nikos.dto;

import java.io.Serializable;

public interface BaseManagementDTO<E extends Object, T extends Object> extends Serializable {

	public T getId();

	public BaseManagementDTO<E, T> fromEntity(E entity);

	public E toEntity();

}
