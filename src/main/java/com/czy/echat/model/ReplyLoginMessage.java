package com.czy.echat.model;

/**
 * Feedback the information entity of the front-end ajax login
 */
public class ReplyLoginMessage {

    public static final Integer USER_NAME_NOT_EXIST = 1; //The currently logged in user name has not been registered
    public static final Integer USER_PASSWORD_WRONG = 2; //Login password is wrong
    public static final Integer USER_NAME_OR_PASSWORD_NULL = 3; //User name or password is not filled in
    private boolean successed; //Whether the login is successful
    private Integer errStatus; //Cause of error

    public ReplyLoginMessage(boolean successed) {
        this.successed = successed;
    }

    public ReplyLoginMessage(boolean successed, Integer errStatus) {
        this.successed = successed;
        this.errStatus = errStatus;
    }

    public boolean isSuccessed() {
        return successed;
    }

    public void setSuccessed(boolean successed) {
        this.successed = successed;
    }

    public Integer getErrStatus() {
        return errStatus;
    }

    public void setErrStatus(Integer errStatus) {
        this.errStatus = errStatus;
    }
}
