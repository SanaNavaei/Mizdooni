package mizdooni.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.PropertySource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilePropertySource extends PropertySource<File> {
    private Logger logger = LoggerFactory.getLogger(FilePropertySource.class);
    private Pattern PATTERN = Pattern.compile("^file\\((.+)\\)$");

    public FilePropertySource() {
        super("file");
    }

    @Override
    public Object getProperty(String value) {
        Matcher m = PATTERN.matcher(value);
        if (!m.matches()) return null;
        String path = m.group(1);

        try (BufferedReader br = Files.newBufferedReader(Path.of(path))) {
            String fileContent = br.readLine();
            logger.info("Read property from file: {}", fileContent);
            return fileContent;
        } catch (IOException ex) {
            logger.error("Failed to read file for property: {}", value);
        }
        return null;
    }
}
