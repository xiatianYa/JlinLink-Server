package com.jinlink.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 自定义业务异常
 *
 * @Author summer
 */
@Getter
@Setter
public class JinLinkException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5693633393981860488L;

    /**
     * 错误信息
     */
    private final String msg;

    public JinLinkException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
