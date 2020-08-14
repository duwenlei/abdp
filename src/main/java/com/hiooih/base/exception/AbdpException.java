package com.hiooih.base.exception;

/**
 * 自定义异常
 *
 * @author duwenlei
 **/
public class AbdpException extends Exception {
    public AbdpException(String message) {
        super(message);
    }

    public AbdpException(String message, Throwable cause) {
        super(message, cause);
    }
}
