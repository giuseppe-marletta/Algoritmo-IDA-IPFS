package tesi.ipfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.State;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;



public class ida{
    
	public int n; 
    public int m;
    public int p; 
	public int[] decodeID;
	public static areaDec areas;
	
	

    public ida( int n, int m, int p) throws IOException
    {
        this.n = n ;
        this.m = m ;
        this.p = p;
		decodeID = new int[n];
		areas = new areaDec();
    }

    public double[][] matrice_codifica()
    {
        double matC[][] = new double[n][m];
        for(int i=0;i<n;i++){
    		for(int j=0;j<m;j++){
    			matC[i][j]=Math.pow(1+i,j);
    		}
    	}
        return matC;
    }

	public double[][] matrice_decodifica(int[] frammentidec)   
	{
		double matD[][]=new double[m][m];
		for(int i=0;i<m;i++){
			//System.out.println("\n"+ decodeID[frammentidec[i]-1]+ "\n");
    		for(int j=0;j<m;j++){
    			matD[i][j]=Math.pow(decodeID[frammentidec[i]-1],j);
    		}
    	}
        Inverse inv=new Inverse();
    	return inv.invert(matD);
	}


    public int[][] codifica(int [] messaggio)
    {
        int l = messaggio.length;
    	double a[][]= matrice_codifica();
    	int c[][]=new int[n][l/m];
		int  opC;
    	for(int i=0;i<n;i++)
		{
    		for(int j=0;j<l/m;j++)
			{
    			for(int k=0;k<m;k++)
				{	
					opC = ((int)a[i][k])*messaggio[j*m+k];
    				c[i][j] = c[i][j] + opC ;
    			}
    		}
    	}
      return c;
    }

    public double[] decodifica(int[][] fragment, int[] frammentidec){
    	int len=m*(p/m);
    	double vd[]=new double[len];
    	double inva[][] = matrice_decodifica(frammentidec);
		double opD;
        for(int i=0;i<len;i++){
       	 	for(int k=0;k<m;k++){
				opD =  (inva[i%m][k]*fragment[k][i/m]);
       	 		vd[i] = vd[i] + opD;
       	 	}
        }
        return vd;
    }

    public void setLabels(int[] fragmentID)
    {
		for(int i=0; i<n;i++)
			decodeID[i] = fragmentID[i];
    }


	public int[] checkMul(int [] in) 
	{
		if (p%m != 0)
		{
			for(int i=1;i<=m;i++)
			{
				if((p+i)%m == 0)
				{
					p = p+i;
				    int[] inMul = new int[p+i];
					for(int k=0;k<p-i;k++)
						inMul[k] = in[k];
					for(int j=p;j<p+i;j++)
						inMul[j] = 0; 
					return inMul;
				}
			}
		}
		return in;
	}

	public int[] leggiFile(int p, String path) throws IOException
	{
			File file = new File(path);
			FileInputStream fileInput = null;
			byte[] bytes = new byte[(int) file.length()];


			try {

				fileInput = new FileInputStream(path);
	  
				fileInput.read(bytes);
	  
			} finally {
				if (fileInput != null) {
					fileInput.close();
				}
			}

			//fileInput = new FileInputStream(path);
 
			int in[] = new int[p + 1];
			//System.out.println("il file è lungo " + p);
			//int c = 1;
			//double numR = fileInput.read();
			//in[0] = numR;
			//while (numR != -1) {
			//	System.out.println("Il numero letto vale: " + numR);
			//	numR = fileInput.read();
			//	in[c] = numR;
			//	c ++;
			//}
			//fileInput.close();

			for ( int i = 0 ; i < (int)file.length(); i ++)
			{
				in[i] = bytes[i];
				//System.out.println("Il numero letto vale: " + in[i]);
			}

			

			byte[] provafileout = new byte[(int)file.length()];
			for ( int i = 0 ; i < (int)file.length(); i++)
			{
				provafileout[i] = (byte) in[i];
			}
			FileOutputStream fos = new FileOutputStream("provaoutIDA.txt");
	   		fos.write(provafileout);

			
			//System.out.println("Raggiunta fine del file");
			return in;
	}

	public static int fileLength(String path) 
	{
			
			File file = new File(path);
			int p = (int)file.length();
			return p ;
	}

