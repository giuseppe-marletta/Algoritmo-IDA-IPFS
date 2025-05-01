package tesi.ipfs;

import java.io.IOException;

import io.ipfs.api.IPFS;

public class e_thread extends Thread{
    String name ;
    int delay; 
    areaDec Area;
    int fragmentID;
    String CIDS;
    int[] outipfs;
    int m ;

    e_thread(String name,int delay, areaDec Area, int fragmentID2, String CIDS2, int m2) throws IOException{
        this.name = name;
        this.delay = delay;
        this.Area = Area;
        fragmentID = fragmentID2;
        CIDS = CIDS2; 
        m = m2;
    }

    public void run() {
            outipfs = ipfs.GetFile(CIDS, fragmentID, m, Area);
            try {
                sleep(delay);
            } catch (InterruptedException e) {
            }
       // System.out.println(name + " fatto!");
    }
}