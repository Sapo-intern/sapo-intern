// import React from 'react';

import { useState } from "react";
import { useNavigate } from "react-router-dom";
import auth from "../../api/auth";
import Swal from "sweetalert2";
import { useForm } from "react-hook-form";

const ForgotPassword = () => {
  const { register, handleSubmit, formState: { errors } } = useForm();
  
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      const response = await auth.forgotPassword(data.email);

      Swal.fire({
        title: "Success!",
        text: "Link đặt lại mật khẩu đã gửi đến email của bạn",
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
                  <h3>Quên mật khẩu</h3>
                </div>
                <form onSubmit={handleSubmit(onSubmit)}>
                  <div className="form-group first mb-4">
                    <label htmlFor="email">Email</label>
                    <input
                      type="text"
                      className="form-control"
                      id="email"
                      {...register("email", { required: true })}
                    />
                  </div>
                    {errors.email && <span className="error ">Email không được để trống</span>}
                  
                  <input
                    type="submit"
                    value="Gửi"
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

export default ForgotPassword;
