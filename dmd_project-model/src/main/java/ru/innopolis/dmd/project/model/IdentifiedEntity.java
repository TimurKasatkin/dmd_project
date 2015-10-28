package ru.innopolis.dmd.project.model;

import java.io.Serializable;

/**
 * Created by timur on 15.10.15.
 */
public interface IdentifiedEntity extends Serializable {

    Serializable getId();

}
