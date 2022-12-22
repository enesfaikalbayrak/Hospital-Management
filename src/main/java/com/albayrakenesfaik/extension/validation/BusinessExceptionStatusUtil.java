package com.albayrakenesfaik.extension.validation;

import org.zalando.problem.Status;

public class BusinessExceptionStatusUtil {

    public static Status getExceptionStatus(BusinessExceptionKey businessExceptionKey) {
        switch (businessExceptionKey) {
            case REQUEST_PARAMS_MISSING:
                return Status.BAD_REQUEST;
            case NOT_AUTHORIZED:
                return Status.UNAUTHORIZED;
            case NOT_FOUND:
            case KPI_NOT_FOUND:
            case INBOX_NOT_FOUND:
            case ASSIGNEE_NOT_FOUND:
            case ASSIGNED_TASK_NOT_FOUND:
            case ASSIGNEE_INBOX_NOT_FOUND:
            case GOAL_NOT_FOUND:
            case INBOX_TARGET_NOT_FOUND:
                return Status.NOT_FOUND;
            case KPI_IN_USE:
            case ASSIGNEE_ALREADY_EXIST:
            case ASSIGNEE_INBOX_ALREADY_EXIST:
                return Status.NOT_ACCEPTABLE;
            case OPTION_VALUE_NULL:
            case JSON_PARSE_ERROR:
            case TIME_NOT_VALID:
            case STATUS_NOT_VALID:
                return Status.INTERNAL_SERVER_ERROR;
            default:
                return Status.BAD_REQUEST;
        }
    }
}