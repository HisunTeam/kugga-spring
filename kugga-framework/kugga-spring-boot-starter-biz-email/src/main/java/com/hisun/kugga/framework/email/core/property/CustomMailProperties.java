package com.hisun.kugga.framework.email.core.property;

import org.springframework.boot.autoconfigure.mail.MailProperties;

/**
 * @author: zhou_xiong
 */
public class CustomMailProperties extends MailProperties {

    private Boolean isAvailable;

    private Integer weight;

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "CustomMailProperties{" +
                "isAvailable=" + isAvailable +
                ", weight=" + weight +
                '}';
    }
}
