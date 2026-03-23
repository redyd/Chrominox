package chrominox.domains;

import java.util.List;

/**
 * Décrit comment générer une liste de chrominos.
 * <p>
 * Nous avons choisi une liste pour préserver la position occupée par chaque
 * chrominos.
 * </p>
 * <p>
 * Notre implémentation fournit une liste de chromino dans un ordre précis.
 * </p>
 * <p>
 * Vous devrez fournir une second implémentation pour générer les chrominos dans
 * un ordre aléatoire.
 * </p>
 * <hr>
 * <h1>It-1-q1</h1>
 * <ol>
 * <li>Description de l'algorithme<br>
 * <ul>
 * <li>On crée un tableau ne contenant que les couleurs des TyleType (sans le
 * caméléon)</li>
 * <li>On va itérer dessus dans une triple boucle imbriquée</li>
 * <li>Lors de la premièr itération, on va parcourir tous les éléments de la
 * liste</li>
 * <li>Lors de la deuxième itération, on va reparcourir la liste en partant de
 * la tuile de la première boucle imbriquée</li>
 * <li><i><strong>A ce moment, on obtient les extrémités du chromino, à chaque
 * fois différents et sans doublon</strong></i></li>
 * <li>Ensuite, on reparcours toute la liste pour récupérer la couleur qui ira
 * au milieu du chromino</li>
 * <li>On construit alors chaque chromino avec, en première couleur, la tuile de
 * la première boucle, en dernière couleur, la tuile de la deuxième boucle, et
 * enfin, au milieu, la tuile de la troisème boucle</li>
 * <li><i><strong>Arrivé à cette étape, nous avons construit les 75 chrominos
 * différents</strong></i></li>
 * <li>Pour générer les chrominos caméléon, on itère deux fois sur la même liste
 * sans couleur céméléon</li>
 * <li>La deuxième boucle imbriquée démarre à chaque fois au niveau de la
 * première (comme l'algorithme des 75 caméléons)</li>
 * <li>On ajoute 5 de ces 10 caméléons à la liste existante</li>
 * </ul>
 * </li>
 * <li>CTT de l'algorithme</li>
 * <p>
 * tab = toutes les couleurs des tuiles<br>
 * (tailleTab * tailleTab * tailleTab) + (tailleTab * tailleTab) =
 * tailleTab<sup>3</sup> + tailleTab<sup>2</sup> => O(tailleTab<sup>3</sup>)
 * </p>
 * <p>
 * La complexité temporelle est donc cubique.
 * </p>
 * <li>Règle du sac</li>
 * <p>
 * Pour qu'un sac soit valide, tous les chrominos doivent être unique, même si
 * on les retournes. Les chrominos caméléons doivent posséder qu'une seule case
 * caméléon, au centre.
 * </p>
 * </ol>
 * <hr>
 * <h1>It-1-q2</h1>
 * <ol>
 * <li>Postcondition<br>
 * La liste des chrominos générés doit contenir 80 chrominos, tous strictement
 * différents (donc différent même si on le retourne). Sur les 80 cchrominos, 5
 * d'entre eux doivent être des caméléons (deux couleurs différentes aux
 * extrémités et la couleur caméléon au centre).<br>
 * Les 80 chrominos générés viennent de 75 chrominos + 5 caméléon. Les 75
 * chrominos différents se calculent grâce au nombre de couleurs présents dans
 * l'enum: 5 couleurs (+1 caméléon). Le calcul est le suivant: <br>
 * {@code 5*5 + 5*4 + 5*3 + 5*2 + 5*1 = 75}, où {@code 5} correspond au nombre
 * de couleurs disponible, et son muliplicateur au nombre de couleurs restant à
 * multiplier avec, sans faire de doublons.<br>
 * On ajoute ensuite 5 caméléons à ce résultat pour créer un sac.</li>
 * </ol>
 */
@FunctionalInterface
public interface ChrominoGenerator {

	/**
	 * Retourne une liste de 80 nouveaux chrominos.
	 */
	List<Chromino> generateChrominoes();
}
