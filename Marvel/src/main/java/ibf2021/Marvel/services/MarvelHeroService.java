package ibf2021.Marvel.services;

import org.springframework.util.DigestUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2021.Marvel.models.Superhero;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class MarvelHeroService {

    private static final Logger logger = Logger.getLogger(MarvelHeroService.class.getName());

    public Map<String, Object> get(String input) {
        long timestamp = new Date().getTime();
        String marvel_pubKey = System.getenv("Marvel_pubKey");
        String marvel_privateKey = System.getenv("Marvel_privateKey");
        String stringToHash = timestamp + marvel_privateKey + marvel_pubKey;
        Map<String, Object> superheroesMap = new LinkedHashMap<>();
        List<Superhero> listOfSuperhero = new LinkedList<>();
        // Implement MD5 Hashing to access API
        try (InputStream is = new ByteArrayInputStream(stringToHash.getBytes())) {
            String marvel_hash = DigestUtils.md5DigestAsHex(is).toLowerCase();
            String uri = "https://gateway.marvel.com/v1/public/characters";
            String url = UriComponentsBuilder.fromUriString(uri)
                    .queryParam("ts", timestamp)
                    .queryParam("nameStartsWith", input)
                    .queryParam("apikey", marvel_pubKey)
                    .queryParam("hash", marvel_hash)
                    .toUriString();
            RequestEntity<Void> req = RequestEntity.get(url).build();
            final RestTemplate template = new RestTemplate();
            // resp will populate the entire {code,status,copyright,attributionText, etag,
            // data{offset,limit, total, count,results[{id, name, description, modified,
            // thumbnail{path, extension}, resourceURI,comics... } {} ]}}
            ResponseEntity<String> resp = template.exchange(req, String.class);
            // logger.info("resp --> " + resp);
            try (InputStream inputStream = new ByteArrayInputStream(resp.getBody().getBytes())) {
                JsonReader reader = Json.createReader(inputStream);
                JsonArray superheroesArray = reader.readObject()
                        .getJsonObject("data")
                        .getJsonArray("results");
                // logger.info(" JSON ARRAY >>>" + superheroesArray);
                // Superhero superhero = new Superhero();
                // String name = superheroesArray.get(0).asJsonObject().getString("name");
                // logger.info("name" + name);
                for (int i = 0; i < superheroesArray.size(); i++) {
                    Superhero superhero = new Superhero();
                    String name = superheroesArray.get(i).asJsonObject().getString("name");
                    logger.info("superhero name --> " + name);
                    superhero.setName(name);
                    String description = superheroesArray.get(i).asJsonObject().getString("description");
                    superhero.setDescription(description);
                    logger.info("superhero description --> " + description);
                    JsonObject thumbnail = superheroesArray.get(i).asJsonObject().getJsonObject("thumbnail");
                    String thumbnailURL = thumbnail.getString("path");
                    String thumbnailFormat = thumbnail.getString("extension");
                    String fullThumbnail = thumbnailURL + "." + thumbnailFormat;
                    logger.info("superhero thumbnail --> " + fullThumbnail);
                    superhero.setThumbnail(fullThumbnail);
                    superheroesMap.put(name, superhero);
                    logger.info("superheroes map -->" + superheroesMap.toString());
                    listOfSuperhero.add(superhero);
                    logger.info("superheroes list --> " + listOfSuperhero.toString());
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return superheroesMap;
    }

}
