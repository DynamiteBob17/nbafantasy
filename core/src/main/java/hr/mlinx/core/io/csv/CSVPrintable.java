package hr.mlinx.core.io.csv;

public interface CSVPrintable {
    String getCSVHeader(CSVLineGenerator csvLineGenerator);
    String getCSVValues(CSVLineGenerator csvLineGenerator);
}
