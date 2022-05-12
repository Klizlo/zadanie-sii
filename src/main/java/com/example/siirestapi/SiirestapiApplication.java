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
		clearNotifications();
		SpringApplication.run(SiirestapiApplication.class, args);
	}

	private static void clearNotifications() {
		try(BufferedWriter bufferedWriter = new BufferedWriter(
						new FileWriter("src/main/resources/powiadomienia.txt"))) {
			bufferedWriter.write( "");
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

}
