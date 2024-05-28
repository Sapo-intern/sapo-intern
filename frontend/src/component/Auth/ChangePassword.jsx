import { useNavigate } from "react-router-dom";
import auth from "../../api/auth";
import Swal from "sweetalert2";
import { useForm } from "react-hook-form";
import { useAuth } from "../../Context/ContextAuth";

const ChangePassword = () => {
  const { user } = useAuth();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
       await auth.changePassword({
        email: user.email,
        oldPassword: data.oldPassword,
        newPassword: data.newPassword,
        confirmNewPassword: data.confirmNewPassword
      });

      Swal.fire({
        title: "Success!",
        text: "Đổi mật khẩu thành công",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => navigate("/"));
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: error.response.data.message,
        icon: "error",
        confirmButtonText: "OK",
      });
    }
  };

  return (
    <div className="content">
      <div className="container">
        <div className="row">
          <div className="col-md-6">
            <img
              src="../src/assets/images/undraw_remotely_2j6y.svg"
              alt="Image"
              className="img-fluid"
            />
          </div>
          <div className="col-md-6 contents">
            <div className="row justify-content-center">
              <div className="col-md-8">
                <div className="mb-4">
                  <h3>Đặt lại mật khẩu</h3>
                </div>
                <form onSubmit={handleSubmit(onSubmit)}>
                  <div className="form-group last ">
                    <label htmlFor="oldPassword">Mật khẩu cũ</label>
                    <input
                      type="password"
                      className="form-control"
                      id="oldPassword"
                      {...register("oldPassword", { required: true })}
                    />
                  </div>
                  {errors.password && (
                    <span className="error">Hãy nhập mật khẩu của bạn</span>
                  )}

                  <div className="form-group last ">
                    <label htmlFor="newPassword">Mật khẩu mới</label>
                    <input
                      type="password"
                      className="form-control"
                      id="newPassword"
                      {...register("newPassword", { required: true })}
                    />
                  </div>
                  {errors.password && (
                    <span className="error">Xác nhận lại mật khẩu mới </span>
                  )}

                  <div className="form-group last ">
                    <label htmlFor="confirmNewPassword">Xác nhân mật khẩu</label>
                    <input
                      type="password"
                      className="form-control"
                      id="confirmNewPassword"
                      {...register("confirmNewPassword", { required: true })}
                    />
                  </div>
                  {errors.confirmpassword && (
                    <span className="error">Hãy xác nhận lại mật khẩu</span>
                  )}
                  <input
                    type="submit"
                    value="Xác nhận"
                    className="btn btn-block btn-primary"
                  />
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChangePassword;
