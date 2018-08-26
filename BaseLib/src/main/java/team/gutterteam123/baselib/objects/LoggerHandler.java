package team.gutterteam123.baselib.objects;

import org.apache.commons.io.IOUtils;
import org.apache.maven.shared.invoker.InvocationOutputHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LoggerHandler implements InvocationOutputHandler {

        private static final String LS = System.getProperty("line.separator");

        private final File output;

        private FileWriter writer;

        public LoggerHandler(File logFile) {
            output = logFile;
        }

        public void consumeLine(String line) {
            if (writer == null)  {
                try {
                    output.getParentFile().mkdirs();
                    writer = new FileWriter( output );
                } catch (IOException e) {
                    throw new IllegalStateException("Failed to open build log: " + output + "\n\nError: "
                        + e.getMessage() );
                }
            }

            try {
                writer.write( line + LS );
                writer.flush();
            } catch (IOException e) {
                throw new IllegalStateException( "Failed to write to build log: " + output + " output:\n\n\'" + line
                    + "\'\n\nError: " + e.getMessage() );
            }
        }

        public void close() {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}