package ibf2021.fortuneCookieAPI.controllers;

import java.util.List;
// import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.fortuneCookieAPI.FortuneCookieApiApplication;
import ibf2021.fortuneCookieAPI.services.FortuneCookie;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

@RestController
@RequestMapping(path = "/cookies", produces = MediaType.APPLICATION_JSON_VALUE)
public class FortuneCookieController {

    // private final Logger logger =
    // Logger.getLogger(FortuneCookieApiApplication.class.getName());

    @Autowired
    FortuneCookie fCookie;

    @GetMapping
    public ResponseEntity<String> getCookieMapping(@RequestParam(defaultValue = "1") Integer count) {
        if ((count > 10) || (count < 1)) {
            // ResponseEntity<String> re = new ResponseEntity<>("COUNT MUST BE BETWEEN 1 TO
            // 10 INCLUSIVELY",
            // HttpStatus.BAD_REQUEST);

            // ResponseEntity
            // .status(HttpStatus.BAD_REQUEST)
            // .body("COUNT MUST BE BETWEEN 1 TO 10 INCLUSIVELY");

            // ResponseEntity
            // .badRequest()
            // .body("COUNT MUST BE BETWEEN 1 TO 10 INCLUSIVELY");

            JsonObjectBuilder errorBuilder = Json.createObjectBuilder();
            String errorBody = errorBuilder.add("error", "number should be between 1 and 10 inclusive!").build()
                    .toString();
            // logger.info("errorBody --> " + errorBody);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
        } else {

            List<String> cookies = fCookie.getCookies(count);
            // logger.info("Cookies -->" + cookies);
            JsonArrayBuilder cookieBuilder = Json.createArrayBuilder();

            // cookies.stream().forEach(v -> cookieBuilder.add(v));

            for (String cookie : cookies) {
                cookieBuilder.add(cookie);
            }

            JsonObjectBuilder okBuilder = Json.createObjectBuilder();
            okBuilder.add("cookies", cookieBuilder).add("timestamp", System.currentTimeMillis());
            String OKResponse = okBuilder.build().toString();
            // logger.info("OK -->" + OK);
            return ResponseEntity.status(HttpStatus.OK).body(OKResponse);
        }
    }

}
