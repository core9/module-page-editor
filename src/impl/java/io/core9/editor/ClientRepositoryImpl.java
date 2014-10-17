package io.core9.editor;

import java.util.HashMap;
import java.util.Map;


public class ClientRepositoryImpl implements ClientRepository {

	private Map<String, String> repository = new HashMap<>();


	@Override
	public void addDomain(String domain, String client){
		repository.put(domain, client);
	}

	@Override
	public String getClientForDomain(String domain){
		return repository.get(domain);
	}

}
