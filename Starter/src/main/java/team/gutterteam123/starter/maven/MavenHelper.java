package team.gutterteam123.starter.maven;

import org.apache.maven.cli.MavenCli;
import org.apache.maven.shared.invoker.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.baselib.constants.FileConstants;
import team.gutterteam123.baselib.objects.LoggerHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collections;

public class MavenHelper {

    private MavenCli cli = new MavenCli();
    private File repo;
    private LoggerHandler stdOut;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MavenHelper(File repo) {
        this.repo = repo;
        stdOut = new LoggerHandler(FileConstants.getMAVEN_LOG());
    }

    @Deprecated
    public void build(String name) {
        if (!name.endsWith("/"))
            name += '/';
        cli.doMain(new String[]{"clean", "package"}, new File(repo, name).getAbsolutePath(), System.out, System.err);
    }

    @Deprecated
    public void install(String name) {
        if (!name.endsWith("/"))
            name += '/';
        install(new File(repo, name));
    }

    public void install(File file) {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(file);
        request.setGoals(Collections.singletonList("install"));
        request.setDebug(true);
        request.setOutputHandler(stdOut);
        request.setErrorHandler(stdOut);

        Invoker invoker = new DefaultInvoker();
        try {
            invoker.execute(request);
        } catch (MavenInvocationException e) {
            logger.error("Maven Build Failed", e);
        }
    }
}
