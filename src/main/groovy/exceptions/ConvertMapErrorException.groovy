package exceptions

/**
 *
 * @author: weiwei
 * create: 16:52.
 * description:
 */
class ConvertMapErrorException extends Exception {

    ConvertMapErrorException(String msg) {
        super(msg)
    }

    ConvertMapErrorException(String msg, Throwable cause) {
        super(msg, cause)
    }

    ConvertMapErrorException(Throwable cause) {
        super(cause)
    }
}
