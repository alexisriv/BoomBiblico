package com.sixelasavir.www.boombiblico.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by alexis on 05/09/17.
 */

@Entity
public class GamerRecord {

    @Id(autoincrement = true)
    private Long id;

    private String nameGamer;

    private Integer recordGamer;

    private String timerRecordGamer;

    private String type;

    private Integer level;

    private Integer number;

    @Transient
    private Integer position;



    @Generated(hash = 1726250002)
    public GamerRecord(Long id, String nameGamer, Integer recordGamer,
            String timerRecordGamer, String type, Integer level, Integer number) {
        this.id = id;
        this.nameGamer = nameGamer;
        this.recordGamer = recordGamer;
        this.timerRecordGamer = timerRecordGamer;
        this.type = type;
        this.level = level;
        this.number = number;
    }

    @Generated(hash = 1674413257)
    public GamerRecord() {
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameGamer() {
        return nameGamer;
    }

    public void setNameGamer(String nameGamer) {
        this.nameGamer = nameGamer;
    }

    public Integer getRecordGamer() {
        return recordGamer;
    }

    public void setRecordGamer(Integer recordGamer) {
        this.recordGamer = recordGamer;
    }

    public String getTimerRecordGamer() {
        return timerRecordGamer;
    }

    public void setTimerRecordGamer(String timerRecordGamer) {
        this.timerRecordGamer = timerRecordGamer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
