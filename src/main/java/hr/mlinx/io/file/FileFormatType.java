package hr.mlinx.io.file;

public enum FileFormatType {
    @SuppressWarnings("unused") TXT("txt"), JSON(".json"), CSV(".csv");

    private final String fileExtension;

    @SuppressWarnings("unused")
    FileFormatType(String value) {
        fileExtension = value;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
