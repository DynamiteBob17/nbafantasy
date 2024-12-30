package hr.mlinx.core.io.csv;

public class CSVGenerator {
    private static final CSVGenerator INSTANCE = new CSVGenerator();
    private final CSVLineGenerator csvLineGenerator;

    private CSVGenerator() {
        this.csvLineGenerator = CSVLineGenerator.getInstance();
    }

    public static CSVGenerator getInstance() {
        return INSTANCE;
    }

    public String generateCompleteCSV(CSVPrintable csvPrintable) {
        return csvPrintable.getCSVHeader(csvLineGenerator) + '\n' +
                csvPrintable.getCSVValues(csvLineGenerator);
    }

    public String generateCompleteCSV(CSVPrintable csvPrintableHeaderDefinition, CSVPrintable... csvPrintables) {
        StringBuilder csv = new StringBuilder();

        csv.append(csvPrintableHeaderDefinition.getCSVHeader(csvLineGenerator)).append('\n');

        for (CSVPrintable csvPrintable : csvPrintables) {
            csv.append(csvPrintable.getCSVValues(csvLineGenerator)).append('\n');
        }

        return csv.toString();
    }

}
