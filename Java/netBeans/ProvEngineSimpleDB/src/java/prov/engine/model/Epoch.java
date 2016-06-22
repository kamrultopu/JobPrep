/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.engine.model;

import java.util.Date;

/**
 *
 * @author Shams
 */
public class Epoch {
    private Long epochId;
    private Date startTime;
    private Date endTime;
    private String startTimeStr;
    private String endTimeStr;

    public Long getEpochId() {
        return epochId;
    }

    public void setEpochId(Long epochId) {
        this.epochId = epochId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }
        
    public String toString()
    {
        return epochId+":"+startTimeStr+":"+endTimeStr;
    }
    
}
