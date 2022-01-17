package ibf2021.Marvel.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2021.Marvel.models.Superhero;

@Service
public class RandomHeroService {

    private final static Logger logger = Logger.getLogger(RandomHeroService.class.getName());

    @Autowired
    MarvelHeroService MHSvc;

    public Superhero randomise() {
        Map<String, Object> heroMap = MHSvc.get("Venom");
        heroMap.putAll(MHSvc.get("spider"));
        logger.info("Venom map ---> " + heroMap);
        int randomIndex = (int) Math.floor(Math.random() * heroMap.size());
        List<String> superheroesName = new LinkedList<>();
        superheroesName = heroMap.keySet().stream().toList();
        String name2Map = superheroesName.get(randomIndex);
        return (Superhero) heroMap.get(name2Map);

    }

}
