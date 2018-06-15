/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandatoryproject2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;


//Driver program for skip list implementation.


/**
 *
 * @author Srabonti Chakraborty SXC154030
 */
public class SplitListDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
	Scanner sc;
	if (args.length > 0) {
	    File file = new File(args[0]);
	    sc = new Scanner(file);
	} else {
	    sc = new Scanner(System.in);
	}
	String operation = "";
	long operand = 0;
	int modValue = 999983;
	long result = 0;
	Long returnValue = null;
	SkipListImpl<Long> skipList = new SkipListImpl<>();
	// Initialize the timer
	Timer timer = new Timer();

	while (!((operation = sc.next()).equals("End"))) {
	    switch (operation) {
	    case "Add": {
		operand = sc.nextLong();
                boolean a = skipList.add(operand);
                //System.out.println("add return value : "+ a);
		if(a) {
                    //System.out.println("return add");
		    result = (result + 1) % modValue;
		}
		break;
	    }
	    case "Ceiling": {
		operand = sc.nextLong();
		returnValue = skipList.ceiling(operand);
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		}
		break;
	    }
	    case "FindIndex": {
		int intOperand = sc.nextInt();
		returnValue = skipList.findIndex(intOperand);
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		}
		break;
	    }
	    case "First": {
		returnValue = skipList.first();
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		}
		break;
	    }
	    case "Last": {
		returnValue = skipList.last();
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		}
		break;
	    }
	    case "Floor": {
		operand = sc.nextLong();
		returnValue = skipList.floor(operand);
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		}
		break;
	    }
	    case "Remove": {
		operand = sc.nextLong();
               
		if (skipList.remove(operand) != null) {
		    result = (result + 1) % modValue;
		}
		break;
	    }
	    case "Contains":{
		operand = sc.nextLong();
		if (skipList.contains(operand)) {
		    result = (result + 1) % modValue;
		}
		break;
	    }
            
            /*case "rebuild":{
		//operand = sc.nextLong();
		skipList.rebuild();
		break;
	    }
		*/
	    }
	}

	// End Time
	timer.end();

	System.out.println(result);
	System.out.println(timer);
    }
    
}
