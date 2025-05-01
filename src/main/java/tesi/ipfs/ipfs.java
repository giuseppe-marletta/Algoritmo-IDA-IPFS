package tesi.ipfs;


import java.io.File;
import java.io.IOException;
import java.util.List;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;


public class ipfs {

    private static IPFS ipfs;
    private static int c;


    ipfs() throws IOException
    {
        ipfs = new IPFS("127.0.0.1",5002);
        ipfs.refs.local();
    }

    public String AddFile(String toIpfs)
    {
          try {
                NamedStreamable.ByteArrayWrapper bytearray = new NamedStreamable.ByteArrayWrapper(toIpfs.getBytes());
                MerkleNode outAdd = ipfs.add(bytearray).get(0);
                System.out.println(" Il CID è: " + outAdd.hash.toBase58());
                return outAdd.hash.toBase58();
            } 
            catch (IOException ex) 
            {
                throw new RuntimeException("Errore durante la comununicazione con il nodo IPFS di riferimento", ex);
            }
    }

    public static int[] GetFile(String hash, int fragmentID, int m, areaDec Area)
    {
        
            try 
            {
                try 
                {
                    Thread.sleep((long) (Math.random()*(2500-500+1)+500));
                }
                catch (InterruptedException  e) 
                {
                    throw new RuntimeException("Errore durante la comununicazione con il nodo IPFS di riferimento", e);
                }
                Multihash multihash = Multihash.fromBase58(hash);
                byte[] content = ipfs.cat(multihash);
                manageC();
                if ( c <= m )
                {
                    System.out.println("\n" + "Uso il frammento " + fragmentID + " caricato su IPFS con il CID: " + hash + " per ricostruire il messaggio originale!");
                    System.out.println("\n" + "Il contenuto del frammento " + fragmentID + " con CID: " + hash + " è: " + new String(content) + "\n");
                    String[] contentstring = new String(content).split(" ");
                    int[] contentint = new int[contentstring.length];
                    for(int i = 0 ; i < contentstring.length; i++)
                    { 
                       contentint[i] =  Integer.parseInt(contentstring[i]);
                    }
                    Area.DecWriter(fragmentID, hash, contentint);
                    return contentint;
                }
                else 
                {
                    try 
                    {
                        Thread.sleep((long) (Math.random()*(2500-500+1)+500));
                    } 
                    catch (InterruptedException  e) 
                    {
                        throw new RuntimeException("Errore durante la comununicazione con il nodo IPFS di riferimento", e);
                    }
                    System.out.println("\n" + " Il frammento " + fragmentID + " non è più utile, quindi scartato!");
                    return null;
                }
            }
            catch (IOException ex)
            {
                throw new RuntimeException("Errore durante la comununicazione con il nodo IPFS di riferimento", ex);
            }
    }

    public static  synchronized void  manageC()
    {
        c = c + 1;
    }
    
}
