package ibf2021.workshop14.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import ibf2021.workshop14.model.Contact;
import ibf2021.workshop14.repositories.ContactRepo;

@Service
public class ContactsSvc implements ContactRepo {

    private static final Logger logger = Logger.getLogger(ContactsSvc.class.getName());

    @Autowired
    @Qualifier("AppConfig")
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(final Contact contact) {
        redisTemplate.opsForValue().set(contact.getHexID(), contact);
        logger.info(contact.getHexID());
    }

    @Override
    public Contact findById(String contactId) {
        Contact results = (Contact) redisTemplate.opsForValue().get(contactId);
        logger.info("Check if results tally via its email ->" + results.getEmail());
        logger.info("Check if results tally via its name ->" + results.getName());
        logger.info("Check if results tally via its email ->" + results.getHexID());
        logger.info("Check if results tally via its number ->" + results.getPhone_number());
        return results;
    }

}
