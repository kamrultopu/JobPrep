/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.engine.model;

import prov.engine.crypto.CryptoUtil;
import prov.engine.util.Config;

/**
 *
 * @author Shams
 */
public class ProvenanceRecord {
    
    private Long provId;
    private String objectId;
    private String userId;
    private String currentStateHash;
    private String stateChangeEnc;
    private String stateChange;
    private Long blockId;
    private String provTime;

    
    public Long getProvId() {
        return provId;
    }

    public void setProvId(Long provId) {
        this.provId = provId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentStateHash() {
        return currentStateHash;
    }

    public void setCurrentStateHash(String currentStateHash) {
        this.currentStateHash = currentStateHash;
    }

    public String getStateChangeEnc() {
        return stateChangeEnc;
    }

    public void setStateChangeEnc(String stateChangeEnc) {
        this.stateChangeEnc = stateChangeEnc;
    }

    public String getStateChange() {
        return stateChange;
    }

    public void setStateChange(String stateChange) {
        this.stateChange = stateChange;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getProvTime() {
        return provTime;
    }

    public void setProvTime(String provTime) {
        this.provTime = provTime;
    }
    
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append(currentStateHash);
        str.append(stateChange);
        str.append(objectId);
        str.append(userId);
        str.append(provTime);
        return str.toString();
    }
    
    public String getHash()
    {
        return CryptoUtil.getHash(this.toString(), Config.Instanc().HashAlgorithm());
    }
}
