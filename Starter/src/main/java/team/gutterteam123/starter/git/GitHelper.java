package team.gutterteam123.starter.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class GitHelper {

    Logger logger = LoggerFactory.getLogger(getClass());


    private static final String REMOTE = "https://github.com/SplotyCode/Dinstagram.git";

    public void gitClone(File directory) throws GitAPIException {
        logger.info("Cloning from " + REMOTE + " to " + directory.getAbsolutePath());
        Git result = Git.cloneRepository()
                .setURI(REMOTE)
                .setDirectory(directory)
                .setBranch("master")
                .call();
        result.close();
        logger.info("Cloning Done!");
    }

    public MergeResult.MergeStatus gitPull(File directory, String branch, CredentialsProvider cp) throws GitAPIException, IOException {
        logger.info("Pulling From " + REMOTE);
        try (Git git = Git.open(directory)) {
            PullResult result = git.pull()
                    .setCredentialsProvider(cp)
                    .setRemoteBranchName(branch)
                    .call();
            logger.info("Fetch Message: " + result.getFetchResult().getMessages());
            logger.info("Merge Status: " + result.getMergeResult().getMergeStatus());
            return result.getMergeResult().getMergeStatus();
        } catch (TransportException ex) {
            logger.error("Failed to Connect to Git", ex);
        }
        return null;
    }
}
