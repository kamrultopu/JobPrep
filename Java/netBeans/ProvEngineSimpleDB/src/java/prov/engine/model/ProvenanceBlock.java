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
public class ProvenanceBlock {
    
    private Long blockId;
    private String objectId;
    private String currentHC;
    private String blockChain;
    private String provChainSigma;
    private String blockHash;
    private Long onewayAccId;
    private String blockGenTime;

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCurrentHC() {
        return currentHC;
    }

    public void setCurrentHC(String currentHC) {
        this.currentHC = currentHC;
    }

    public String getBlockChain() {
        return blockChain;
    }

    public void setBlockChain(String blockChain) {
        this.blockChain = blockChain;
    }

    public String getProvChainSigma() {
        return provChainSigma;
    }

    public void setProvChainSigma(String provChainSigma) {
        this.provChainSigma = provChainSigma;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public Long getOnewayAccId() {
        return onewayAccId;
    }

    public void setOnewayAccId(Long onewayAccId) {
        this.onewayAccId = onewayAccId;
    }

    public String getBlockGenTime() {
        return blockGenTime;
    }

    public void setBlockGenTime(String provTime) {
        this.blockGenTime = provTime;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(objectId);
        sb.append(blockGenTime);
        sb.append(blockChain);
        sb.append(currentHC);
        sb.append(provChainSigma);
        return sb.toString();
    }
    
    public String getHash()
    {
        return CryptoUtil.getHash(this.toString(), Config.Instanc().HashAlgorithm());
    }
    
}
