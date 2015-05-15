package uk.ac.ebi.uniprot.extractFeatures.uniprot;

import java.util.List;
import uk.ac.ebi.kraken.ffwriter.line.FFLineBuilder;
import uk.ac.ebi.kraken.ffwriter.line.impl.ft.FeatureLineBuilderFactory;
import uk.ac.ebi.kraken.ffwriter.line.impl.ft.SimpleFeatureLineBuilder;
import uk.ac.ebi.kraken.interfaces.uniprot.features.Feature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureType;

import uk.ac.ebi.uniprot.dataservice.client.Client;
import uk.ac.ebi.uniprot.dataservice.client.QueryResult;
import uk.ac.ebi.uniprot.dataservice.client.ServiceFactory;
import uk.ac.ebi.uniprot.dataservice.client.exception.ServiceException;
import uk.ac.ebi.uniprot.dataservice.client.uniprot.UniProtComponent;
import uk.ac.ebi.uniprot.dataservice.client.uniprot.UniProtQueryBuilder;
import uk.ac.ebi.uniprot.dataservice.client.uniprot.UniProtService;
import uk.ac.ebi.uniprot.dataservice.query.Query;

import uk.ac.ebi.kraken.interfaces.uniprot.features.PeptideFeature;

import uk.ac.ebi.kraken.interfaces.uniprot.features.NpBindFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.MutagenFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.MotifFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.ModResFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.MetalFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.LipidFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.IntramemFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.InitMetFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.HelixFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.DomainFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.DnaBindFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.DisulfidFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.CrosslinkFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.ConflictFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.CompBiasFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.CoiledFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.CarbohydFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.CaBindFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.BindingFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.ActSiteFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.ChainFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureDescription;
import uk.ac.ebi.kraken.interfaces.uniprot.features.HasFeatureDescription;
import uk.ac.ebi.kraken.interfaces.uniprot.features.VariantFeature;

public class positionalFeatures {

	private UniProtService uniProtService;
	private Query query;
	
	public void startUniProtService() {
		ServiceFactory serviceFactoryInstance = Client.getServiceFactoryInstance();
		uniProtService = serviceFactoryInstance.getUniProtQueryService();
		uniProtService.start();
		
	}
	
	public void stopUniProtService() {
		uniProtService.stop();
	}
	
	
	
