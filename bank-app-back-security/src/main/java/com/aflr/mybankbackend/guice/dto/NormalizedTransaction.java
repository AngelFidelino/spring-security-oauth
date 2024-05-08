package com.aflr.mybankbackend.guice.dto;

import io.micrometer.common.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NormalizedTransaction {

    private long transactionId;
    private int brokerId;
    private int feedType;

    private Integer retailerId;
    private Integer type;
    private String retailerName;
    private String brokerRetailerId;
    private String shoppingToken;
    private Long collectorNumber;
    private Long postingDate;
    private Long transactionDate;
    private BigDecimal amountCAD;
    private BigDecimal amountUSD;
    private String orderId;
    private BigDecimal commissionCAD;
    private BigDecimal commissionUSD;
    private String actionId;
    private String actionName;
    private String actionType;
    private Long lockingDate;
    private String brokerName;
    private String brokerUniqueIdentifier;
    private FailedTransaction failedTransaction;
    private boolean publishTransaction;
    private Long reversalDate;

    //special case fields for Affiliate Window
    private String amountCurrency;
    private String commissionCurrency;
    private boolean replayState = false;
    //added two new field to handled the updated transaction for amount difference, oldSale and oldCommission object string
    //added because of in dateType=transaction api call it would come with null value object
    private Object oldSaleAmountObject;
    private Object oldSaleCommissionAmountObject;

    //special case fields for Partnerize conversion
    private String status;

    private List<NormalizedTransactionItem> items = new ArrayList<>();

    public NormalizedTransaction(long transactionId, int brokerId) {
        this.transactionId = transactionId;
        this.brokerId = brokerId;
        this.publishTransaction = true;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public int getBrokerId() {
        return brokerId;
    }

    public int getFeedType() {
        return feedType;
    }

    public Integer getRetailerId() {
        return retailerId;
    }

    public Integer getType() {
        return type;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public String getBrokerRetailerId() {
        return brokerRetailerId;
    }

    public String getShoppingToken() {
        return shoppingToken;
    }

    public Long getCollectorNumber() {
        return collectorNumber;
    }

    public Long getPostingDate() {
        return postingDate;
    }

    public Long getTransactionDate() {
        return transactionDate;
    }

    public BigDecimal getAmountCAD() {
        return amountCAD;
    }

    public BigDecimal getAmountUSD() {
        return amountUSD;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getActionId() {
        return actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public String getActionType() {
        return actionType;
    }

    public Long getLockingDate() {
        return lockingDate;
    }

    public FailedTransaction getFailedTransaction() {
        return failedTransaction;
    }

    public BigDecimal getCommissionCAD() {
        return commissionCAD;
    }

    public BigDecimal getCommissionUSD() {
        return commissionUSD;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public String getAmountCurrency() {
        return amountCurrency;
    }

    public String getCommissionCurrency() {
        return commissionCurrency;
    }

    public String getBrokerUniqueIdentifier() {
        return brokerUniqueIdentifier;
    }

    public boolean isPublishTransaction() {
        return publishTransaction;
    }

    public NormalizedTransaction setPublishTransaction(boolean publishTransaction) {
        this.publishTransaction = publishTransaction;
        return this;
    }

    public NormalizedTransaction setTransactionId(long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public NormalizedTransaction setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
        return this;
    }

    public NormalizedTransaction setFeedType(Integer feedType) {
        this.feedType = feedType;
        return this;
    }

    public NormalizedTransaction setRetailerId(Integer retailerId) {
        this.retailerId = retailerId;
        return this;
    }

    public NormalizedTransaction setType(Integer type) {
        this.type = type;
        return this;
    }

    public NormalizedTransaction setRetailerName(String retailerName) {
        this.retailerName = retailerName;
        return this;
    }

    public NormalizedTransaction setBrokerRetailerId(String brokerRetailerId) {
        this.brokerRetailerId = brokerRetailerId;
        return this;
    }

    public NormalizedTransaction setShoppingToken(String shoppingToken) {
        this.shoppingToken = shoppingToken;
        return this;
    }

    public NormalizedTransaction setCollectorNumber(Long collectorNumber) {
        this.collectorNumber = collectorNumber;
        return this;
    }

    public NormalizedTransaction setPostingDate(Long postingDate) {
        this.postingDate = postingDate;
        return this;
    }

    public NormalizedTransaction setTransactionDate(Long transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public NormalizedTransaction setAmountCAD(BigDecimal amountCAD) {
        this.amountCAD = amountCAD;
        return this;
    }

    public NormalizedTransaction setAmountUSD(BigDecimal amountUSD) {
        this.amountUSD = amountUSD;
        return this;
    }

    public NormalizedTransaction setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public NormalizedTransaction setActionId(String actionId) {
        this.actionId = actionId;
        return this;
    }

    public NormalizedTransaction setActionName(String actionName) {
        this.actionName = actionName;
        return this;
    }

    public NormalizedTransaction setActionType(String actionType) {
        this.actionType = actionType;
        return this;
    }

    public NormalizedTransaction setLockingDate(Long lockingDate) {
        this.lockingDate = lockingDate;
        return this;
    }

    public NormalizedTransaction setCommissionCAD(BigDecimal commissionCAD) {
        this.commissionCAD = commissionCAD;
        return this;
    }

    public NormalizedTransaction setCommissionUSD(BigDecimal commissionUSD) {
        this.commissionUSD = commissionUSD;
        return this;
    }

    public NormalizedTransaction setBrokerName(String brokerName) {
        this.brokerName = brokerName;
        return this;
    }

    public NormalizedTransaction setFailedTransaction(FailedTransaction failedTransaction) {
        this.failedTransaction = failedTransaction;
        return this;
    }

    public NormalizedTransaction setAmountCurrency(String amountCurrency) {
        this.amountCurrency = amountCurrency;
        return this;
    }

    public Long getReversalDate() {
        return reversalDate;
    }

    public NormalizedTransaction setReversalDate(Long reversalDate) {
        this.reversalDate = reversalDate;
        return this;
    }

    public NormalizedTransaction setCommissionCurrency(String commissionCurrency) {
        this.commissionCurrency = commissionCurrency;
        return this;
    }

    public NormalizedTransaction setBrokerUniqueIdentifier(String brokerUniqueIdentifier) {
        this.brokerUniqueIdentifier = brokerUniqueIdentifier;
        return this;
    }

    public boolean isReplayState() {
        return replayState;
    }

    public NormalizedTransaction setReplayState(boolean replayState) {
        this.replayState = replayState;
        return this;
    }

    public List<NormalizedTransactionItem> getItems() {
        return items;
    }

    public NormalizedTransaction setItems(List<NormalizedTransactionItem> items) {
        this.items = items;
        return this;
    }

    public NormalizedTransaction addItem(NormalizedTransactionItem item) {
        this.items.add(item);
        return this;
    }

    public Object getOldSaleAmountObject() {
        return oldSaleAmountObject;
    }

    public NormalizedTransaction setOldSaleAmountObject(Object oldSaleAmountObject) {
        this.oldSaleAmountObject = oldSaleAmountObject;
        return this;
    }

    public Object getOldSaleCommissionAmountObject() {
        return oldSaleCommissionAmountObject;
    }

    public NormalizedTransaction setOldSaleCommissionAmountObject(Object oldSaleCommissionAmountObject) {
        this.oldSaleCommissionAmountObject = oldSaleCommissionAmountObject;
        return this;
    }

    private Instant parseDateStrToInstant(String formattedDate, String format) {
        if (StringUtils.isEmpty(format))
            format = "uuuu-MM-dd\'T\'HH:mm:ss\'Z\'";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        //if format defines time zone offset, we don't append UTC time zone
        if (!format.contains("x") && !format.contains("X"))
            dateTimeFormatter = dateTimeFormatter.withZone(ZoneId.of("UTC"));
        TemporalAccessor temporalAccessor = dateTimeFormatter.parse(formattedDate);
        if (temporalAccessor.isSupported(ChronoField.HOUR_OF_DAY)) {
            return Instant.from(temporalAccessor);
        } else {
            LocalDate localDate = LocalDate.parse(formattedDate, dateTimeFormatter);
            return localDate.atTime(LocalTime.of(12, 0, 0)).atZone(ZoneId.of("UTC", ZoneId.SHORT_IDS)).toInstant();
        }
    }

    private Method findSetter(String methodName) {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods)
            if (method.getName().equalsIgnoreCase(methodName))
                return method;
        return null;
    }

    public void set(String amsField, Class<?> amsFieldType, Object value, String format)
            throws InvocationTargetException, IllegalAccessException {

        Method setter = findSetter("set" + amsField);

        //need to cast based on type
        if (value != null) {
            if (amsFieldType == Timestamp.class) {
                if (StringUtils.isNotBlank(value.toString()))
                    setter.invoke(this, parseDateStrToInstant(value.toString(), format).toEpochMilli());
            } else if (amsFieldType == String.class) {
                setter.invoke(this, value.toString());
            } else if (amsFieldType == BigDecimal.class) {
                if (StringUtils.isNotBlank(value.toString()))
                    setter.invoke(this, new BigDecimal(value.toString().replaceAll(",", "")));
            } else if (amsFieldType == Integer.class) {
                if (StringUtils.isNotBlank(value.toString()))
                    setter.invoke(this, Integer.parseInt(value.toString()));
            } else if (amsFieldType == Object.class) {
                // use to make sure parsed value keep in json and used letter to get field for AWIN etc.
                setter.invoke(this, value);
            } else {
                throw new RuntimeException("AMSFieldType not accepted");
            }
        }
    }

    @Override
    public String toString() {
        return "NormalizedTransaction{" +
                "transactionId=" + transactionId +
                ", brokerId=" + brokerId +
                ", retailerId=" + retailerId +
                ", type=" + type +
                ", retailerName='" + retailerName + '\'' +
                ", brokerRetailerId=" + brokerRetailerId +
                ", shoppingToken='" + shoppingToken + '\'' +
                ", collectorNumber=" + collectorNumber +
                ", postingDate=" + postingDate +
                ", transactionDate=" + transactionDate +
                ", amountCAD=" + amountCAD +
                ", amountUSD=" + amountUSD +
                ", orderId='" + orderId + '\'' +
                ", commissionCAD=" + commissionCAD +
                ", commissionUSD=" + commissionUSD +
                ", actionId='" + actionId + '\'' +
                ", actionName='" + actionName + '\'' +
                ", actionType='" + actionType + '\'' +
                ", lockingDate=" + lockingDate +
                ", brokerName='" + brokerName + '\'' +
                ", reversalDate='" + reversalDate + '\'' +
                ", failedTransaction=" + failedTransaction + '\'' +
                ", amountCurrency=" + amountCurrency + '\'' +
                ", commissionCurrency=" + commissionCurrency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        NormalizedTransaction that = (NormalizedTransaction) o;
        return transactionId == that.transactionId &&
                brokerId == that.brokerId &&
                feedType == that.feedType &&
                publishTransaction == that.publishTransaction &&
                Objects.equals(retailerId, that.retailerId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(retailerName, that.retailerName) &&
                Objects.equals(brokerRetailerId, that.brokerRetailerId) &&
                Objects.equals(shoppingToken, that.shoppingToken) &&
                Objects.equals(collectorNumber, that.collectorNumber) &&
                Objects.equals(postingDate, that.postingDate) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(amountCAD, that.amountCAD) &&
                Objects.equals(amountUSD, that.amountUSD) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(commissionCAD, that.commissionCAD) &&
                Objects.equals(commissionUSD, that.commissionUSD) &&
                Objects.equals(actionId, that.actionId) &&
                Objects.equals(actionName, that.actionName) &&
                Objects.equals(actionType, that.actionType) &&
                Objects.equals(lockingDate, that.lockingDate) &&
                Objects.equals(brokerName, that.brokerName) &&
                Objects.equals(reversalDate, that.reversalDate) &&
                Objects.equals(failedTransaction, that.failedTransaction) &&
                Objects.equals(amountCurrency, that.amountCurrency) &&
                Objects.equals(commissionCurrency, that.commissionCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, brokerId, retailerId, type, retailerName, brokerRetailerId, shoppingToken,
                collectorNumber, postingDate, transactionDate, amountCAD, amountUSD, orderId, commissionCAD,
                commissionUSD, actionId, actionName, actionType, lockingDate, brokerName, failedTransaction);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
