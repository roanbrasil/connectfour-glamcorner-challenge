package com.glamcorner;

import com.glamcorner.controller.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	@Autowired
	public static Game game;

	public Application(Game game) {
		this.game = game;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		game.initToPlay();
	}
}
