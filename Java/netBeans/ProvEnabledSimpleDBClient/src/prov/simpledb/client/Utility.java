/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.simpledb.client;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 *
 * @author Shams
 */
public class Utility {
    public static void genFile(int noOfRecord, String fileName) {
        String n = "name";
        String add = "address";

        CSVPrinter csvFilePrinter = null;
        FileWriter fileWriter = null;
        String NEW_LINE_SEPARATOR = "\n";
        //Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

        try {

            fileWriter = new FileWriter(fileName);

            //initialize CSVPrinter object 
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

            //Write a new student object list to the CSV file
            for (long i = 1; i <= noOfRecord; i++) {
                List record = new ArrayList();
                record.add(String.valueOf(i));
                record.add(n + "-" + i);
                record.add(add + "-" + i);
                csvFilePrinter.printRecord(record);
            }

            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
                e.printStackTrace();
            }
        }
    }
}
