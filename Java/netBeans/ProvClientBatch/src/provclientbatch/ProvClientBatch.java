/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provclientbatch;

import java.io.IOException;
import java.util.Random;

/**
 *
 * @author misty
 */
public class ProvClientBatch {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Random rnd = new Random(System.currentTimeMillis());
        String[] option = {"pi","ri"};
        int recordNo = 3500;
        int userNo = 1;
        String filename = "data_option_" + option[0] + "_NoOfUser_"+Integer.toString(userNo);
        
        for( int i = 1 ; i <= userNo; i ++){
            String username = "user_" + Integer.toString(i);
            String tempfilename = username+"_"+filename +".txt";
            Runtime.getRuntime().exec("cmd /c start D:\\Java\\netBeans\\provClient " + option[0] + " " + Integer.toString(recordNo) + " "+  username + " " + Integer.toString(i) + " " + tempfilename);
        }
        
    }
    
}
