package team.gutterteam123.starter.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;

import java.io.File;
import java.io.IOException;

public class GitHelper {


    private static final String REMOTE = "https://github.com/SplotyCode/Dinstagram.git";

    public void gitClone(File directory) throws GitAPIException {
        System.out.println("Cloning from " + REMOTE + " to " + directory.getAbsolutePath());
        Git result = Git.cloneRepository()
                .setURI(REMOTE)
                .setDirectory(directory)
                .setBranch("master")
                .call();
        result.close();
        System.out.println("Cloning Done!");
    }

    public void gitPull(File directory, String branch, CredentialsProvider cp) throws GitAPIException, IOException {
        System.out.println("Pulling From " + REMOTE);
        try (Git git = Git.open(directory)) {
            PullResult result = git.pull()
                    .setCredentialsProvider(cp)
                    .setRemoteBranchName(branch)
                    .call();
            System.out.println("Fetch Message: " + result.getFetchResult().getMessages());
            System.out.println("Merge Status: " + result.getMergeResult().getMergeStatus());
        }
    }
}
