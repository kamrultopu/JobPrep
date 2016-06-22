/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.simpledb.client;

/**
 *
 * @author Shams
 */
public class Patient {
    private Long itemNo;
    private String name;
    private String address;

    public Patient(Long itemNo, String name, String address) {
        this.itemNo = itemNo;
        this.name = name;
        this.address = address;
    }

    public Long getItemNo() {
        return itemNo;
    }

    public void setItemNo(Long itemNo) {
        this.itemNo = itemNo;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
