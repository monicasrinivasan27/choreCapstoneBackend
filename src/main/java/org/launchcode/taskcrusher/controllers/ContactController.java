package org.launchcode.taskcrusher.controllers;

import org.launchcode.taskcrusher.models.Contact;
import org.launchcode.taskcrusher.models.data.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/contact")
    public Contact processContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }
}
