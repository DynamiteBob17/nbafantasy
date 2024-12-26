package hr.mlinx.io.csv;

import java.util.Arrays;

public class CSVLineGenerator {
    private static CSVLineGenerator csvLineGenerator = null;

    private CSVLineGenerator() {
    }

    public static CSVLineGenerator getInstance() {
        if (csvLineGenerator != null) return csvLineGenerator;

        csvLineGenerator = new CSVLineGenerator();
        return csvLineGenerator;
    }

    public String generateStringValues(String... values) {
        return String.join(",", values);
    }

    private String generateValues(String format, Object... values) {
        String[] formats = new String[values.length];
        Arrays.fill(formats, format);
        return String.join(",", formats).formatted(values);
    }

    public String generateDoubleValues(int precision, Double... values) {
        return generateValues("%." + precision + "f", (Object[]) values);
    }

    public String generateDoubleValues(Double... values) {
        return generateDoubleValues(2, values);
    }

}
