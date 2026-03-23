package chrominox.supervisors;

import java.util.List;

/**
 * Décrit comment le superviseur du menu principal alimente sa vue end données.
 * */
public interface MainMenuView extends View {
	/**
	 * Définit les items du menu à afficher.
	 * <p>
	 * Les items sont affichés dans l'ordre de la liste.
	 * </p>
	 * @param items la liste des items à afficher.
	 * @throws NullPointerException si items est null
	 * @throws IllegalArgumentException si la liste est vide
	 * */
	void setItems(List<String> items);
}
