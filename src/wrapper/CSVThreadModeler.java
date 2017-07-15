package wrapper;

import engines.ComparisonEngine;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import wrapper.CSVDataWrapper;
import wrapper.SaveToExcel;

public class CSVThreadModeler {

    CSVDataWrapper csvwarpsrc, csvwarptrg;
    ComparisonEngine ce;
    SaveToExcel ste;
    String srcheader;
    String trgheader;

    public CSVThreadModeler(String srcfilenamewithpath, String trgfilenamewithpath,String srcheader, String trgheader,  String srcseparator, String trgseparator, String srcfileextension, String trgfileextension) throws Exception {

        //check the param
        if (srcheader.isEmpty() || trgheader.isEmpty() || srcfilenamewithpath.isEmpty() || trgfilenamewithpath.isEmpty() || srcseparator.isEmpty() || trgseparator.isEmpty() || srcfileextension.isEmpty() || trgfileextension.isEmpty()) {
            
            System.out.println("[Error] : Missing Source or Target Information");
            throw new Exception("[Error] : Missing Source or Target Information");
            
        }
        else{
            
            this.srcheader = srcheader;
            this.trgheader = trgheader;
           
            //src
            csvwarpsrc = new CSVDataWrapper();
            csvwarpsrc.setHeaderLine(srcheader);
            csvwarpsrc.SetConnection(srcfilenamewithpath);
            csvwarpsrc.setSeparator(srcseparator);
            csvwarpsrc.fileExtension(srcfileextension);
            
            //trg
            csvwarptrg = new CSVDataWrapper();
            csvwarptrg.setHeaderLine(trgheader);
            csvwarptrg.SetConnection(trgfilenamewithpath);
            csvwarptrg.setSeparator(trgseparator);
            csvwarptrg.fileExtension(trgfileextension);
            
            //Comparsion
            ce = new ComparisonEngine();
            
            //save file
            System.out.println(srcfilenamewithpath);
            ste = new SaveToExcel(srcfilenamewithpath.split(".csv")[0]);

        }

    }
    
    public List writeDiff(String srcqry,String trgqry,String srcsheetname,String trgsheetname) throws SQLException, Exception{
        
         List basics =  new ArrayList();
        
        //get data
        List srcdata = csvwarpsrc.getData(srcqry);
        List trgdata = csvwarptrg.getData(trgqry);

        //comparison
        List srcdiff = ce.getUnSrcData(srcdata, trgdata);
        List trgdiff = ce.getUnTrgData(srcdata, trgdata);

        //basics
        basics.add(srcdata.size());
        basics.add(trgdata.size());
        basics.add(srcdiff.size());
        basics.add(trgdiff.size());
        
        //writing to file
        ste.saveSheet(srcsheetname, srcheader, srcdiff);
        ste.saveSheet(trgsheetname, trgheader, trgdiff);
        ste.writeClose();
        return basics;
    }
    /*
    public static void main(String args[]) throws Exception{
        
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
    */
}
