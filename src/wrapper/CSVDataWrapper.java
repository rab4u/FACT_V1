/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;

import engines.CSVSQLEngine;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class CSVDataWrapper {
    
    static Properties csvprops;
    private CSVSQLEngine csvse;
     
    public CSVDataWrapper(){
         
         csvprops = new java.util.Properties();

     }
     
    public void SetConnection(String localpathwithfilename) throws Exception{   
        //create csv engine object
        if(!csvprops.isEmpty()){
            csvse = new CSVSQLEngine(localpathwithfilename,csvprops);   
        }
        else{
            csvse = new CSVSQLEngine(localpathwithfilename); 
        }
    }
     
    public void setSeparator(String separator) {
        csvprops.put("separator", separator);
    }

    public void supressHeader(boolean flag) {
        csvprops.put("suppressHeaders", flag);
    }

    public void fileExtension(String ext) {
        csvprops.put("fileExtension", "." + ext);
    }

    public void setHeaderLine(String header) {
        csvprops.put("suppressHeaders", "true");
        csvprops.put("headerline", header);
    }
    
    public List getData(String qry) throws SQLException{
       
        List data = new ArrayList();

        ResultSet rs = csvse.getResultSet(qry);
        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();
        
       // System.out.println("Column Count :"+colCount);
        
        while (rs.next()) {
            List row = new ArrayList();
            for (int col = 1; col <= colCount; col++) {
                row.add(rs.getObject(col));
            }
            data.add(row);
        }
        rs.close();
        csvse.close();

        return data;
    }
    
    public static void main(String args[]) throws Exception{
        
        CSVDataWrapper csvdataobj = new CSVDataWrapper();
        
        csvdataobj.setHeaderLine("Product_id,Product_name,Quantity,Price,group,flag_1,flag_2,flag_3,start_dt,end_dt");
        csvdataobj.SetConnection("E:\\CSV AUTOMATION\\CSV-Files\\output\\Test1\\0605category.csv");
        
        
        List data = csvdataobj.getData("select * from 0605category order by product_id limit 10");
        
        System.out.println(data);
        
    }
}