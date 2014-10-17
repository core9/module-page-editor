package io.core9.editor.connector;

import io.core9.editor.resource.BlockRegistryResource;
import io.core9.editor.resource.BlockResource;
import io.core9.editor.resource.UserResource;
import io.core9.plugin.rest.RestResource;
import io.core9.plugin.rest.RestResourceConfig;
import io.core9.plugin.rest.RestResourceConfigImpl;
import io.core9.plugin.rest.RestUtils;

import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.wordnik.swagger.config.SwaggerConfig;



@PluginImplementation
public class BlockRestResourceProviderImpl implements BlockRestResourceProvider {

	private Map<String, RestResource> resourceMap = new HashMap<>();

	@Override
	public Map<String, RestResource> getResources() {


		RestResourceConfig restResourceConfig =  new RestResourceConfigImpl();

	    SwaggerConfig config = new SwaggerConfig();
	    config.setApiVersion("1.0.1");
	    config.setBasePath("http://localhost:8080/api");

		restResourceConfig.setSwaggerConfig(config);
		restResourceConfig.setModelPackage("io.core9.editor.model");


		resourceMap.putAll(RestUtils.addRestResource(restResourceConfig, new BlockResource()));

		resourceMap.putAll(RestUtils.addRestResource(restResourceConfig, new BlockRegistryResource()));

		resourceMap.putAll(RestUtils.addRestResource(restResourceConfig, new UserResource()));


		return resourceMap;
	}





}
