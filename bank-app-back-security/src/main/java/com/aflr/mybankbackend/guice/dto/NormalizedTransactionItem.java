package com.aflr.mybankbackend.guice.dto;

import io.micrometer.common.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class NormalizedTransactionItem {
    private Integer id;
    private long transactionId;
    private String sku;
    private String category;
    private String additionalInfo;
    private Integer quantity;
    private BigDecimal dollarAmount;

    public Integer getId() {
        return id;
    }

    public NormalizedTransactionItem setId(Integer id) {
        this.id = id;
        return this;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public NormalizedTransactionItem setTransactionId(long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public NormalizedTransactionItem setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public NormalizedTransactionItem setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public NormalizedTransactionItem setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public BigDecimal getDollarAmount() {
        return dollarAmount;
    }

    public NormalizedTransactionItem setDollarAmount(BigDecimal dollarAmount) {
        this.dollarAmount = dollarAmount;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public NormalizedTransactionItem setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void set(String amsField, Class<?> amsFieldType, Object value, String format) throws
            InvocationTargetException, IllegalAccessException {

        Method setter = findSetter("set" + amsField);

        //need to cast based on type
        if (value != null) {
            if (amsFieldType == String.class) {
                setter.invoke(this, value.toString());
            } else if (amsFieldType == BigDecimal.class) {
                if (StringUtils.isNotBlank(value.toString())) {
                    setter.invoke(this, new BigDecimal(value.toString().replaceAll(",", "")));
                }
            } else if (amsFieldType == Integer.class) {
                if (StringUtils.isNotBlank(value.toString())) {
                    setter.invoke(this, Integer.parseInt(value.toString()));
                }
            } else {
                throw new RuntimeException("AMSFieldType not accepted");
            }
        }
    }

    private Method findSetter(String methodName) {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase(methodName)) {
                return method;
            }
        }
        return null;
    }
}
