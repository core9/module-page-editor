package io.core9.editor.data;

import io.core9.editor.ClientRepository;
import io.core9.editor.ClientRepositoryImpl;

public class ClientData {

	
	
	
	public static ClientRepository getRepository(){
		
		ClientRepository clientRepository = new ClientRepositoryImpl();
		
		clientRepository.addDomain("www.easydrain.nl", "easydrain");
		clientRepository.addDomain("easydrain.docker.trimm.net", "easydrain");
		clientRepository.addDomain("easydrain.localhost", "easydrain");
		clientRepository.addDomain("www.kennispark.nl", "kennispark");
		clientRepository.addDomain("kennispark.editor.docker.trimm.net", "kennispark");
		clientRepository.addDomain("kennispark.localhost", "kennispark");
		
		clientRepository.addSiteRepository("kennispark", "https://github.com/jessec/site-kennispark.git");
		clientRepository.addBlockRepository("kennispark", "https://github.com/jessec/block-video.git");
		
		clientRepository.addSiteRepository("easydrain", "https://github.com/jessec/site-core9.git");
		clientRepository.addBlockRepository("easydrain", "https://github.com/jessec/block-video.git");
		
		
		return clientRepository;
	}
	
	
}
