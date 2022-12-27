package com.albayrakenesfaik.extension.validation;

import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.Map;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1999445721841283571L;
    private static final String appName = "inboxServerApp";
    private String entity;
    private String notificationType;
    private String errorMessage;
    private BusinessExceptionKey messageKey;
    private Map<String, Object> params;

    public BusinessException(BusinessExceptionKey messageKey) {
        super();
        this.messageKey = messageKey;
        this.notificationType = "toast";
    }

    public BusinessException(BusinessExceptionKey messageKey, String message) {
        super(message);
        this.messageKey = messageKey;
        this.errorMessage = message;
    }

    public BusinessException(BusinessExceptionKey messageKey, Throwable cause) {
        super(cause);
        this.messageKey = messageKey;
    }

    public BusinessException(BusinessExceptionKey messageKey, String message, Throwable cause) {
        super(message, cause);
        this.messageKey = messageKey;
        this.errorMessage = message;
    }

    public BusinessException(BusinessExceptionKey messageKey, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.messageKey = messageKey;
        this.errorMessage = message;
    }

    public BusinessException withEntity(String entityName) {
        this.entity = entityName;
        return this;
    }

    public BusinessException withNotificationType(String notificationType) {
        this.notificationType = notificationType;
        return this;
    }

    public BusinessException withParam(String key, Object value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return this;
        }
        if (null == this.params) {
            this.params = new HashMap<>();
        }
        this.params.put(key, value);
        return this;
    }

    public String getNotificationType() {
        return this.notificationType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public BusinessExceptionKey getMessageKey() {
        return this.messageKey;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public String getMessageKeyText() {
        if (StringUtils.isEmpty(this.entity)) {
            return appName + ".notification." + this.messageKey.name();
        } else {
            return appName + "." + this.entity + ".notification." + this.messageKey.name();
        }
    }
}
