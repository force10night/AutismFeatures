package uk.ac.ebi.uniprot.extractFeatures.uniprot;


public enum SearchType {

	TRUNCATED, LOST, OVERLAPPING;

	public SearchType[] getTypes() {
		return values();
	}
}
