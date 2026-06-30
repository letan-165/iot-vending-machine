package com.app.vending.iot.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ORDER_EMPTY(3010, "Đơn hàng phải có ít nhất một sản phẩm", HttpStatus.BAD_REQUEST),
    PRODUCT_OUT_OF_STOCK(3009, "Sản phẩm không đủ số lượng trong kho", HttpStatus.BAD_REQUEST),
    PAYMENT_NOT_FOUND(3008, "Không tìm thấy thanh toán", HttpStatus.NOT_FOUND),
    INVENTORY_LOG_NOT_FOUND(3007, "Không tìm thấy lịch sử kho", HttpStatus.NOT_FOUND),
    MACHINE_NOT_FOUND(3006, "Không tìm thấy máy bán nước", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND(3005, "Không tìm thấy đơn hàng", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(3004, "Không tìm thấy sản phẩm", HttpStatus.NOT_FOUND),
    PRODUCT_EXISTS(3003, "Sản phẩm đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_EXISTS(3002,"Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(3001,"Người dùng không tồn tại", HttpStatus.BAD_REQUEST),

    USER_ROLE_INVALID(2005,"Role không hợp lệ (STAFF, ADMIN)", HttpStatus.BAD_REQUEST),
    ENUM_INVALID(2001,"Giá trị enum không được định nghĩa", HttpStatus.BAD_REQUEST),

    TOKEN_INVALID(1005,"Token không hợp lệ", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004,"Mật khẩu không hợp lệ", HttpStatus.BAD_REQUEST),
    LOGIN_INVALID(1003,"Thông tin đăng nhập không hợp lệ", HttpStatus.BAD_REQUEST),
    AUTHENTICATION(1002,"Token chưa được xác thực", HttpStatus.UNAUTHORIZED),
    AUTHORIZED(1001,"Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    OTHER_ERROL(9999,"Lỗi hệ thống chưa định nghĩa", HttpStatus.INTERNAL_SERVER_ERROR);


    int code;
    String message;
    HttpStatusCode httpStatus;
}
