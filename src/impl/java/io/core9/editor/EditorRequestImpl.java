package io.core9.editor;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class EditorRequestImpl implements EditorRequest {

	private URL urlObject;
	private String absoluteUrl;
	private ClientRepository clientRepository;
	private Map<String, String> params;

	@Override
	public String getHost() {
		return urlObject.getHost();
	}

	@Override
	public String getPath() {
		return urlObject.getPath();
	}

	@Override
	public void setAbsoluteUrl(String absoluteUrl) {
		this.absoluteUrl = absoluteUrl;
		try {
			this.urlObject = new URL(absoluteUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int hashCode() {
		return urlObject.hashCode();
	}

	@Override
	public String getAbsoluteUrl() {
		return absoluteUrl;
	}

	@Override
	public String getClient() {
		return "9a8eccd84f9c40c791281139a87da7b645f25fab";//clientRepository.getClientForDomain(urlObject.getHost());
	}

	public void setClientRepository(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	private static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
		Map<String, String> query_pairs = new LinkedHashMap<String, String>();
		String query = url.getQuery();
		String[] pairs = query.split("&");
		for (String pair : pairs) {
			int idx = pair.indexOf("=");
			query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
		}
		return query_pairs;
	}

	@Override
	public Map<String, String> getParams() {
		URL url = null;
		try {
			String urlStr = urlObject.toString();
			url = new URL(urlStr);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			params = splitQuery(url);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return params;
	}

}
