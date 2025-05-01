package tesi.ipfs;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Vector;

public class areaDec  {
    //int fragmentID;
    //String CIDS;
    public int[] outAreaDec ;
    //public int frammentidecAreaDec;
    //public static ipfs ipfs ;
    public static ArrayList<int[]>  bufferframmenti  = new ArrayList<int[]>();
    public static ArrayList<Integer>  bufferIDframmenti  = new ArrayList<Integer>();



    /* 
    areaDec(int fragmentID2, String CIDS2) throws IOException
    {
        fragmentID = fragmentID2;
        CIDS = CIDS2;
        ipfs = new ipfs();
        frammentidecAreaDec = 0;
    }   */

    areaDec() throws IOException
    {
        //ipfs = new ipfs();
        

    }

    public synchronized void DecWriter(int fragmentID, String CIDS, int[] outipfs) {

                    
                    
                       // System.out.println("\n" + "Uso il frammento " + fragmentID + " caricato su IPFS con il CID: " + CIDS + " per ricostruire il messaggio originale!");
                        try {
                            Thread.sleep((long) (Math.random()*(2500-500+1)+500));
                        } catch (InterruptedException  e) {
                            //TODO: handle exception
                        }
					    //outAreaDec =  ipfs.GetFile(CIDS);
                      //bufferframmenti.add(ipfs.GetFile(CIDS));
                        bufferframmenti.add(outipfs);
					    bufferIDframmenti.add(fragmentID);   
                        
                        try {
                           Thread.sleep((long) (Math.random()*(2500-500+1)+500));
                        } catch (InterruptedException  e) {
                            //TODO: handle exception
                        }

                    
                /* 
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {   } 
        */
    }

    
}