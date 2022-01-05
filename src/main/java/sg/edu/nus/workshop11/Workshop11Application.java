package sg.edu.nus.workshop11;

// import java libraries
import java.util.Collections;
import java.util.List;

// import third party library for logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Workshop11Application {
	// instantiate logger
	private static final Logger logger = LoggerFactory.getLogger(Workshop11Application.class);

	// default fallback port used by spring boot application
	private static final String DEFAULT_PORT = "3001";

	public static void main(String[] args) {
		logger.info("Workshop 11");
		// init the spring app
		SpringApplication app = new SpringApplication(Workshop11Application.class);

		// decode the java app args using spring args helper
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);

		// return the args from java args as list of strings
		List optsVal = appArgs.getOptionValues("port");

		// var to hold up the port number to be pass on to spring boot app
		String portNumber = null;

		// check if the opt arg is null or the first element is null
		// before retrieving it from the env varaible
		if (optsVal == null || optsVal.get(0) == null) {
			// retrieve from the OS env variable
			portNumber = System.getenv("PORT");

			// if the OS env varaiable is null or empty
			// default a default port
			if (portNumber == null) {
				portNumber = DEFAULT_PORT;
			}
			// if both of the above conditions is not met, get from args of the app
		} else {
			portNumber = (String) optsVal.get(0);

		}

		// check if the port number is still null or empty
		if (portNumber != null) {
			app.setDefaultProperties(Collections.singletonMap("server.port", portNumber));
		}

		logger.info("optsVal -> " + optsVal);
		// logger.info("optsVal.get(0) -> " + optsVal.get(0));
		logger.info("port Number -> " + portNumber);
		// logger.info("System.getenv(PORT) -> " + System.getenv("PORT"));

		app.run(args);
	}

}