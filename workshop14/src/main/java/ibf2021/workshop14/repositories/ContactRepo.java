package ibf2021.workshop14.repositories;

import org.springframework.stereotype.Repository;

import ibf2021.workshop14.model.Contact;

@Repository
public interface ContactRepo {

    public void save(final Contact contact);

    public Contact findById(final String contactId);
}