	public static String InputFile(Scanner input)
	{
			//Scanner input = new Scanner(System.in);
			System.out.println("\n" + "inserisci file da condividere");
			String path = input.nextLine();
			System.out.println("hai inserito " + path);
			//input.close();
			return path;
	}

	public static int InputN(Scanner input)
	{
		//Scanner input = new Scanner(System.in);
		System.out.println("\n" + "Inserisci il numero di frammenti IDA che vuoi produrre"); //20
		String n = input.nextLine();
		System.out.println("hai inserito " + n);
		//input.close();
		return Integer.parseInt(n);
	}

	public static int InputM(Scanner input)
	{
		//Scanner input = new Scanner(System.in);
		System.out.println("\n" + "Inserisci il numero di blocchi per la suddivisione del file"); //8
		String m = input.nextLine();
		System.out.println("hai inserito " + m);
		//input.close();
		return Integer.parseInt(m);
	}


	  public static void main(String[] args) throws IOException 
	  {
		ipfs ipfs = new ipfs();
		Scanner input = new Scanner(System.in);
		String path = InputFile(input);
		int p = fileLength(path);
		int m = InputM(input);
		int n = InputN(input);
		
		ida ida=new ida(n,m,p);
		int[] in = ida.leggiFile(p,path);
		System.out.print("\nil messaggio è: ");
		for ( int i = 0 ; i < in.length - 1  ; i++)
		{
			System.out.print(in[i] + " ");
		}
		System.out.println();
		//double in[] = {6,4,2,1,3,4,2,4,7,5,2,6,1,4,3,2};
		int val[][]=new int [ida.n][ida.p/ida.m];
		in = ida.checkMul(in);   //se p non è multiplo di m, allora vengono aggiunti degli 0 fino a quando non lo diventa
		//for(int i = 0 ; i < ida.p ; i++)
		//	System.out.print((int)in[i] + " "); 


		int []fragmentID = new int[ida.n];
     	for(int i=1;i<=ida.n;i++)
			  fragmentID[i-1] = i;   
		ida.setLabels(fragmentID);


		val=ida.codifica(in);
		System.out.println("\nCodifica:\n");
		//String toIpfs = new char[ida.p/ida.m];
		String[] toIpfs = new String[ida.n];
		String[] CIDS = new String[ida.n];
		for(int i=0;i<ida.n;i++)
		{
		  System.out.print("Frammento "+ fragmentID[i] + " -> ");
		  for(int j=0;j<ida.p/ida.m;j++)
		  {
		  	System.out.print(val[i][j]+ " ");
		  }
		  System.out.println();
		  toIpfs[i] = String.valueOf(val[i][0]);
		  for( int j = 1 ; j < ida.p/ida.m ; j++)
		  {
			toIpfs[i] = toIpfs[i]  + " " + String.valueOf(val[i][j]);
		  }
		 // System.out.println(toIpfs[i]);
		  //System.out.println();
		 // for(int j=0;j<ida.p/ida.m;j++)
		  //{
			//System.out.print(toIpfs[j] +  " ");
		 // }
		   System.out.println();
		   System.out.print("caricato il frammento " + fragmentID[i] + " su IPFS! ");
		   CIDS[i] = ipfs.AddFile(toIpfs[i]);
		   System.out.println("\n");
		}


		System.out.println("\n Servono almeno " + ida.m + " frammenti per ricostruire il messaggio originale\n");
		System.out.println("Vuoi procedere? [SI/NO]");
		//Scanner indecod = new Scanner(System.in);
		String answer = input.nextLine();
		System.out.println("\n" + "Richiedere un numero minore del massimo dei frammenti o il numero massimo dei frammenti? [1/2]");
		String option = input.nextLine();

		
		//System.out.println(answer);
		
		if( answer.equals("SI") )
		{

			int[][] out = new int[ida.m][ida.p/ida.m];
			
			int[] frammentidec = new int[ida.n];
			
			if(option.equals("1")) 
			{	

				int numeroframmentidec = (int)Math.floor(Math.random()*(ida.n-ida.m+1)+ida.m);
				int[] randomcid = new int [numeroframmentidec];
				System.out.println("\n" + "Verrano presi " + numeroframmentidec + " di " +  ida.n + " frammenti per effettuare la decodifica");
				for ( int g = 0; g < numeroframmentidec ; g++)   //possono essere presi m qualsiasi frammenti
				{
					//if( g == m )
					//{ break; }
					
					// out[g][h] = val[g][h];
					randomcid[g] = (int)Math.floor(Math.random()*((ida.n-1)-0+1)+0) ;
					int c = 0;
					while(true && g > 0)
					{
						if(c != g) 
						{
							if(randomcid[g] == randomcid[c])
							{
							randomcid[g] = (int)Math.floor(Math.random()*((ida.n-1)-0+1)+0);
							c = 0 ;
							}
							else c++;
						}
						else c++;
						if( c == numeroframmentidec ) { break; }
					}
					System.out.println("Effettuo la richiesta per il seguente frammento: " + fragmentID[randomcid[g]]);
				}
				

				//inizio thread 

				int[][] fragmentout = new int[numeroframmentidec][ida.p/ida.m];
				for( int i  = 0 ; i < numeroframmentidec ; i ++)
				{
					for( int j  = 0 ; j < ida.p/ida.m ; j++)
					{
						fragmentout[i][j] = 0 ;
					}
				}
				e_thread[] vectThread = new e_thread[numeroframmentidec];
				String[] nomeThread = new String[numeroframmentidec];
				//areaDec[] areas = new areaDec[numeroframmentidec];

				for(int g = 0; g < numeroframmentidec; g++)
				{
					//areas[g] = new areaDec(fragmentID[randomcid[g]], CIDS[randomcid[g]]);
					nomeThread[g] = "Thread " + String.valueOf(g);
					int delay = (int)Math.floor(Math.random()*(2500-500+1)+500);
					vectThread[g] = new e_thread( nomeThread[g] , delay, areas, fragmentID[randomcid[g]], CIDS[randomcid[g]], ida.m);
				}

				for(int g = 0 ; g < numeroframmentidec; g++)
				{
					vectThread[g].start();
				}

				int c = 0;
				while(true)
				{
					for(int g = 0 ; g < numeroframmentidec ; g++)
					{
						if(vectThread[g].getState() == State.TERMINATED)
						{
							c++;
						}

						if(c == ida.m) {break;}
						
					}

					if(c == ida.m)
					{ 
						for(int g = 0 ; g < numeroframmentidec ; g++)
						{
							vectThread[g].interrupt();
						}
						break;
					}
				}

				for( int g = 0 ; g < numeroframmentidec; g++)
				{
						try {
							vectThread[g].join();
						} catch (InterruptedException e) {
						}
				}  


				for( int g = 0 ; g < ida.m ; g++)
				{
					//fragmentout[g] = areas[g].outAreaDec ;
					//frammentidec[g] = areas[g].frammentidecAreaDec;
					fragmentout[g] = areaDec.bufferframmenti.get(g);
					frammentidec[g] = areaDec.bufferIDframmenti.get(g);

				}

				/* 
				for( int g = 0 ; g < ida.m ; g++)
				{
					System.out.println();
					System.out.print(" " + frammentidec[g] + " ");
					System.out.println();
				}
				*/

				for ( int g = 0 ; g < ida.m ; g++)
				{
					out[g] = fragmentout[g];
				}

				//fine thread

				
			}
			else if ( option.equals("2"))
			{
				/* 
				int[] randomcid = new int [ida.n];
				System.out.println("Verrano presi " + ida.n + " di " +  ida.n + " frammenti per effettuare la decodifica");
				for ( int g = 0; g < ida.n ; g++)   //possono essere presi m qualsiasi frammenti
				{
					if( g == ida.m )
					{ break; }
					
					// out[g][h] = val[g][h];
					randomcid[g] = (int)Math.floor(Math.random()*((ida.n-1)-0+1)+0) ;
					int c = 0;
					while(true && g > 0)
					{
						if(c != g) 
						{
							if(randomcid[g] == randomcid[c])
							{
							randomcid[g] = (int)Math.floor(Math.random()*((ida.n-1)-0+1)+0);
							c = 0 ;
							}
							else c++;
						}
						else c++;
						if( c == ida.n ) { break; }
					}
					//System.out.println(randomcid[g]);
					System.out.println("\n" + "Uso il frammento " + fragmentID[randomcid[g]] + " caricato su IPFS con il CID: " + CIDS[randomcid[g]] + " per ricostruire il messaggio originale!");
					out[g] =  ipfs.GetFile(CIDS[randomcid[g]]);
					frammentidec[g] = fragmentID[randomcid[g]];
				}  */

				int[] randomcid = new int [ida.n];   
				System.out.println("Verrano presi " + ida.n + " di " +  ida.n + " frammenti per effettuare la decodifica");
				for ( int g = 0; g < ida.n ; g++)   //possono essere presi m qualsiasi frammenti
				{
					//if( g == m )
					//{ break; }
					
					// out[g][h] = val[g][h];
					randomcid[g] = (int)Math.floor(Math.random()*((ida.n-1)-0+1)+0) ;
					int c = 0;
					while(true && g > 0)
					{
						if(c != g) 
						{
							if(randomcid[g] == randomcid[c])
							{
							randomcid[g] = (int)Math.floor(Math.random()*((ida.n-1)-0+1)+0);
							c = 0 ;
							}
							else c++;
						}
						else c++;
						if( c == ida.n ) { break; }
					}
					System.out.println("Effettuo la richiesta per il seguente frammento: " + fragmentID[randomcid[g]]);

				}
				

				//inizio thread 

				int[][] fragmentout = new int[ida.n][ida.p/ida.m];
				for( int i  = 0 ; i < ida.n ; i ++)
				{
					for( int j  = 0 ; j < ida.p/ida.m ; j++)
					{
						fragmentout[i][j] = 0 ;
					}
				}
				e_thread[] vectThread = new e_thread[ida.n];
				String[] nomeThread = new String[ida.n];
				//areaDec[] areas = new areaDec[ida.n];

				for(int g = 0; g < ida.n ; g++)
				{
					//areas[g] = new areaDec(fragmentID[randomcid[g]], CIDS[randomcid[g]]);
					nomeThread[g] = "Thread " + String.valueOf(g);
					int delay = (int)Math.floor(Math.random()*(2500-500+1)+500);
					vectThread[g] = new e_thread( nomeThread[g] , delay, areas, fragmentID[randomcid[g]], CIDS[randomcid[g]], ida.m);
				}

				for(int g = 0 ; g < ida.n; g++)
				{
					vectThread[g].start();
				}

				int c = 0;
				while(true)
				{
					for(int g = 0 ; g < ida.n ; g++)
					{
						if(vectThread[g].getState() == State.TERMINATED)
						{
							c++;
						}

						if(c == ida.m) {break;}
						
					}

					if(c == ida.m)
					{ 
						for(int g = 0 ; g < ida.n ; g++)
						{
							vectThread[g].interrupt();
						}
						break;
					}
				}

				for( int g = 0 ; g < ida.n; g++)
				{
						try {
							vectThread[g].join();
						} catch (InterruptedException e) {
						}
				}  


				for( int g = 0 ; g < ida.m ; g++)    //ida.m
				{
					fragmentout[g] = areaDec.bufferframmenti.get(g) ;
					//frammentidec[g] = areas[g].frammentidecAreaDec;
					frammentidec[g] = areaDec.bufferIDframmenti.get(g);
				}

			//	System.out.println();

				for ( int g = 0 ; g < ida.m ; g++) //ida.m
				{
					out[g] = fragmentout[g];
					//System.out.print(frammentidec[g] + " ");
					
				}

				//fine thread
			}

			double[] vd = ida.decodifica(out,frammentidec);
			long[] vdint = new long [p];
			byte[] vdbytes = new byte [p];
			System.out.println("\nMessaggio originale: ");
			for ( int i=0; i < p; i++)
			{
				System.out.print(Math.round(vd[i]) + " ");
				vdint[i] = Math.round(vd[i]);
			}
			System.out.println();
		
			//vdint = Arrays.stream(vdint).filter(x -> x != null ).toArray();
			//System.out.println( "Ovvero... \n  ");
			for ( int i = 0 ; i < p ; i++)
			{
				vdbytes[i] = (byte)vdint[i];
				// System.out.print(vdbytes[i] + " ");
			}
			FileOutputStream fos = new FileOutputStream("provaoutIDA2.txt");
			fos.write(vdbytes);
			System.out.println("\nMESSAGGIO RICOSTRUITO\n");
		}
		else if ( answer.equals("NO")) { System.out.println("Messaggio non ricostruito!");}
		input.close();
	}
	  
}