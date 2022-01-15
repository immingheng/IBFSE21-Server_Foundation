package ibf2021.workshop3;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Collections;
import java.util.List;

// Import logging library from SLF4J
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* 
Application should accept a command line parameter call --dataDir
This option references a directory on local computer.
If it does not exists, application creates it.
If --dataDir option is not specified, print an error message and stop.
*/

@SpringBootApplication
public class Workshop13Application {
	private static final Logger logger = LoggerFactory.getLogger(Workshop13Application.class);

	public static void main(String[] args) {
		// Instantiating Spring App
		SpringApplication app = new SpringApplication(Workshop13Application.class);

		// Decode java app args using spring args helper
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);

		List<String> optsVal = appArgs.getOptionValues("dataDir");

		logger.info("optsVal --> " + optsVal.get(0));
		String directory = (String) optsVal.get(0); // reads from argument
		logger.info("directory --> " + directory);
		String dir = "";
		if (directory == null) {
			// print an error message and stop
			System.err.println("You have to specify the dataDir in your argument!");
			// argument to exit = 0 => expected termination
			// non-zero argument => unexpected termination
			System.exit(0);
		} else {
			// check if the dataDir exists, if it doesn't create it
			// Create a file instance based on the abstract pathname (String)
			File f = new File(directory);
			if (f.exists()) {
				if (f.isDirectory()) {
					dir = f.getAbsolutePath();
				}
			} else {
				// if directory does not exists, create one.
				f.mkdirs();
				dir = f.getAbsolutePath();
			}

			app.setDefaultProperties(Collections.singletonMap("dataDir", dir));
			app.run(args);
		}
	}

}
