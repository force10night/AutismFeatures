package uk.ac.ebi.uniprot.extractFeatures.uniprot;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ebi.uniprot.dataservice.client.exception.ServiceException;

public class positionalFeaturesTests {

	
	private positionalFeatures posFeat;
	
	@Before
	public void setUp() {
		posFeat = new positionalFeatures();
		posFeat.startUniProtService();
	}

	@After
	public void tearDown() {
		posFeat.stopUniProtService();
	}
	
	
	@Test
	public void testqueryAccessionForFeatures() {
		
		try {
			posFeat.queryAccessionForFeatures("Q9HCK8", Integer.valueOf(62), SearchType.LOST);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	
}
