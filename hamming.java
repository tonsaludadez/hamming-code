import java.io.*;
import java.util.*;

class Hamming{
	ArrayList<Character> code;
	String parity;
	int size;

	public Hamming(String word, String parity){
		code = new ArrayList<Character>();

		for(int i = 0; i < word.length(); i++) {
			code.add(word.charAt(i));
		}
		//System.out.println(code);

		this.parity = parity;
		this.size = code.size();
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.print("Construct or Check\n\nChoice: ");
		String choice = scan.nextLine();

		if(choice.toLowerCase().equals("construct")){
			System.out.print("Enter word: ");
			String word = scan.nextLine();
			System.out.print("Enter parity: ");
			String parity = scan.nextLine();

			Hamming ham = new Hamming(word, parity);

			String codeword = ham.construct();
			System.out.println("\nResult: " + codeword);
		}

		else {
			System.out.print("Enter word: ");
			String word = scan.nextLine();
			System.out.print("Enter parity: ");
			String parity = scan.nextLine();

			System.out.println("Has Error: " + Hamming.hasError(word, parity));
		}

	}

	public static boolean isPower(int i){
		if(i == 0){
			//System.out.println("I:" + i + " is power top");
			return true;
		}

		else if(i == 1){
			//System.out.println("I:" + i + " is power mid");
			return true;
		}

		else{
			for(int j = 2; j < i;){
				j = j*2;
				if(i == j-1){
					//System.out.println("I:" + i + " is power bot");
					return true;
				}
			}
		}

		return false;
	}

	public String construct(){
		//double checkBit = Math.log(code.size()) / Math.log(2);
		int temp = (int) (Math.log(code.size()) / Math.log(2))+1;

		char[] result = new char[size + temp];
		int[] position = new int[temp];

		for (int i = 1, j = 0; i < result.length; i++){		//either i=0 or i=1
			if(Hamming.isPower(i) && result.length-1 != i){
				//System.out.println("i: " + i);
				position[j] = i;
				//System.out.println(j+"Position: " + position[j]);
				j++;
			}

			else{
				result[i] = code.remove(0);
				//System.out.println("Result: " + result[i]);
			}
		}

		//for(int i = 0; i < result.length; i++){
		//	System.out.println(i+1+":" + result[i]);
		//}

		for(int i = 0; i < position.length; i++){
			int ctr = 0;
			int skip = position[i] + 1;
			//System.out.println("pos: " + position.length);

			for(int j = skip - 1; j < result.length; j = j + skip){
				for(int k = 0; k < skip && j < result.length; k++, j++){
					if(result[j] == '1'){
						ctr++;
					}
				}
			}
		
			
			if(parity.toLowerCase().equals("even")){
				if(ctr % 2 == 0){
					result[skip-1] = '0';
				}
				else
					result[skip-1] = '1';
			}

			else{
				if(ctr % 2 == 1){
					result[skip-1] = '0';
				}
				else
					result[skip-1] = '1';	
			}
		}

		return String.valueOf(result);
	}

	public static boolean hasError(String word, String par){
		
		ArrayList<Integer> pos = new ArrayList<Integer>();
		ArrayList<Character> temp = new ArrayList<Character>();
		char[] result = new char[word.length()];

		for (int i = 1, j = 0; i < word.length(); i++){		//either i=0 or i=1
			if(Hamming.isPower(i) && word.length() != i){
				pos.add(i);
				//System.out.println("Position: " + pos.get(j));
				j++;
			}

			else{
				temp.add(word.charAt(i));
				//System.out.println("Result: " + result[i]);
				result[i] = temp.remove(0);
				//System.out.println("Result: " + result[i]);
			}
		}

		pos.add(0);

		//for(int i = 0; i < result.length; i++){
		//	System.out.println(i+1+":" + result[i]);
		//}

		for(int i = 0; i < pos.size(); i++){
			int ctr = 0;
			int skip = pos.get(i) + 1;
			//System.out.println("pos: " + pos.size());

			for(int j = skip - 1; j < result.length; j = j + skip){
				for(int k = 0; k < skip && j < result.length; k++, j++){
					if(result[j] == '1'){
						ctr++;
					}
				}
			}
			
			//for(int o = 0; o < result.length; o++){
			//	System.out.println(o+1+"::" + result[o]);
			//}
		

			if(par.toLowerCase().equals("even")){
				if(ctr % 2 == 0){
					result[skip-1] = '0';
				}
				else
					result[skip-1] = '1';


			}

			else{
				if(ctr % 2 == 1){
					result[skip-1] = '0';
				}
				else
					result[skip-1] = '1';	
			}
		}

		//System.out.println(String.valueOf(result));

		if (String.valueOf(result).equals(word)) {
			return false;
		}

		else{
			int count = 0;
			for (int i = 0; i<result.length;i++) {
				if(result[i] != word.charAt(i))
					count = count + i+1;
			}
			
			System.out.println("Erroneous bit: " + count);
			return true;

			/*System.out.print("Errors at parity: ");
			for (int i = 0; i<result.length;i++) {
				if(result[i] != word.charAt(i))
					System.out.print("["+(i+1)+"] ");
			}
			System.out.println();
			return true;
			*/
		}
	
	}

}