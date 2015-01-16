package io.core9.editor.data;

import io.core9.editor.ClientRepository;
import io.core9.editor.client.ClientRepositoryImpl;

public class ClientData {




	public static ClientRepository getRepository(){

		ClientRepository clientRepository = new ClientRepositoryImpl();

		clientRepository.addDomain("www.easydrain.nl", "easydrain");
		clientRepository.addDomain("easydrain.docker.trimm.net", "easydrain");
		clientRepository.addDomain("easydrain.editor.core9.io", "easydrain");
		clientRepository.addDomain("easydrain.localhost", "easydrain");
		clientRepository.addDomain("pageeditor-prod-ens.trimm.net", "easydrain");
		clientRepository.addDomain("easydrain.editor.trimm.net", "easydrain");
		clientRepository.addDomain("easydrain.com", "easydrain");
		clientRepository.addDomain("www.easydrain.com", "easydrain");

/*		clientRepository.addPage("www.easydrain.nl", "/nl/");
		clientRepository.addPage("easydrain.docker.trimm.net", "/nl/");
		clientRepository.addPage("easydrain.editor.core9.io", "/nl/");
		clientRepository.addPage("easydrain.localhost", "/nl/");
*/

		clientRepository.addDomain("www.kennispark.nl", "kennispark");
		clientRepository.addDomain("kennispark.editor.docker.trimm.net", "kennispark");
		clientRepository.addDomain("kennispark.editor.core9.io", "kennispark");
		clientRepository.addDomain("kennispark.localhost", "kennispark");
		clientRepository.addDomain("kennispark.editor.trimm.net", "kennispark");



/*		clientRepository.addPage("www.kennispark.nl", "/jaarplan/");
		clientRepository.addPage("kennispark.editor.docker.trimm.net", "/jaarplan/");
		clientRepository.addPage("kennispark.editor.core9.io", "/jaarplan/");
		clientRepository.addPage("kennispark.localhost", "/jaarplan/");*/


		clientRepository.addSiteRepository("kennispark", "https://github.com/jessec/site-kennispark.git");
		clientRepository.addBlockRepository("kennispark", "https://github.com/jessec/block-video.git");

		clientRepository.addSiteRepository("easydrain", "https://github.com/jessec/site-core9.git");
		clientRepository.addBlockRepository("easydrain", "https://github.com/jessec/block-video.git");


		return clientRepository;
	}


}
