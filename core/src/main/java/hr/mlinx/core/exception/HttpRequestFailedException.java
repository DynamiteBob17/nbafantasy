package hr.mlinx.core.exception;

public class HttpRequestFailedException extends Exception {
    public HttpRequestFailedException(int responseCode) {
        super("Request failed with status code " + responseCode);
    }
}
