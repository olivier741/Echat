package com.tatsinktech.echat.model;

/**
 * Feedback the message entity registered by the front end ajax
 */
public class ReplyRegistMessage {

    public static final Integer USER_NAME_EXIST = 1; //The registered name already exists
    private boolean successed; //whether the registration is successful
    private Integer errStatus; //Cause of errors

    public ReplyRegistMessage(boolean isSuccessed) {
        this.successed = isSuccessed;
    }

    public ReplyRegistMessage(boolean isSuccessed, Integer errStatus) {
        this.successed = isSuccessed;
        this.errStatus = errStatus;
    }

    public boolean isSuccessed() {
        return successed;
    }

    public void setSuccessed(boolean successed) {
        successed = successed;
    }

    public Integer getErrStatus() {
        return errStatus;
    }

    public void setErrStatus(Integer errStatus) {
        this.errStatus = errStatus;
    }
}
