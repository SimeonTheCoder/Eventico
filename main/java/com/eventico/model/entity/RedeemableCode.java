package com.eventico.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "codes")
public class RedeemableCode extends BaseEntity{
    @NotNull
    private String code;
    @NotNull
    private boolean used;

    @NotNull
    private int value;

    public RedeemableCode(String code, boolean used, int value) {
        this.code = code;
        this.used = used;
        this.value = value;
    }

    public RedeemableCode() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
