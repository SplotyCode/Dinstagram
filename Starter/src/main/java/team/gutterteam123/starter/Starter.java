package team.gutterteam123.starter;

import org.apache.maven.cli.MavenCli;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import team.gutterteam123.baselib.OperatingSystem;
import team.gutterteam123.baselib.argparser.ArgumentBuilder;
import team.gutterteam123.baselib.argparser.Parameter;
import team.gutterteam123.starter.git.GitHelper;
import team.gutterteam123.starter.maven.MavenHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Starter {

    Process master;

    public static void main(String[] args){
        try {
            new Starter(args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Parameter(name = "gituser")
    public String gitUser;

    @Parameter(name = "gitpass")
    public String gitPass;

    @Parameter(name = "branch")
    public String branch = "master";

    @Parameter(name = "config")
    public boolean config = true;

    @Parameter(name = "configserver", needed = true)
    public String configServer;

    private File dinstagram = new File(System.getProperty("user.home"), "Dinstagram/");
    private File repo = new File(dinstagram, "repo/");

    private GitHelper gitHelper = new GitHelper();
    private MavenHelper mavenHelper = new MavenHelper(repo);

    private Starter(String[] args) throws IOException, GitAPIException, InterruptedException {
        new ArgumentBuilder().setInput(args).setObject(this).build();

        CredentialsProvider cp = new UsernamePasswordCredentialsProvider(gitUser, gitPass);

        if (!repo.exists()) {
            repo.mkdirs();
            gitHelper.gitClone(repo);
        } else {
            gitHelper.gitPull(repo, branch, cp);
        }

        mavenHelper.build("Master");
        
        startMaster();
    }

    private void startMaster() throws IOException, InterruptedException {
        master = executeProccess(dinstagram, "java", "-jar", "Master.jar");
        master.waitFor();
        System.out.println("Master Process Down! Exiting with: " + master.exitValue() + " Restarting in 10s...");
        Thread.sleep(10 * 1000);
        startMaster();
    }

    private Process executeProccess(File directory, String... commands) throws IOException {
        List<String> cmds = new ArrayList<>();
        if (OperatingSystem.current() == OperatingSystem.WINDOWS)
            cmds.add("cmd.exe");
        cmds.addAll(Arrays.asList(commands));
        return new ProcessBuilder(commands).
                inheritIO().
                directory(directory).
                start();
    }


}
