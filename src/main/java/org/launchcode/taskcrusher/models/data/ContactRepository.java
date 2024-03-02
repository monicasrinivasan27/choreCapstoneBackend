package org.launchcode.taskcrusher.models.data;

import org.launchcode.taskcrusher.models.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {
}
