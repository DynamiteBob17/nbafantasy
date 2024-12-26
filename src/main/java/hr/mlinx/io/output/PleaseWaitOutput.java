package hr.mlinx.io.output;

public class PleaseWaitOutput {
    private PleaseWaitOutput() {
    }

    public static void waitFor(String description, String subject) {
        System.out.printf("Please wait, %s %s...%n", description, subject);
    }
}
