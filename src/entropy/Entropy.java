package entropy;

import java.io.*;
import java.util.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//package entropy;

/**
 *
 * @author Степан
 */
public class Entropy {

    /**
     * @param args the command line arguments
     */
    static final String PATH = "D:\\\\Programming\\Crypt\\entropy\\text\\master.txt";
    public static void main(String[] args) throws Exception {
        Reader monoRead = new Reader();
        int[] count = new int[32];
        int len=0;
        try{
            count=monoRead.readFileMono(PATH);
            len=monoRead.getLen();
        }
        catch(IOException ex){
            System.out.print(ex.getMessage());
        }
        
        
        //System.out.println(count);
        DoubleArray mono = new DoubleArray(32);
        mono.setValue(count);
        mono.sort();
        System.out.println(mono);
        mono.printNotZero(len);
        /*Map<Character,Integer> monogram = new TreeMap<>();
        for (int i=0;i<32;i++){
            monogram.put((char)(i+1072), count[i]);
        }
        System.out.println(monogram.entrySet());*/
       
        
        /*List list = new ArrayList(monogram.entrySet());
        Collections.sort(list, new Comparator(){
            @Override public int compare(Entry e1, Entry e2) {return e1.getValue().compareTo(e2.getValue()); }
        });*/
 
    }
    
}

class Reader {
    private int len;
    int[] count = new int[32];
    public int[] readFileMono(String path) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path),"CP1251"));
        String s;
        while((s = br.readLine()) != null) {
            s=s.toLowerCase();
            //s=s.replaceAll("\\w+", "");
            s=s.replaceAll("\\w+|\\!|\\,|\\.|\\?|\\s|\\*|\\-|\\(|\\)|\\\"|\\:|\\^|\\#|\\$|\\%|\\ё|\\_|\\\n|\\;|\\/|\\<|\\>", "");
            System.out.println(s);
            len +=  s.length();
            for (char c: s.toCharArray()){
                //System.out.println(((int)c - 1072));
                //if ((((int)c - 1072)<32)&&(((int)c - 1072)>-1)){
                    count[((int)c - 1072)] +=1;
               // }
                
            } 
        }
        br.close();
        return count;
    }
    public int getLen(){
        return len;
    }
}

class DoubleArray {
    private int size;
    private int[] value;
    private char[] order = new char[size];
    DoubleArray(int size){
        this.size=size;
        value = new int[size];
        this.order = new char[size];
        for(int i=0;i<size;i++){
            order[i]=(char)(i+1072); 
        }
    }
    public void setValue(int[] arr){
        for(int i=0; i<size;i++){
            value[i] = arr[i];
        }        
    }
    @Override
    public String toString(){
        String res="";
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<size;i++){
            res= res+order[i];
            res+=" = ";
            res+=value[i] + " ";
        }
        return res;
    }
    public int searchMax(int from, int to){
        int max = value[from];
        int res=from;
        for(int i=from+1;i<to;i++){
            if(value[i]>max){
                max=value[i];
                res = i;
            }
        }
        return res;
    }
    public void swap(int x, int y){
        int buf;
        char ch;
        buf=value[x];
        value[x] = value[y];
        value[y] = buf;
        ch=order[x];
        order[x] = order[y];
        order[y] = ch;
    }
    public void sort(){
        for(int i=0;i<size;i++){
            this.swap(i, this.searchMax(i, size));
        }
    }
    public void printNotZero(int len){
        int i=0;
        double d =0;
        System.out.println(size);
        while((value[i]!=0)&&(i<size)){
            System.out.println(i);
            d = (double)(value[i]*100)/len;
            System.out.println(order[i]+" = " + d +"%");
            i++;
            if(i==32) break;
        }
    }

}


/*class Reader {
    public static String readFileMono(String path) throws IOException{
        String res = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path),"CP1251"));
        StringBuilder sb = new StringBuilder();
        String s;
        while((s = br.readLine()) != null) {
            s=s.toLowerCase();
            //s=s.replaceAll("\\w+", "");
            s=s.replaceAll("\\w+|\\!|\\,|\\.|\\?|\\s", "");
            sb.append(s);
        }
        res = sb.toString();
        //System.out.println(s);
        br.close();
        return res;
    }
}
*/