package exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

    private final String code;
    private final String message;
    private final String data;

    public ApplicationException(String code, String message) {
        this.message = message;
        this.code = code;
        this.data = "";
    }


}
