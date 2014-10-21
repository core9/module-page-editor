package io.core9.editor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClientRepositoryImpl implements ClientRepository {

	private Map<String, String> domainRepository = new HashMap<>();
	private Map<String, String> siteGitRepository = new HashMap<>();
	private Map<String, List<String>> blockGitRepository = new HashMap<>();


	@Override
	public void addDomain(String domain, String client){
		domainRepository.put(domain, getShaId(client));
	}

	@Override
	public String getClientForDomain(String domain){
		return domainRepository.get(domain);
	}

	@Override
	public void addSiteRepository(String client, String repository) {
		siteGitRepository.put(getShaId(client), repository);
	}
	
	@Override
	public String getSiteRepository(String client){
		return siteGitRepository.get(client);
	}

	@Override
	public void addBlockRepository(String client, String repository) {
		List<String> list = blockGitRepository.get(getShaId(client));
		if(list == null){
			list = new ArrayList<String>();
		}
		list.add(repository);
		blockGitRepository.put(getShaId(client), list);
	}
	
	@Override
	public List<String> getBlockRepository(String client) {
		return blockGitRepository.get(client);
	}
	
	public static String getShaId(String hashCode) {
		String sha1 = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(hashCode.getBytes(StandardCharsets.UTF_8));
			sha1 = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}
		return sha1;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}


}
