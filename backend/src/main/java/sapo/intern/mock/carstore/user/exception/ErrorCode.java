package sapo.intern.mock.carstore.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategozied error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1003, "Không đúng định dạng email", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1005, "Người dùng không tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1006, "Mật khẩu cũ không đúng", HttpStatus.BAD_REQUEST),
    PASSWORD_CONFIRMATION_FAILED(1007, "Mật khẩu mới không khớp", HttpStatus.BAD_REQUEST),
    WEAK_PASSWORD(1008, "Mật khẩu phải có cả chữ hoa, chữ thường, số và phải lớn hơn 8 kí tự", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1004, "Username must be at least 4 characters", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1010, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    NO_DATA(1009, "Không có dữ liêu", HttpStatus.BAD_REQUEST),
    INVALID_OR_EXPIRED_TOKEN(1010,"Token không hợp đúng", HttpStatus.BAD_REQUEST)
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
