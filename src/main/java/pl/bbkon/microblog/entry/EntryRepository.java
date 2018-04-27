package pl.bbkon.microblog.entry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bbkon.microblog.user.User;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Integer> {

    List<Entry> findAllByOrderByCreationDateDesc();

    List<Entry> findAllByAuthorOrderByCreationDateDesc(User user);
}
