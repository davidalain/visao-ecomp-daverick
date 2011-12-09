package testes;


public class RandomIntGenerator{  
    public RandomIntGenerator(int l, int h){  
        low = l;  
        high = h;
    }  
    public int draw(){  
        int r = low + (int)((high-low+1)*nextRandom());  
        return r;  
    }  
    
    public static int[] getClasses(int quantidade){  
        RandomIntGenerator r1 = new RandomIntGenerator(1,1000);  
        //RandomIntGenerator r2 = new RandomIntGenerator(0,1);  
        int i;  
        int [] numeros = new int[quantidade];
        for (i = 0; i<quantidade; i++){
        	int n;
        	do{
        		n = r1.draw();
        	}
        	while(contem(numeros, n));
        	numeros[i] = n;  
        }
        
        return numeros;
     } 
    
    public static int[] getAngulos(int quantidade){  
        RandomIntGenerator r1 = new RandomIntGenerator(1,71);  
        //RandomIntGenerator r2 = new RandomIntGenerator(0,1);  
        int i;  
        int [] numeros = new int[quantidade];
        for (i = 0; i<quantidade; i++){
        	int n;
        	do{
        		n = r1.draw();
        	}
        	while(contem(numeros, n));
        	numeros[i] = n*5;  
        }
        
        return numeros;
     } 
    
    
    private static boolean contem(int[] vetor, int numero){
    	for(int i=0; i<vetor.length; i++){
    		if(vetor[i] == numero){
    			return true;
    		}
    	}
    	
    	return false;
    }
    
     public static double nextRandom(){  
         int pos = (int)(java.lang.Math.random() * BUFFER_SIZE);  
         double r = buffer[pos];  
         buffer[pos] = java.lang.Math.random();  
         return r;  
     }  
    private static final int BUFFER_SIZE = 101;  
    private static double[] buffer = new double[BUFFER_SIZE];  
    static{  
       int i;  
       for (i = 0; i<BUFFER_SIZE; i++)  
           buffer[i] = java.lang.Math.random();  
    }  
    private int low;  
    private int high;  
}  

