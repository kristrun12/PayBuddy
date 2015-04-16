package com.kla.paybuddy.data;

/**
 * Created by kla on 16.4.2015.
 */
public class TransactionResult {

    private int resultCode;
    private String resultMessage;
    private String resultContent;

    public String getResultContent() {
        return resultContent;
    }

    public void setResultContent(String resultContent) {
        this.resultContent = resultContent;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }


}
