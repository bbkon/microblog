package pl.bbkon.microblog.entry;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EntryService {
    private EntryRepository entryRepository;

    public List<Entry> findAll() {
        return entryRepository.findAll();
    }
}
