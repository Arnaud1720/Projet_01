package com.arnaud.ia.api;

import com.arnaud.ia.api.entity.Roles;
import com.arnaud.ia.api.entity.Utilisateurs;
import com.arnaud.ia.api.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication(scanBasePackages = "com.arnaud.ia.api")
public class ApiApplication {

	@Bean
	public CommandLineRunner createAdminUser(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			String email = "admin@example.com";
			// Vérifier si l'utilisateur n'existe pas déjà
			if (!utilisateurRepository.existsByEmail(email)) {
				Utilisateurs admin = new Utilisateurs();
				admin.setNom("Admin");
				admin.setPrenom("Super");
				admin.setEmail(email);
				// Mot de passe en clair, à hacher :
				admin.setPassword(passwordEncoder.encode("monmotdepasse"));
				admin.setTelephone("0123456789");
				admin.setRole(Roles.Administrateur);

				utilisateurRepository.save(admin);
				System.out.println("Utilisateur administrateur créé au démarrage : " + email);
			} else {
				System.out.println("L'utilisateur admin existe déjà, aucune action effectuée.");
			}
		};
	}


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


