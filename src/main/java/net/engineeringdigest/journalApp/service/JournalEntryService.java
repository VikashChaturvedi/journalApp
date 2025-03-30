package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

        @Transactional
        public void saveEntry(JournalEntry journalEntry, String userName){
            try {
                User user=userService.findByUserName(userName);
                journalEntry.setDate(LocalDateTime.now());
                JournalEntry saved = journalEntryRepository.save(journalEntry);
                user.getJournalEntries().add(saved);
                userService.saveEntry(user);
            } catch (Exception e) {
                log.error("Error ,",e);
                throw new RuntimeException("An error occurred while saving the entry",e);
            }
        }

        // dont confuse- below saveEntry function is used when we are updating a journal entry whereas above one is used when new entry is added
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

        public List<JournalEntry> getAll(){
            return journalEntryRepository.findAll();
        }

        public Optional<JournalEntry> searchById(ObjectId id) {
           return journalEntryRepository.findById(id);
        }

    //below we have used transactional because we are performing 2 operations dependent on each other
        @Transactional
        public boolean deleteById(ObjectId id, String userName){
            boolean removed = false;
            try {
                User user =userService.findByUserName(userName);
                //removeIf returns boolean
                removed= user.getJournalEntries().removeIf(x-> x.getId().equals(id));
                if(removed){
                    userService.saveEntry(user);
                    journalEntryRepository.deleteById(id);
                }
            }catch(Exception e){
                    System.out.println(e);
                    throw new RuntimeException("An error occured while deleting the entry.", e);
            }
            return removed;

        }
}



// controller----->servie----->repopsitory