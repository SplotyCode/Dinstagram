package team.gutterteam123.starter.maven;

import org.apache.maven.cli.MavenCli;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.constants.FileConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class MavenHelper {

    private MavenCli cli = new MavenCli();
    private File repo;
    private PrintStream stdOut;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MavenHelper(File repo) {
        this.repo = repo;
        try {
            stdOut = new PrintStream(FileConstants.getMAVEN_LOG());
        } catch (FileNotFoundException ex) {
            logger.error("Maven Log File was not created by Starter", ex);
        }
    }

    public void build(String name) {
        if (!name.endsWith("/"))
            name += '/';
        cli.doMain(new String[]{"clean", "package"}, new File(repo, name).getAbsolutePath(), System.out, System.err);
    }

    public void install(String name) {
        if (!name.endsWith("/"))
            name += '/';
        install(new File(repo, name));
    }

    public void install(File file) {
        cli.doMain(new String[]{"clean", "install", "-Dmaven.multiModuleProjectDirectory=" + file.getAbsolutePath()}, file.getAbsolutePath(), stdOut, stdOut);
    }
}
