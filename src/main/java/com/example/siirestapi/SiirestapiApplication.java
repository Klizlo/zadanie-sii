package com.example.siirestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class SiirestapiApplication {

	public static void main(String[] args) {
		//clear the file powiadomienia.txt
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/powiadomienia.txt"));
			bufferedWriter.write( "");
			bufferedWriter.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
		SpringApplication.run(SiirestapiApplication.class, args);
	}

}
