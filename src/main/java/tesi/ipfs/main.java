/* 

package tesi.ipfs;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;


public class main 
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "Hello World!" );
        IPFS ipfs = new IPFS("127.0.0.1",5001);
        ipfs.refs.local();
        File file = new File("provaAPI.txt");
        if ( file.createNewFile()){
            System.out.println("The file is created successfully!");
        }
       // NamedStreamable.FileWrapper fileApi = new NamedStreamable.FileWrapper(file);
        
        //MerkleNode addResult = ipfs.add(fileApi).get(0);

        try {
            NamedStreamable.FileWrapper fileAPi = new NamedStreamable.FileWrapper(file);
            MerkleNode response = ipfs.add(fileAPi).get(0);
            System.out.println("la response:" + response.toString());
            System.out.println("Hash (base 58): " + response.hash.toBase58());
          } 
          catch (IOException ex) {
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
          }


          try {
            String hash = "QmU9tKW8UPm5BY9icEZsFQ8Er57RUPcw7rEMZM6NVES1jQ"; // Hash of a file
            Multihash multihash = Multihash.fromBase58(hash);
            byte[] content = ipfs.cat(multihash);
            System.out.println("Content of " + hash + ": " + new String(content));
          } catch (IOException ex) {
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
          }
          
          
          try {
            String hash = "QmU9tKW8UPm5BY9icEZsFQ8Er57RUPcw7rEMZM6NVES1jQ"; // Hash of a file
            Multihash multihash = Multihash.fromBase58(hash);
            ipfs.pin.add(multihash);
          } catch (IOException ex) {
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
          }


          try {
            String hash = "QmWCscor6qWPdx53zEQmZvQvuWQYxx1ARRCXwYVE4s9wzJ"; // Hash of a file
            Multihash multihash = Multihash.fromBase58(hash);
            byte[] content = ipfs.cat(multihash);
            System.out.println("Content of " + hash + ": " + new String(content));
            List<MerkleNode> contentls = ipfs.ls(multihash);
            int i = 0;
            System.out.println(contentls.isEmpty());
          } catch (IOException ex) {
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
          }

          //Multihash multihash = Multihash.fromBase58("Qmeybqr2GaiUyGSRWX3dhS2Qz6VTVBXzBiYiFcKpYFJ7tH");    
          //List<MerkleNode> contentls = ipfs.ls(multihash);
          //int i = 0;
          //System.out.println(contentls);     
          
          
    }
}

*/