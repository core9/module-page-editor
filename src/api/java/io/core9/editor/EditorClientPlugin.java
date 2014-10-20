package io.core9.editor;

import io.core9.client.EditorClientProfile;
import io.core9.core.plugin.Core9Plugin;
import io.core9.plugin.server.VirtualHost;

public interface EditorClientPlugin extends Core9Plugin {

	EditorClientProfile getProfile(VirtualHost vhost, String id);

	String create(EditorClientProfile profile);

}
