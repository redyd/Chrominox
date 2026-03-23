package chrominox;

import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

/**
 * Valide les dépendances entre les paquetages.
 * */
public class ArchTest {
	private static final String PROJECT = ArchTest.class.getPackageName();

	@Test
	void domainsShouldBeAccessedBySupervisersAndDomains() {
		var classes = new ClassFileImporter().importPackages(PROJECT);
		
		ArchRule myRule = classes()
			    .that().resideInAPackage(PROJECT+".domains..")
			    .should().onlyBeAccessed()
			    .byAnyPackage(PROJECT, PROJECT+".supervisors..", PROJECT+".domains..", PROJECT+".automated..");
		
		myRule.check(classes);
	}
	
	@Test
	void supervisersShouldOnlyBeAccessedBySwingAndSupervisers() {
		var classes = new ClassFileImporter().importPackages(PROJECT);
		
		ArchRule myRule = classes()
			    .that().resideInAPackage(PROJECT+".supervisors..")
			    .should().onlyBeAccessed()
			    .byAnyPackage(PROJECT, PROJECT+".swing..", PROJECT+".supervisors..", PROJECT+".automated..", PROJECT+".acceptances..");
		
		myRule.check(classes);
	}
	
	@Test
	void swingShouldOnlyBeAccessedBySwing() {
		var classes = new ClassFileImporter().importPackages(PROJECT);
		
		ArchRule myRule = classes()
			    .that().resideInAPackage(PROJECT+".swing..")
			    .should().onlyBeAccessed()
			    .byAnyPackage(PROJECT, PROJECT+".swing..", PROJECT+".automated..", PROJECT+".acceptances..");
		
		myRule.check(classes);
	}
}
