package ibf2021.workshop3.controllers;

// Spring libraries
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

// Model object that will be passed around in MVC layer
import ibf2021.workshop3.model.Contact;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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
public class ContactController {
    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    // Initialise dataDir based on argument input from controller
    @Value("${dataDir}")
    private String dataDir;

    // Read from contactForm
    @GetMapping("/contact")
    public String showGenForm(Model model) {
        Contact contact = new Contact();
        model.addAttribute("contact", contact);
        return "contactForm";
    }

    // Post form (Create)
    @PostMapping("/contact")
    public ResponseEntity<String> generateContact(@ModelAttribute Contact contact) {
        // Get relevant parameters from the form
        String name = contact.getName();
        logger.info("name -->" + name);
        String email = contact.getEmail();
        logger.info("email -->" + email);
        int phoneNumber = contact.getPhone_number();
        logger.info("phoneNumber --> " + phoneNumber);
        String HexID = contact.getHexID();
        logger.info("HexID --> " + HexID);

        String path = dataDir + "\\" + HexID;
        logger.info("dataDir --> " + dataDir);
        File hexFile = new File(path);
        logger.info("path --> " + path);
        try {
            // try to create the new file based on the abstract pathname
            hexFile.createNewFile();
            try (FileOutputStream fos = new FileOutputStream(hexFile);
                    OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
                osw.write(String.format(name + "\n"));
                osw.write(String.format(email + "\n"));
                osw.write(String.format(phoneNumber + "\n"));
                osw.write(String.format(HexID + "\n"));
                osw.flush();
                fos.close();

                // Upon completion of file creation, controller should print HTTP Status code
                // 201 (Created) to indicate file creation success with the appropriate message.
                // The status code and the message should then be printed onto the html
                // resultsPage

                // Kenneth added a new Contact instance to separate what was tabulated on the
                // form and what was sent to the back-end
                // This is known as object isolation which allows the backend log to contain
                // sensitive variable called from other sources without rendering such
                // information onto the form.

            } catch (FileNotFoundException FNFe) {
                FNFe.printStackTrace();

            }
        } catch (IOException IOe) {
            IOe.printStackTrace();

        }
        return new ResponseEntity<>("HTTP 201: Your details have been added into the address book!",
                HttpStatus.CREATED);
    }

    @GetMapping("/contact/{id}")
    public String retrieveContact(@PathVariable String id, Model model) {
        // Check if file exists within local directory
        File localID = new File(dataDir + "\\" + id);
        List<String> toPrint = new ArrayList<>();
        if (localID.exists()) {
            try (FileInputStream fis = new FileInputStream(localID);
                    InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                    BufferedReader br = new BufferedReader(isr)) {
                // if file exists, open up the file and read line by line.
                String line = "";
                while ((line = br.readLine()) != null) {
                    toPrint.add(line.strip());
                }

                Contact contactFromServer = new Contact(toPrint.get(0), toPrint.get(1),
                        Integer.parseInt(toPrint.get(2)),
                        toPrint.get(3));
                // contactFromServer.setName(toPrint.get(0));
                logger.info("name --> " + toPrint.get(0));
                // contactFromServer.setEmail(toPrint.get(1));
                logger.info("email -->" + toPrint.get(1));
                // contactFromServer.setPhone_number(Integer.parseInt(toPrint.get(2)));
                logger.info("phone Number -->" + toPrint.get(2));
                model.addAttribute("retrieval", contactFromServer);
            } catch (FileNotFoundException FNFe) {
                FNFe.printStackTrace();
            } catch (IOException IOe) {
                IOe.printStackTrace();
            }
        } else {
            // else file does not exists , throw an http status error code 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error 404: File Not Found!");
        }

        return "searchID";
    }

}