	public void queryAccessionForFeatures(String accession, Integer position, SearchType type) throws ServiceException {
		
		query = UniProtQueryBuilder.accession(accession);
		
		switch (type) {
		case TRUNCATED:
		case OVERLAPPING:	
			query = query.and(UniProtQueryBuilder.featuresStartAfter(0));
			break;
		case LOST:
			query = query.and(UniProtQueryBuilder.featuresStartAfter(position.intValue()));
			break;
		default:
			break;
		}

		QueryResult<UniProtComponent<Feature>> results = uniProtService.getFeatures(query);
		while(results.hasNext()) {
			UniProtComponent<Feature> featComponent = results.next();
			List<Feature> features = featComponent.getComponent();
			FFLineBuilder<Feature> builder = FeatureLineBuilderFactory.create(features.get(0));
			
			
			builder.buildString(features.get(0));
			for (Feature feature : features) {
				
				FeatureType ftType = feature.getType();
				
				if(ftType.compareTo(FeatureType.ACT_SITE) == 0) {
				    ActSiteFeature actFeat = (ActSiteFeature) feature;
				    System.out.println(actFeat.getType().getName()
				                    +" "+actFeat.getFeatureLocation().getStart()
				                    +" "+actFeat.getFeatureLocation().getEnd()
				                    +" "+actFeat.getFeatureDescription().getValue());
                        
                      

                 
                } else if(ftType.compareTo(FeatureType.BINDING) == 0) {
                  BindingFeature bindFeat = (BindingFeature) feature;
                  System.out.println(bindFeat.getType().getName()
                          +" "+bindFeat.getFeatureLocation().getStart()
                          +" "+bindFeat.getFeatureLocation().getEnd()
                          +" "+bindFeat.getFeatureDescription().getValue());
                  
                    
                } else if(ftType.compareTo(FeatureType.CA_BIND) == 0) {
                    CaBindFeature caFeat = (CaBindFeature) feature;
                    System.out.println(caFeat.getType().getName()
                            +" "+caFeat.getFeatureLocation().getStart()
                            +" "+caFeat.getFeatureLocation().getEnd()
                            +" "+caFeat.getFeatureDescription().getValue());
                    
                  
                } else if(ftType.compareTo(FeatureType.CARBOHYD) == 0) {
                    CarbohydFeature carbFeat = (CarbohydFeature) feature;
                    System.out.println(carbFeat.getType().getName()
                            +" "+carbFeat.getFeatureLocation().getStart()
                            +" "+carbFeat.getFeatureLocation().getEnd()
                            +" "+carbFeat.getFeatureDescription().getValue());
                    
                    
                } else if(ftType.compareTo(FeatureType.COILED) == 0) {
                    CoiledFeature coilFeat = (CoiledFeature) feature;
                    System.out.println(coilFeat.getType().getName()
                            +" "+coilFeat.getFeatureLocation().getStart()
                            +" "+coilFeat.getFeatureLocation().getEnd());
                    
                } else if(ftType.compareTo(FeatureType.COMPBIAS) == 0) {
                    CompBiasFeature compFeat = (CompBiasFeature) feature;
                    System.out.println(compFeat.getType().getName()
                            +" "+compFeat.getFeatureLocation().getStart()
                            +" "+compFeat.getFeatureLocation().getEnd()
                            +" "+compFeat.getFeatureDescription().getValue());
                } else if(ftType.compareTo(FeatureType.CONFLICT) == 0) {
                    ConflictFeature conFeat = (ConflictFeature) feature;
                    System.out.println(conFeat.getType().getName()
                            +" "+conFeat.getFeatureLocation().getStart()
                            +" "+conFeat.getFeatureLocation().getEnd()
                            +" "+conFeat.getFeatureDescription().getValue());
                } else if(ftType.compareTo(FeatureType.CROSSLNK) == 0) {
                    CrosslinkFeature xlnkFeat = (CrosslinkFeature) feature;
                    System.out.println(xlnkFeat.getType().getName()
                            +" "+xlnkFeat.getFeatureLocation().getStart()
                            +" "+xlnkFeat.getFeatureLocation().getEnd()
                            +" "+xlnkFeat.getFeatureDescription().getValue());
                } else if(ftType.compareTo(FeatureType.DISULFID) == 0) {
                    DisulfidFeature disulFeat = (DisulfidFeature) feature;
                    System.out.println(disulFeat.getType().getName()
                            +" "+disulFeat.getFeatureLocation().getStart()
                            +" "+disulFeat.getFeatureLocation().getEnd()
                            +" "+disulFeat.getFeatureDescription().getValue());
                } else if(ftType.compareTo(FeatureType.DNA_BIND) == 0) {
                    DnaBindFeature dnaFeat = (DnaBindFeature) feature;
                    System.out.println(dnaFeat.getType().getName()
                            +" "+dnaFeat.getFeatureLocation().getStart()
                            +" "+dnaFeat.getFeatureLocation().getEnd()
                            +" "+dnaFeat.getFeatureDescription().getValue());
                } else if(ftType.compareTo(FeatureType.DOMAIN) == 0) {				
                    DomainFeature domFeat = (DomainFeature) feature;
                    System.out.println(domFeat.getType().getName()
                            +" "+domFeat.getFeatureLocation().getStart()
                            +" "+domFeat.getFeatureLocation().getEnd()
                            +" "+domFeat.getFeatureDescription().getValue());
                    
                } else if(ftType.compareTo(FeatureType.HELIX) == 0) {
                    HelixFeature helFeat = (HelixFeature) feature;
                    System.out.println(helFeat.getType().getName()
                            +" "+helFeat.getFeatureLocation().getStart()
                            +" "+helFeat.getFeatureLocation().getEnd());
                            
                } else if(ftType.compareTo(FeatureType.INIT_MET) == 0) {
                    InitMetFeature  initMFeat = (InitMetFeature) feature;
                    System.out.println(initMFeat.getType().getName()
                            +" "+initMFeat.getFeatureLocation().getStart()
                            +" "+initMFeat.getFeatureLocation().getEnd()
                            +" "+initMFeat.getFeatureDescription().getValue());
                } else if(ftType.compareTo(FeatureType.INTRAMEM) == 0) {
                    IntramemFeature intraMFeat = (IntramemFeature) feature;
                    System.out.println(intraMFeat.getType().getName()
                            +" "+intraMFeat.getFeatureLocation().getStart()
                            +" "+intraMFeat.getFeatureLocation().getEnd()
                            +" "+intraMFeat.getFeatureDescription().getValue());
                } else if(ftType.compareTo(FeatureType.LIPID) == 0) {
                    LipidFeature lipFeat = (LipidFeature) feature;
                    System.out.println(lipFeat.getType().getName()
                            +" "+lipFeat.getFeatureLocation().getStart()
                            +" "+lipFeat.getFeatureLocation().getEnd()
                            +" "+lipFeat.getFeatureDescription().getValue());
                } else if(ftType.compareTo(FeatureType.METAL) == 0) {
                    MetalFeature metFeat = (MetalFeature) feature;
                    System.out.println(metFeat.getType().getName()
                            +" "+metFeat.getFeatureLocation().getStart()
                            +" "+metFeat.getFeatureLocation().getEnd()
                            +" "+metFeat.getFeatureDescription().getValue());
                } else if(ftType.compareTo(FeatureType.MOD_RES) == 0) {
                    ModResFeature modResFeat = (ModResFeature) feature;
                    System.out.println(modResFeat.getType().getName()
                            +" "+modResFeat.getFeatureLocation().getStart()
                            +" "+modResFeat.getFeatureLocation().getEnd()
                            +" "+modResFeat.getFeatureDescription().getValue());
                } else if(ftType.compareTo(FeatureType.MOTIF) == 0) {
                    MotifFeature motifFeat = (MotifFeature) feature;
                    System.out.println(motifFeat.getType().getName()
                            +" "+motifFeat.getFeatureLocation().getStart()
                            +" "+motifFeat.getFeatureLocation().getEnd()
                            +" "+motifFeat.getFeatureDescription().getValue());
                } else if(ftType.compareTo(FeatureType.MUTAGEN) == 0) {
                    MutagenFeature mutFeat = (MutagenFeature) feature;
                    System.out.println(mutFeat.getType().getName()
                            +" "+mutFeat.getFeatureLocation().getStart()
                            +" "+mutFeat.getFeatureLocation().getEnd());

                } else if(ftType.compareTo(FeatureType.NP_BIND) == 0) {
                    NpBindFeature npBindFeat = (NpBindFeature) feature;
                    System.out.println(npBindFeat.getType().getName()
                            +" "+npBindFeat.getFeatureLocation().getStart()
                            +" "+npBindFeat.getFeatureLocation().getEnd()
                            +" "+npBindFeat.getFeatureDescription().getValue());
                } else if(ftType.compareTo(FeatureType.PEPTIDE) == 0) {
                    PeptideFeature pepFeat = (PeptideFeature) feature;
                    System.out.println(pepFeat.getType().getName()
                            +" "+pepFeat.getFeatureLocation().getStart()
                            +" "+pepFeat.getFeatureLocation().getEnd()
                            +" "+pepFeat.getFeatureDescription().getValue());
                } else if(ftType.compareTo(FeatureType.PROPEP) == 0) {
                    
                } else if(ftType.compareTo(FeatureType.PROPEP) == 0) {
                    
                } else if(ftType.compareTo(FeatureType.REGION) == 0) {
                    
                } else if(ftType.compareTo(FeatureType.REPEAT) == 0) {
                    
                } else if(ftType.compareTo(FeatureType.SIGNAL) == 0) {
                    
                } else if(ftType.compareTo(FeatureType.SITE) == 0) {
                    
                } else if(ftType.compareTo(FeatureType.STRAND) == 0) {
                    
                } else if(ftType.compareTo(FeatureType.TOPO_DOM) == 0) {
                    
                } else if(ftType.compareTo(FeatureType.TRANSIT) == 0) {
                    
                } else if(ftType.compareTo(FeatureType.TRANSMEM) == 0) {
                    
                } else if(ftType.compareTo(FeatureType.TURN) == 0) {
                    
                } else if(ftType.compareTo(FeatureType.VARIANT) == 0) {
                
                    VariantFeature varFeat = (VariantFeature) feature;
                 
                    String varOut = varFeat.getType().getName()
                            +" "+varFeat.getFeatureId().getValue()
                            +" "+varFeat.getFeatureLocation().getStart()
                            +" "+varFeat.getFeatureLocation().getEnd();
                    
                    if(varFeat.getOriginalSequence().getValue().length() > 0) {
                        varOut = varOut.concat(" missense "+varFeat.getOriginalSequence().getValue().toString()
                                    +"/"+varFeat.getAlternativeSequences().toString()
                                    +" "+varFeat.getVariantReport().getValue());
                    } else {
                        varOut = varOut.concat(" deletion "+" "+varFeat.getVariantReport().getValue());
                    }
                    
                   System.out.println(varOut);
                                   
                  
                } else if(ftType.compareTo(FeatureType.CHAIN) == 0) {
                    ChainFeature chnFeat = (ChainFeature) feature;
                    System.out.println(chnFeat.getType().getName()
                                    +" "+chnFeat.getFeatureId().getValue()
                                    +" "+chnFeat.getFeatureLocation().getStart()
                                    +" "+chnFeat.getFeatureLocation().getEnd()
                                    +" "+chnFeat.getFeatureDescription().getValue());
                    
                } else if(ftType.compareTo(FeatureType.ZN_FING) == 0) {
                    
                }
				
			}
		
			
		}
		
	}
	
	
	public void getTruncatedFeatures(Integer position) {
		
	}
	
	public void getLostFeatures(Integer position) {
		
		
	}
	
	public void getOverlappingFeatures(Integer position) {
		
		
	}
	
	
	
	
	
}
