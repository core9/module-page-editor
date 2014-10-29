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
		//String repoDir = "../../data/editor/9a8eccd84f9c40c791281139a87da7b645f25fab/site";
		GitHandler git = new GitHandlerImpl(httpsRepositoryUrl, repoDir);
		git.init();
	}
}
