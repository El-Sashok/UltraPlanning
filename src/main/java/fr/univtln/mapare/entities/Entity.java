package fr.univtln.mapare.entities;

import java.util.List;

/**
 * An interface to force an entity used in DAOs to have a long id.
 */
public interface Entity {
    long getId();

    void setId(long id);
}
