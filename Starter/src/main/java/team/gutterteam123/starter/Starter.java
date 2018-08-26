package team.gutterteam123.starter;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.JSONObject;
import team.gutterteam123.baselib.FileConstants;
import team.gutterteam123.baselib.PortConstants;
import team.gutterteam123.baselib.argparser.ArgumentBuilder;
import team.gutterteam123.baselib.argparser.Parameter;
import team.gutterteam123.netlib.impl.ConfigClient;
import team.gutterteam123.starter.git.GitHelper;
import team.gutterteam123.starter.maven.MavenHelper;
import team.gutterteam123.starter.process.ConfigProcess;
import team.gutterteam123.starter.process.MasterProcess;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class Starter {

    @Getter private static Starter instance;

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

    @Parameter(name = "servergroup", needed = true)
    public String serverGroup;

    private GitHelper gitHelper = new GitHelper();
    private MavenHelper mavenHelper = new MavenHelper(FileConstants.getREPO());
    private ConfigClient configClient;

    private Starter(String[] args) throws IOException, GitAPIException, InterruptedException {
        instance = this;
        BasicConfigurator.configure();
        loadConfig();
        new ArgumentBuilder().setInput(args).setObject(this).build();

        configClient = new ConfigClient(new InetSocketAddress(configServer, PortConstants.getCONFIG_DOWNLOAD()));

        CredentialsProvider cp = new UsernamePasswordCredentialsProvider(gitUser, gitPass);

        if (!FileConstants.getREPO().exists()) {
            FileConstants.getREPO().mkdirs();
            gitHelper.gitClone(FileConstants.getREPO());
            buildMaven();
        } else {
            MergeResult.MergeStatus status = gitHelper.gitPull(FileConstants.getREPO(), branch, cp);
            if (status != MergeResult.MergeStatus.ALREADY_UP_TO_DATE) {
                buildMaven();
            } else {
                System.out.println("Building skipped current jars are up-to-date");
            }
        }
        
        if (config) {
            new ConfigProcess().start();
        }
        configClient.setOnConfigChange(s -> {
            System.out.println("Config Updated!");
        });
        configClient.start();
        configClient.join();

        new MasterProcess().start();

    }

    private void loadConfig() throws IOException {
        if (FileConstants.getCONFIG().exists()) {
            JSONObject object = new JSONObject(FileUtils.readFileToString(FileConstants.getCONFIG(), Charset.forName("Utf-8")));
            branch = object.getString("branch");
        }
    }

    private void buildMaven() {
        System.out.println("Installing All Modules...");
        mavenHelper.install(FileConstants.getREPO());
        System.out.println("Finished Building 3 Files!");
    }


}
