/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fact;

import wrapper.CSVThreadModeler;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Ravindra
 */
public class FACTStartPoint implements Job{

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        
        try {    
            //calling starts here
            
            //FTP code
            //.......
            
            //checkcode
            //.......
          
            
            //CSVThreadModeler
            callModeler();
        
        } catch (Exception ex) {
            Logger.getLogger(FACTStartPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void callModeler() throws Exception{
        String srcfile = "E:\\CSV AUTOMATION\\CSV-Files\\output\\Test1\\triggers0608.csv";
        String trgfile = "E:\\CSV AUTOMATION\\CSV-Files\\output\\Test1\\triggers0609.csv";
        String srcheader = "col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12";
        String trgheader = "col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12";
        String srcsep = ",";
        String trgsep = ",";
        String srcext = ".csv";
        String trgext = ".csv";
        String srcqry = "Select * from triggers0608";
        String trgqry = "Select * from triggers0609";
        String srcsheet = "triggers0608";
        String trgsheet = "triggers0609";
             
        CSVThreadModeler csvtm = new CSVThreadModeler(srcfile,trgfile,srcheader,trgheader,srcsep,trgsep,srcext,trgext);
        List basics = csvtm.writeDiff(srcqry, trgqry, srcsheet, trgsheet);
        
        System.out.println("Basic Info :"+basics);
    }
    
}
