package com.example.productservice.common.exception;

public class InvalidOtpException extends RuntimeException {
    private Integer blocPeriod;
    private Integer nombreTentativeRestantes;
    private int status;
    private String error;

    public InvalidOtpException() {
    }

    public void InvalidOtpException(String error, Integer blocPeriod, Integer nombreTentativeRestantes, int status) {
        this.error = error;
        this.blocPeriod = blocPeriod;
        this.nombreTentativeRestantes = nombreTentativeRestantes;
        this.status = status;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getBlocPeriod() {
        return this.blocPeriod;
    }

    public void setBlocPeriod(Integer blocPeriod) {
        this.blocPeriod = blocPeriod;
    }

    public Integer getNombreTentativeRestantes() {
        return this.nombreTentativeRestantes;
    }

    public void setNombreTentativeRestantes(Integer nombreTentativeRestantes) {
        this.nombreTentativeRestantes = nombreTentativeRestantes;
    }
}
