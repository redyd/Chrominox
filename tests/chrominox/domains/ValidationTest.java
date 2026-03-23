package chrominox.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValidationTest {

	@Test
	void should_return_correct_message_when_toString() {
		assertEquals(Validation.SUCCESS.toString(), "");
		assertEquals(Validation.AT_LEAST_TWO.toString(),
				"Le chromino doit être en contact avec au moins deux carreaux de la mosaïque");
		assertEquals(Validation.ONE_TILE_WRONG.toString(),
				"Un carreau ou plus est en contact avec un carreau de couleur différente");
		assertEquals(Validation.ON_OTHER_CHROMINO.toString(),
				"Il n'est pas possible d'ajouter un chromino sur un autre");
	}

}
