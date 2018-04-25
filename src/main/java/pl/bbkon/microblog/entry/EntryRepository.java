package pl.bbkon.microblog.entry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Integer> {
    Optional<Entry> findByAuthor();
}
