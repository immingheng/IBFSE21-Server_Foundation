package ibf2021.workshop14.controllers;

import org.springframework.beans.factory.annotation.Autowired;
// Spring libraries
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2021.workshop14.model.Contact;
import ibf2021.workshop14.services.ContactsSvc;

import java.util.ArrayList;
import java.util.List;

// Import 3rd party logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Task 2 - Write a HTML form that collects the following information: name, email and phone number. This information should be sent to /contact resources with the HTTP POST method.

Task 3 - The controller mapped to /contact process the data according to the following steps:
1. Randomly generate an 8 character long hex string (abcd1234); the hex string will be used as the id for the data
2. Create a file with the above generated hex string in the directory specified by --dataDir option.
3. Write the data into the file (abcd1234) as text (UTF-8) one field per line.
Once the controller has completed the above steps, controller should return the 'created' HTTP code with an appropriate message (201)

All methods that manages the data directory should be handle by a single class and not in the controller - call it Contacts. Write this class and do Task 5 before continuing task 3.

Task 4 - Write a controller to handle a GET to /contact/<id> where id is a 8 char long hex digit. The controller will look into the data directory for a file with the corresponding <id> in the resource. Display the contents of the file in a HTML document. If the <id> does not exists in the data directory, then return a not found status code (404) with an appropriate message.

Task 5 - Write test for Contacts Class.
*/

@Controller
@RequestMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
public class ContactController {
    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    ContactsSvc contactService;

    // Read from contactForm
    @GetMapping
    public String showGenForm(Model model) {
        Contact contact = new Contact();
        model.addAttribute("contact", contact);
        return "contactForm";
    }

    // Post form (Create)
    @PostMapping("/contact")
    public String saveContact(@ModelAttribute Contact contact, Model model) {
        logger.info("Name -> " + contact.getName());
        logger.info("Email ->" + contact.getEmail());
        logger.info("Phone Number -> " + contact.getPhone_number());

        Contact saveToRedis = new Contact(
                contact.getName(),
                contact.getEmail(),
                contact.getPhone_number(),
                contact.getHexID());
        contactService.save(saveToRedis);

        List<String> hexIDList = new ArrayList<>();
        hexIDList.add(contact.getHexID());

        model.addAttribute("hexIDList", hexIDList);
        model.addAttribute("redisContact", saveToRedis);
        return "resultsPage";
    }

    @GetMapping("/contact/{id}")
    public String retrieveContact(@PathVariable(value = "id") String id, Model model) {
        logger.info("HexID -> " + id);
        Contact redisToClientContact = contactService.findById(id);
        logger.info("getID -> " + redisToClientContact.getHexID());
        logger.info("name -> " + redisToClientContact.getName());
        logger.info("email ->" + redisToClientContact.getEmail());
        logger.info("phoneNo. ->" + redisToClientContact.getPhone_number());

        model.addAttribute("redisRetrieve", redisToClientContact);
        return "searchID";
    }

}
