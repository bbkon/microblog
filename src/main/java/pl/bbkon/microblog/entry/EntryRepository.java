package pl.bbkon.microblog.entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bbkon.microblog.user.User;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Integer> {

    Page<Entry> findAllByOrderByCreationDateDesc(Pageable pageable);

    List<Entry> findAllByAuthorOrderByCreationDateDesc(User user);

    Integer countAllByAuthorUsername(String username);

}
