package by.intexsoft.restlibrary.util;

import by.intexsoft.restlibrary.model.enumeration.FileExtension;
import org.apache.commons.io.FilenameUtils;

import java.util.Arrays;

public class FileResolver {
    public static FileExtension resolveExtension(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename must not be null.");
        }
        String extension = FilenameUtils.getExtension(filename);
        boolean isKnownExtension = Arrays.stream(FileExtension.values())
                .anyMatch(fileExtension -> fileExtension.getExtension().equals(extension));
        if (!isKnownExtension) {
            throw new IllegalArgumentException("Can't resolve file extension. " + "Filename = " + filename + ".");
        }
        return FileExtension.forString(extension);
    }
}
