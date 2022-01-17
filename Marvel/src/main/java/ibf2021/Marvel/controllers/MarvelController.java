package ibf2021.Marvel.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2021.Marvel.models.Superhero;
import ibf2021.Marvel.services.RandomHeroService;

@Controller
@RequestMapping(path = "/marvel")
public class MarvelController {

    private static final Logger logger = Logger.getLogger(MarvelController.class.getName());

    @Autowired
    RandomHeroService randomHeroSvc;

    @GetMapping
    public String GenerateSuperheroesList(Model model) {
        Superhero hero = randomHeroSvc.randomise();
        logger.info("Random superhero --> " + hero);
        model.addAttribute("hero", hero);
        return "superhero";
    }

}
