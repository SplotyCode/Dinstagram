package team.gutterteam123.starter.maven;

import org.apache.maven.cli.MavenCli;

import java.io.File;

public class MavenHelper {

    private MavenCli cli = new MavenCli();
    private File repo;

    public MavenHelper(File repo) {
        this.repo = repo;
    }

    public void build(String name) {
        if (!name.endsWith("/"))
            name += '/';
        cli.doMain(new String[]{"clean", "package"}, new File(repo, name).getAbsolutePath(), System.out, System.err);
    }
}
