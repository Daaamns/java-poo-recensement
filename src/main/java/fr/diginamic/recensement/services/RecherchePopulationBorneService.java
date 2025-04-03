package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.GlobalException;
import fr.diginamic.recensement.exceptions.LettreAuLieuChiffreException;
import fr.diginamic.recensement.exceptions.MinMaxException;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Recherche et affichage de toutes les villes d'un département dont la
 * population est comprise entre une valeur min et une valeur max renseignées
 * par l'utilisateur.
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationBorneService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws GlobalException {

		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine();

		boolean departementExiste = rec.getVilles().stream()
				.anyMatch(ville -> ville.getCodeDepartement().equalsIgnoreCase(choix));

		if (!departementExiste) {
			throw new LettreAuLieuChiffreException("Le département " + choix + " n'existe pas.");
		}

		System.out.println("Choississez une population minimum (en milliers d'habitants): ");
		String saisieMin = scanner.nextLine();
		
		System.out.println("Choississez une population maximum (en milliers d'habitants): ");
		String saisieMax = scanner.nextLine();

		if (!NumberUtils.isDigits(saisieMin)) {
			throw new LettreAuLieuChiffreException("Erreur : la population minimum doit être un nombre.");
		}

		if (!NumberUtils.isDigits(saisieMax)) {
			throw new LettreAuLieuChiffreException("Erreur : la population maximum doit être un nombre.");
		}

		int min = Integer.parseInt(saisieMin) * 1000;
		int max = Integer.parseInt(saisieMax) * 1000;

		if (min < 0 || max < 0 || min > max) {
			throw new MinMaxException("Erreur : min et max doivent être positifs, et min doit être inférieur ou égal à max.");
		}


		List<Ville> villes = rec.getVilles();
		for (Ville ville : villes) {
			if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
				if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
					System.out.println(ville);
				}
			}
		}
	}

}
