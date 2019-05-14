package com.redrock.oauth.entry;

import lombok.Data;

@Data
public class CommonException extends RuntimeException {

    private int code;
    private String msg;

    public CommonException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
