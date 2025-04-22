package com.zoo.domain.enclosure.repository;

import com.zoo.domain.enclosure.model.Enclosure;
import com.zoo.domain.enclosure.model.EnclosureId;
import java.util.List;
import java.util.Optional;

public interface EnclosureRepository {
    Optional<Enclosure> findById(EnclosureId id);
    List<Enclosure> findAll();
    void save(Enclosure enclosure);
    void deleteById(EnclosureId id);
    long count();
    long countFree();
}
