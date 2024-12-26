package hr.mlinx.io.csv;

public interface CSVPrintable {
    String getCSVHeader(CSVLineGenerator csvLineGenerator);
    String getCSVValues(CSVLineGenerator csvLineGenerator);
}
