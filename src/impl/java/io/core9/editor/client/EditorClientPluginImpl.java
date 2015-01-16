package io.core9.editor.client;

import io.core9.editor.EditorClientPlugin;
import io.core9.editor.EditorClientProfile;
import io.core9.plugin.database.repository.CrudRepository;
import io.core9.plugin.database.repository.NoCollectionNamePresentException;
import io.core9.plugin.database.repository.RepositoryFactory;
import io.core9.plugin.server.VirtualHost;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.PluginLoaded;

@PluginImplementation
public class EditorClientPluginImpl implements EditorClientPlugin {

	private CrudRepository<EditorClientProfile> profiles;

	@PluginLoaded
	public void onRepositoryFactory(RepositoryFactory factory) throws NoCollectionNamePresentException {
		profiles = factory.getRepository(EditorClientProfile.class);
	}




	@Override
	public EditorClientProfile getProfile(VirtualHost vhost, String id) {
		return profiles.read(vhost, id);
	}




	@Override
	public String create(EditorClientProfile profile) {
		// TODO Auto-generated method stub
		return "";
	}



}
