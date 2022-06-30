
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class Part1 {

    public void totalBirths(){
        FileResource fr=new FileResource();
        int totalbirth=0;
        int totalgirl=0;
        int totalboy=0;
        for (CSVRecord record:fr.getCSVParser(false)){
            int numberbirth=Integer.parseInt(record.get(2));
            totalbirth+=numberbirth;
            if(record.get(1).equals("F")){
                totalgirl+=numberbirth;
            }else{totalboy+=numberbirth;}
        }
        System.out.println("total birth is   "+totalbirth);
        System.out.println("total girl is   "+totalgirl);
        System.out.println("total boy is   "+totalboy);
    }

    public long getrank(int year,String name, String gender){
        FileResource fr=new FileResource();
        long result=0;
        long i=0;
        for (CSVRecord record:fr.getCSVParser(false)){
            String currname=record.get(0);
            String currgender=record.get(1);

            if(currgender.equals("F")){
                i++;}

            if(currgender.equals(gender)&&currname.equals(name)){
                if(gender.equals("F")){
                    result=record.getRecordNumber();
                }else{result=record.getRecordNumber()-i;}
                break;
            }else{result= -1;}
        }
        return result;
    }

    public String getname(int year, int rank, String gender){
        FileResource fr=new FileResource();
        int i=0;
        String result=null;
        for (CSVRecord record:fr.getCSVParser(false)){
            String currname=record.get(0);
            String currgender=record.get(1);
            int pos=(int)record.getRecordNumber();

            if(currgender.equals("F")){
                i++;
            }

            if(gender.equals("F")){
                if(currgender.equals(gender)&&pos==rank){
                    result=record.get(0);
                    break;
                }else{result= "NO NAME";}
            }else{
                if(currgender.equals(gender)&&pos==rank+i){
                    result=record.get(0);
                    break;
                }else{result= "NO NAME";}
            }
        }
        return result;
    }

    public void whatisnameinyear(String name, int year, int newyear, String gender){
        int rank=(int)getrank(year, name, gender);
        String newname=getname(newyear,rank,gender);
        System.out.println(name+"   born in  "+year+"  would be  "+newname+"  if she was born in  "+newyear);
    }

    public int yearofhighestrank(String name, String gender){
        DirectoryResource dr=new DirectoryResource();
        int highestrank=0;
        int result=0;

        for(File f:dr.selectedFiles()){
            FileResource fr=new FileResource(f);
            int year=Integer.parseInt(f.getName().substring(3,7));
            int rank=(int)getrank2(year, name, gender,fr);

            if(highestrank==0&&rank!=-1){
                result=year;
                highestrank=rank; 
            }else if(rank!=-1&&rank<highestrank){
                result=year;
                highestrank=rank;}
        }
        return result;
    }

    public long getrank2(int year,String name, String gender,FileResource fr){
        long result=0;
        long i=namenumber("F",fr);
        for (CSVRecord record:fr.getCSVParser(false)){
            String currname=record.get(0);
            String currgender=record.get(1);

            if(currgender.equals(gender)&&currname.equals(name)){
                if(gender.equals("F")){
                    result=record.getRecordNumber();
                }else{result=record.getRecordNumber()-i;}
                break;
            }else{result= -1;}
        }
        return result;
    }

    public double getaveragerank(String name, String gender){
        DirectoryResource dr=new DirectoryResource();
        int result=0;
        double i=0;

        for(File f:dr.selectedFiles()){
            FileResource fr=new FileResource(f);
            int year=Integer.parseInt(f.getName().substring(3,7));
            int rank=(int)getrank2(year, name, gender,fr);
            if(rank==-1){return -1;}else{
                result+=rank;
                i++;}
        }
        return result/i;}

    public int totalbirthsrankedhigher(int year, String name, String gender){
        FileResource fr=new FileResource();
        int result=0;
        int i=1;
        int rank=(int)getrank2(year,name,gender,fr);

        for(CSVRecord record:fr.getCSVParser(false)){

            if(rank!=-1&&record.get(1).equals(gender)){
                if(i<rank){
                    int currbirth=Integer.parseInt(record.get(2));
                    i++;
                    result+=currbirth;}}}
        return result;
    }

    public int namenumber(String gender,FileResource fr){
        int g=0;
        for(CSVRecord record:fr.getCSVParser(false)){
            if(record.get(1).equals(gender)){
                g++;}
        }
        return g;
    }

    public void test(){
        //FileResource fr=new FileResource();
        //long result1=getrank2(1990,"Emily","F",fr);
        //System.out.println("q4 is  "+result1);

        //int result0=namenumber("M",fr);
        //System.out.println("boys number is  "+result0);
        //String result2=getname(2012,450,"M");
        //System.out.println("q5 is   "+result2);

        //int result3=yearofhighestrank("Mich","M");
        //System.out.println("highest rank is  "+result3);

        //double result4=getaveragerank("Susan","F");
        //System.out.println("average rank is  "+result4);

        //double result6=getaveragerank("Robert","M");
        //System.out.println("average rank is  "+result6);

        int result5=totalbirthsrankedhigher(1990,"Emily","F");
        System.out.println("total birth is  "+result5);
        //int result7=totalbirthsrankedhigher(2012,"Drew","M");
        //System.out.println("total birth is  "+result7);
    }

}
