package exceptions

/**
 *
 * @author: weiwei
 * create: 10:34.
 * description:
 */
class NoSuchRequestTypeException extends Exception {

    NoSuchRequestTypeException(String msg) {
        super(msg)
    }

    NoSuchRequestTypeException(String msg, Throwable cause) {
        super(msg, cause)
    }

    NoSuchRequestTypeException(Throwable cause) {
        super(cause)
    }
}
