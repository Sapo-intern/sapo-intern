package sapo.intern.mock.carstore.global.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApiResponse<T> {
    private String code;
    private T data;

    public ApiResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }


}
