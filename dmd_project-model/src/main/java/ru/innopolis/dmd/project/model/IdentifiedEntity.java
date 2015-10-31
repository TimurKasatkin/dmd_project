package ru.innopolis.dmd.project.model;

import java.io.Serializable;

/**
 * Created by timur on 15.10.15.
 */
public interface IdentifiedEntity<T extends Serializable> extends Serializable {

    T getId();

    void setId(T id);

}
