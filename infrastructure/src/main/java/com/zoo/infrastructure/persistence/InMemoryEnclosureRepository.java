package com.zoo.infrastructure.persistence;

import com.zoo.domain.enclosure.model.Enclosure;
import com.zoo.domain.enclosure.model.EnclosureId;
import com.zoo.domain.enclosure.repository.EnclosureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InMemoryEnclosureRepository implements EnclosureRepository {

    private final InMemoryDatabase database;

    @Override
    public Optional<Enclosure> findById(EnclosureId id) {
        return database.findEnclosureById(id).map(Enclosure::new);
    }

    @Override
    public List<Enclosure> findAll() {
        return database.findAllEnclosures().stream()
                .map(Enclosure::new)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Enclosure enclosure) {
        database.saveEnclosure(new Enclosure(enclosure));
    }

    @Override
    public void deleteById(EnclosureId id) {
        database.deleteEnclosureById(id);
    }

     @Override
    public long count() {
        return database.countEnclosures();
    }

    @Override
    public long countFree() {
        return database.findAllEnclosures().stream()
                .filter(e -> !e.isFull())
                .count();
    }
}
