    from os import listdir
    from os.path import isfile, join


    mypath = "/home/david/Desktop/Programieren/java/ProcessingInJava/libs";
    mavenString = "";
    onlyfiles = [f for f in listdir(mypath) if isfile(join(mypath, f))]
    for file in onlyfiles:
        mavenString += """<dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>""" + file[:-4] + """</artifactId>
        <version>${lwjgl.version}</version>
        <scope>system</scope>
        <systemPath>${pom.basedir}/libs/""" + file + """</systemPath>
    </dependency>\n""";

    print mavenString;