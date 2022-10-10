package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException{

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }


    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }
    // Throwable cause : 해당 예외가 발생한 근본적인 원인(여기서는 RuntimeException)

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

}
