import { Link, useNavigate } from "react-router-dom";
import auth from "../../api/auth";
import Swal from "sweetalert2";
import { useForm } from "react-hook-form";
import { useAuth } from "../../Context/ContextAuth";

const Login = () => {
  const { onLoginSuccess } = useAuth();
  const { register, handleSubmit, formState: { errors } } = useForm();
  
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      const response = await auth.login({
        email: data.email,
        password: data.password,
      });

      // onLoginSuccess(response.result.token,response.result.refreshToken,response.result);
      onLoginSuccess(response.result.token, response.result);
  
      Swal.fire({
        title: "Success!",
        text: "Đăng nhập thành công",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => {
        if (response.result.firstLogin === true) {
          navigate("/changepassword");
        } else {
          switch (response.result.role) {
            case "MANAGER":
              navigate("/");
              break;
            case "COORDINATOR":
              navigate("/ticket");
              break;
            case "TECHNICIAN":
              navigate("/issue");
              break;
            default:
              navigate("/error");
              break;
          }
        }
      });
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
                  <h3>Đăng nhập</h3>
                </div>
                <form onSubmit={handleSubmit(onSubmit)}>
                  <div className="form-group first ">
                    <label htmlFor="email">Email</label>
                    <input
                      type="text"
                      className="form-control"
                      id="email"
                      {...register("email", { required: true })}
                    />
                  </div>
                    {errors.email && <span className="error">Email không được để trống</span>}
                  <div className="form-group last ">
                    <label htmlFor="password">Mật khẩu</label>
                    <input
                      type="password"
                      className="form-control"
                      id="password"
                      {...register("password", { required: true })}
                    />
                  </div>
                    {errors.password && <span className="error">Mật khẩu không được để trống</span>}

                  <div className="d-flex mb-5 align-items-center">
                    <span className="ml-auto">
                      <Link to={"/forgotpassword"} className="forgot-pass">
                        Quên mật khẩu
                      </Link>
                    </span>
                  </div>

                  <input
                    type="submit"
                    value="Đăng nhập"
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

export default Login;
