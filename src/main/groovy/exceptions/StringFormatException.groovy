package exceptions

/**
 *
 * @author: WeiWei
 * create: 10:03.
 * description:
 */
class StringFormatException extends Exception{
    StringFormatException(String msg) {
        super(msg)
    }

    StringFormatException(String msg, Throwable cause) {
        super(msg, cause)
    }

    StringFormatException(Throwable cause) {
        super(cause)
    }
}
