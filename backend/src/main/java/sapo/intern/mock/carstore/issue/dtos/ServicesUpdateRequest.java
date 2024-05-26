package sapo.intern.mock.carstore.issue.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServicesUpdateRequest {
    @NotNull(message = "Mã dịch vụ  không được rỗng")
    @Size(min = 1, max = 50, message = "Mã dịch vụ phải có từ 1 đến 50 ký tự")
    String servicesCode;

    @NotNull(message = "Tên không được rỗng")
    @Size(min = 1, max = 100, message = "Tên phải có từ 1 đến 100 ký tự")
    String name;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải là số và lớn hơn 1")
    Double price;
    String urlImage;

    @NotNull
    @Min(value = 1, message = "Số lượng phải là số và lớn hơn 1")
    Integer quantity;
    String description;
}
