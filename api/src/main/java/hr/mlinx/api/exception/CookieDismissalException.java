package hr.mlinx.api.exception;

public class CookieDismissalException extends Exception {
    public CookieDismissalException(Throwable cause) {
        super(cause);
    }

    public CookieDismissalException(String msg) {
        super(msg);
    }
}
