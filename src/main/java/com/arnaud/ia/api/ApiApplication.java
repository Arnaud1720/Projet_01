package com.arnaud.ia.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication(scanBasePackages = "com.arnaud.ia.api")
public class ApiApplication {

	public static void main(String[] args) {
		// Demander à l'utilisateur de saisir le chemin
		Scanner scanner = new Scanner(System.in);
		System.out.println("Entrez le chemin de base pour enregistrer les fichiers (par défaut : /mnt/c) : ");
		String userPath = scanner.nextLine();

		// Si l'utilisateur ne saisit rien, utiliser la valeur par défaut
		if (userPath.isEmpty()) {
			userPath = "/mnt/c";
		}

		// Définir dynamiquement la propriété Spring
		System.setProperty("app.base-path", userPath);

		// Lancer l'application Spring Boot
		SpringApplication.run(ApiApplication.class, args);
	}

}


