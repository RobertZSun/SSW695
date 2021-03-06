package com.hobbymatcher.entity;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
public class Blog implements Serializable {
    private static final long serialVersionUID = 303252625304650080L;

    private Integer id;
    @NotBlank
    private String title;
    @NotBlank
    private String html;
    private Date onDatetime;
    private Integer clapCount;
    private Integer byUserId;
    private String byUserFirst;
    private String byUserLast;
    private Integer forHobbyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Date getOnDatetime() {
        return onDatetime;
    }

    public void setOnDatetime(Date onDatetime) {
        this.onDatetime = onDatetime;
    }

    public Integer getClapCount() {
        return clapCount;
    }

    public void setClapCount(Integer clapCount) {
        this.clapCount = clapCount;
    }

    public Integer getByUserId() {
        return byUserId;
    }

    public void setByUserId(Integer byUserId) {
        this.byUserId = byUserId;
    }

    public String getByUserFirst() {
        return byUserFirst;
    }

    public String getByUserLast() {
        return byUserLast;
    }

    public Integer getForHobbyId() {
        return forHobbyId;
    }

    public void setForHobbyId(Integer forHobbyId) {
        this.forHobbyId = forHobbyId;
    }
}
