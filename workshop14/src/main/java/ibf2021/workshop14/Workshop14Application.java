package ibf2021.workshop14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* 
Application should accept a command line parameter call --dataDir
This option references a directory on local computer.
If it does not exists, application creates it.
If --dataDir option is not specified, print an error message and stop.
*/

@SpringBootApplication
public class Workshop14Application {

	public static void main(String[] args) {
		// Instantiating Spring App
		SpringApplication.run(Workshop14Application.class, args);
	}

}
