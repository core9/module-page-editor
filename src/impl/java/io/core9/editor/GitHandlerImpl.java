package io.core9.editor;

import java.io.File;

import io.core9.plugin.git.GitRepositoryImpl;
import io.core9.plugin.git.GitRepositoryManager;
import io.core9.plugin.git.GitRepositoryManagerImpl;

public class GitHandlerImpl implements GitHandler {

	private GitRepositoryImpl repo;
	private GitRepositoryManager gitManager = new GitRepositoryManagerImpl();

	public GitHandlerImpl(String httpsRepositoryUrl, String localPath) {

		repo = new GitRepositoryImpl();
		repo.setLocalPath(localPath);
		repo.setOrigin(httpsRepositoryUrl);
	}

	@Override
	public void pull() {
		gitManager.pull(repo);
	}

	@Override
	public void init() {
		gitManager.init(repo);
	}

	public static void clonePublicGitRepository(String httpsRepositoryUrl, String repositoryDirectory) {
		String repoDir = "../.." + File.separator + repositoryDirectory;
		GitHandler git = new GitHandlerImpl(httpsRepositoryUrl, repoDir);
		git.init();
	}
}
