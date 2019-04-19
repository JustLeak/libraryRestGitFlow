package by.intexsoft.restlibrary.model.enumeration;

public enum FileExtension {
    XLS("xls"), XLSX("xlsx");

    private final String extension;

    FileExtension(String format) {
        extension = format;
    }

    public static FileExtension forString(String format) {
        for (FileExtension type : values()) {
            if (type.extension.equals(format)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid file extension: " + format + ".");
    }

    public String getExtension() {
        return extension;
    }
}
