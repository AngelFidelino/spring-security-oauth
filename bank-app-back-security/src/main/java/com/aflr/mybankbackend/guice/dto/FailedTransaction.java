package com.aflr.mybankbackend.guice.dto;

import java.time.Instant;

public class FailedTransaction {
    private Long transactionId;
    private Long timestamp;
    private String reason;
    private int hide;
    private String origin;

    public FailedTransaction() {}

    public FailedTransaction(long transactionId, String reason, String origin) {
        this.transactionId = transactionId;
        this.timestamp = Instant.now().toEpochMilli();
        this.reason = reason;
        this.hide = 0;
        this.origin = origin;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public FailedTransaction setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public FailedTransaction setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public FailedTransaction setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public int getHide() {
        return hide;
    }

    public FailedTransaction setHide(int hide) {
        this.hide = hide;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public FailedTransaction setOrigin(String origin) {
        this.origin = origin;
        return this;
    }
}
