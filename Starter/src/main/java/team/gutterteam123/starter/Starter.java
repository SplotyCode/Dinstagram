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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Starter {

    private static final String REMOTE = "https://github.com/SplotyCode/Dinstagram.git";

    Process master;

    public static void main(String[] args){
        try {
            new Starter(args);
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }

    @Parameter(name = "gituser", needed = true)
    public String gitUser;

    @Parameter(name = "gitpass", needed = true)
    public String gitPass;

    @Parameter(name = "branch")
    public String branch = "master";

    private Starter(String[] args) throws IOException, GitAPIException {
        new ArgumentBuilder().setInput(args).setObject(this).build();

        CredentialsProvider cp = new UsernamePasswordCredentialsProvider(gitUser, gitPass);

        File directory = new File(System.getProperty("user.home") + "/Dinstagram/repo/");
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("Cloning from " + REMOTE + " to " + directory.getAbsolutePath());
            Git result = Git.cloneRepository()
                    .setURI(REMOTE)
                    .setDirectory(directory)
                    .setBranch("master")
                    .call();
            result.close();
            System.out.println("Cloning Done!");
        } else {
            try (Git git = Git.open(directory)) {
                System.out.println("Pulling From " + REMOTE);
                PullResult result = git.pull()
                        .setCredentialsProvider(cp)
                        .setRemoteBranchName(branch)
                        .call();
                System.out.println("Fetch Message: " + result.getFetchResult().getMessages());
                System.out.println("Merge Status: " + result.getMergeResult().getMergeStatus());
            }
        }

        MavenCli cli = new MavenCli();
        cli.doMain(new String[]{"clean", "package"}, new File(directory, "Master/").getAbsolutePath(), System.out, System.out);

        executeProccess(directory, "java", "-jar", "Master.jar");
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
