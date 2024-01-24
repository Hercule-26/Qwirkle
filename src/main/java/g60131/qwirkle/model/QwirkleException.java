package g60131.qwirkle.model;

public class QwirkleException extends java.lang.RuntimeException{
    /*public QwirkleException(String message) {
        throw new RuntimeException("Les tuiles non pas la même couleurs ou la même formes");
    }*/

    public QwirkleException() {
        super();
    }

    public QwirkleException(String message) {
        super(message);
    }

    public QwirkleException(String message, Throwable cause) {
        super(message, cause);
    }

    public QwirkleException(Throwable cause) {
        super(cause);
    }

    protected QwirkleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}