package com.glamcorner;

import com.glamcorner.view.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	@Autowired
	public static Console console;

	public Application(Console console) {
		this.console = console;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		console.initToPlay();
	}
}
