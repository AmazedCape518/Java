package peaksort;

public class Peaksort {

	public static void main(String [] args){	
		// variable declarations
		int[] array = new int[10];
		
	    // randomize the array
		for (int j=0; j < array.length; j++) {
				array[j]=(int)(Math.random()*10); // 0-9 randomly generated
		}
	        
	    // search recursively for a peak
	        for (int i = 1;i<array.length-1; i++){
	            if (array[i]>array[i+1] && array[i]>array[i-1]){
	
	                // return one peak location
	                System.out.println("The postion of a peak is " + i);
	                System.out.println("The peak value is " + array[i]);
	                break;
	            }
	        }
	        System.out.println("The values in the array are: ");
	        System.out.println("0 1 2 3 4 5 6 7 8 9");
	    	for(int j = 0; j<array.length; j++){
	    		// display the array
	    		System.out.print(array[j] + " ");
	    	}
	}
}