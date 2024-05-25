package sapo.intern.mock.carstore.global.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategozied error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1013, "Email đã tồn tại", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1003, "Không đúng định dạng email", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1004, "Username phải có 4 trở lên ", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1005, "Người dùng không tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1006, "Mật khẩu cũ không đúng", HttpStatus.BAD_REQUEST),
    PASSWORD_CONFIRMATION_FAILED(1007, "Mật khẩu mới không khớp", HttpStatus.BAD_REQUEST),
    WEAK_PASSWORD(1008, "Mật khẩu phải có cả chữ hoa, chữ thường, số và phải lớn hơn 8 kí tự", HttpStatus.BAD_REQUEST),
    NO_DATA(1009, "Không có dữ liêu", HttpStatus.BAD_REQUEST),
    INVALID_OR_EXPIRED_TOKEN(1010,"Token không hợp đúng", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1011, "Bạn chưa đăng nhập", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1012, "Bạn không có quyền", HttpStatus.FORBIDDEN),
    PRODUCT_EXISTED(1013, "Sản phẩm đã có", HttpStatus.BAD_REQUEST),
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
