package binary;
import java.util.Stack;
import java.util.Scanner;

public class Binary {
	private static int numbersBeforeDecimalPoint=0;
	private static int numbersAfterDecimalPoint = 0;
	private static int exponent=0;
	private static Stack<Integer> stack1 = new Stack<Integer>();
	private static Stack<Integer> stack2 = new Stack<Integer>();
	private static Stack<Integer> stack3 = new Stack<Integer>();
	private static int whereIsDecimal=0;
	private static boolean doNotDoBinary1 = false;
	private static boolean doNotDoBinary2 = false;
	private static boolean doublePrecision = false;
	
	public static void main(String[] args) 
	{
		Scanner keyboard = new Scanner( System.in ); 
		System.out.println("If you want to convert from DECIMAL to BINARY(Single), enter 0.\n"
				+"If you want to convert from BINARY(Single) to DECIMAL, enter 1.\n"+
				"If you want to convert from DECIMAL to BINARY(Double) enter 2.\nIf you want to convert from BINARY(Double) to DECIMAL, enter 3.");
		int k = keyboard.nextInt();
		
			if(k==0)
		{
			System.out.println("Please enter DECIMAL:");
			String i;
			doublePrecision=false;
			i= keyboard.next();
			d2B(i);
		}
		if(k==1)
		{
			System.out.print("Please enter BINARY:\n");
			String m = keyboard.next();
            float deciFinal;
            if(m.equalsIgnoreCase("0/1|11111111|any nonzero significand"))
            {
            	System.out.println("NaN");
            	System.exit(0);
            }
            if(m.equalsIgnoreCase("0/1|11111111|00000000000000000000000"))
            {
            	System.out.println("Infinity");
            	System.exit(0);
            }
            if(m.equalsIgnoreCase("0/1|00000000|any nonzero significand"))
            {
            	System.out.println("Denormalized Number");
            	System.exit(0);
            }
            String ieee754 = m;
            if(ieee754.charAt(0)==49)
            {
            	System.out.print("-");
            	ieee754="0"+ieee754.substring(1,ieee754.length());
            }
            deciFinal = Float.intBitsToFloat(Integer.parseInt(ieee754, 2));
            System.out.println(deciFinal);
		}
		if(k==2)
		{
			doublePrecision=true;
			System.out.print("Please enter DECIMAL:");
			String i = keyboard.next();
			d2B(i);
			
		}
		if(k==3)
		{
			doublePrecision=true;
			System.out.println("Please enter BINARY:");
			String m = keyboard.next();
			if(m.equalsIgnoreCase("0/1|11111111111|any nonzero significand"))
            {
            	System.out.println("NaN");
            	System.exit(0);
            }
            if(m.equalsIgnoreCase("0/1|11111111111|00000000000000000000000"))
            {
            	System.out.println("Infinity");
            	System.exit(0);
            }
            if(m.equalsIgnoreCase("0/1|00000000000|any nonzero significand"))
            {
            	System.out.println("Denormalized Number");
            	System.exit(0);
            }
			double deciFinal;
            String ieee754 = m;
            if(ieee754.charAt(0)==49)
            {
            	System.out.print("-");
            	ieee754="0"+ieee754.substring(1,ieee754.length());
            }
            ieee754 = ieee754.trim();
            deciFinal = Double.longBitsToDouble(Long.parseLong(ieee754,2));
            System.out.println(deciFinal);
		}
		
	}
	
	private static void d2B(String i)
	{
		checkIfInfinity(i);
		checkIfNAN(i);
		checkIfDecimal(i);
		///sends string to check to see if there is a decimal point
		
		isNegative(numbersBeforeDecimalPoint);
		//checks to see if the number is negative
	
		setBinarys();//sets binaries
				 //calls method to add stacks
				//calls method to set exponents
		printFinalStack();//print significand
		System.out.println();
		
	}
	
	public static void checkIfNAN(String i)
	{
		if(i.equalsIgnoreCase("nan"))
		{
			System.out.println("0/1|11111111|any nonzero significand");
			System.exit(0);
		}
	}
	
	public static void checkIfInfinity(String i)
	{
		if(i.equalsIgnoreCase("Infinity"))
		{
			System.out.println("0/1|11111111|00000000000000000000000");
			System.exit(0);
		}
	}
	
	private static void checkIfZero(String i)
	//This prints out the Single Precision Representation
	//of zero and then exits the program
	{
		if(Integer.parseInt(i)==0)
		{
			System.out.print("0|00000000|00000000000000000000000");
			System.exit(0);
		}
	}
	
	
	private static double convertToDecimal(String m)
	{
		double j=0;
		 for(int i=0;i<m.length();i++)
		    {
		        if(m.charAt(i)== '1')
		        {
		         j=j+ Math.pow(2,m.length()-1-i);
		        }
		    }
		 //System.out.println(j);
		 return (int)j ;
	}
	
	private static void addStacks()
	//This adds calls other methods and adds stacks together if they're not empty
	{
		if((doNotDoBinary1!=true)&&(doNotDoBinary2!=true))
		{
			addBothStacksToThirdStack();
		}
		else
		{
			if((doNotDoBinary1!=true)&&(doNotDoBinary2==true))
			{
				addStack1ToStack3();
			}
			else
			{
				addStack2ToStack3();
			}
		}
	}
	
	private static void addStack2ToStack3()
	//This method is called if there in no number besides zero before the decimal point 
	//in the decimal
	{
		int count=0;
		int bit23=23;
		bit23=bit23-stack2.size();
		boolean run=true;
		while((stack2.size()!=0)&&(run))
		{
			
			stack3.push(stack2.pop());
			
		}
		if(doublePrecision==false)
		{
			while(count<bit23)
			{
				stack3.push(0);
				count++;
			}
		}
		else
		{
			while(count<52)
			{
				stack3.push(0);
				count++;
			}
		}
		while(stack3.size()!=0)
		{
			stack2.push(stack3.pop());
		}
	}
	
	private static void addStack1ToStack3()
	//This adds the stack with the binary from the number 
	//before the decimal point to a stack to be printed out
	{
		int count=0;
		int bit23=23;
		bit23=bit23-stack1.size();
		while(stack1.size()!=0)
		{
			stack3.push(stack1.pop());
		}
		if(doublePrecision==false)
		{
			while(count<bit23)
			{
				stack3.push(0);
				count++;
			}
		}
		else
		{
			while(count<52)
			{
				stack3.push(0);
				count++;
			}
		}
		while(stack3.size()!=0)
		{
			stack2.push(stack3.pop());
		}
	}
	
	private static void addBothStacksToThirdStack()
	//this adds both stack of binary to stack3 and then back to stack2 
	//to later be printed out to the screen
	{
		int count=0;
		if(doublePrecision==true)
		{
			count=stack1.size()+stack2.size();
		}
		int bit23=23;
		bit23=bit23-stack1.size()-stack2.size();
		while(stack1.size()!=0)
		{
			stack3.push(stack1.pop());
		}
		while(stack2.size()!=0)
		{
			stack3.push(stack2.pop());
		}
		if(doublePrecision==false)
		{
			while(count<bit23)
			{
				stack3.push(0);
				count++;
			}
		}
		else
		{
			while(count<52)
			{
				stack3.push(0);
				count++;
			}
		}
		while(stack3.size()!=0)
		{
			stack2.push(stack3.pop());
		}
		
	}
	
	private static void setBinarys()
	//This sets each stack depending if numbers exist before/after the decimal point
	{
		if(doNotDoBinary1==false)
		//if there is a number before the decimal do convert it to binary
		//else it wont
		{
			setBinary1(numbersBeforeDecimalPoint);
			//converts the first part of the number to binary
			//(even if there is or isn't a decimal point)
		}
			
		if(doNotDoBinary2==false)
		//if there is a number before the decimal do convert it to binary
		//else it wont
		{
			setBinary2(numbersAfterDecimalPoint);
			//converts second part of number to binary to it's own stack
			//even if there isn't a number before the decimal point
		}
		addStacks();
		setExponents();
	}
	
	private static void setExponents()
	//this sets the exponent part of the IEEE 754 string
	{
		String s;
		if(doublePrecision==false)
		{
			s = Integer.toBinaryString(exponent+127);
			if(s.length()<8)
			{
				s="0"+s;
				printExponent(s);
			}
			else
			{
				printExponent(s);
			}
		}
		else
		{
			s = Integer.toBinaryString(exponent+1023);
			if(s.length()<11)
			{
				s="0"+s;
				printExponent(s);
			}
			else
			{
				printExponent(s);
			}
		}
	}
	private static void printExponent(String s)
	//prints the exponent part of the string
	{
		System.out.print(s);
	}
	private static void printFinalStack()
	{
		System.out.print("|");
		while(stack2.size()!=0)
		{
			System.out.print(stack2.pop());//this prints out the stack with the significand
		}
		System.out.print("|");
	}
	
	private static int isNegative(int i)
	//checks to see if number is negative or not
	{
		int isNegative = 0;
		if(i<0)
		{
			isNegative=1;//THIS says that the number is negative is true
		}
		System.out.print(isNegative);
		System.out.print("|");
		return isNegative;
	}
	private static void setBinary1(int i)
	//begins stack for binary, converts to binary, and then prints it out
    {
		if(i!=0)
		{
			int remainder;
			while(i!=0)
			{
				remainder=i%2;
				i=i/2;
			
				if(remainder<0)
					{remainder=remainder+2;}
				//if the number is negative this'll be a negative 1 therefore
				//so i add 2 to make it a positive 1 and print out correctly
				stack1.push(remainder);
			
			}
			exponent=stack1.size()-1;
			//This is where the binary is printed from and stored to.
			//It is then popped to the screen
			stack1.pop();
		}
		else
		{
			doNotDoBinary1=true;
		}
    }
	
	private static void checkIfDecimal(String number)
	//Checks to see if the number is a decimal and 
	//then the boolean isDecimal is true
	{
		
		boolean isDecimal=false;
		String test = number;
		int count=0;
		for(int k = 0; k<test.length();k++)
		{
			count++;
			if(test.charAt(k)==46)
			{
				whereIsDecimal=k;
				isDecimal=true;
				//System.out.println(count);
				break;
			}
		}
		if(isDecimal==true)
		{
			numbersAfterDecimalPoint=Integer.parseInt(test.substring(whereIsDecimal+1, test.length()));
			//This cuts from the decimal point to the end of the number 
			//to convert to an integer
			if(count>1)
			{
				
				numbersBeforeDecimalPoint=Integer.parseInt(test.substring(0, whereIsDecimal));
				
				//This cuts from the beginning of the string until the decimal point and converts string to integer
				//to then be converted to binary
			
			}
			else
			{
				
				doNotDoBinary1=true;
			}
			
		}
		else
		{
			checkIfZero(number);
			numbersBeforeDecimalPoint=Integer.parseInt(number);
			doNotDoBinary2=true;
		}
		
		
		//System.out.println(numbersAfterDecimalPoint);
		//System.out.println(numbersBeforeDecimalPoint);
	}
	
	private static void setBinary2(int i)
	//This'll be for the second part of the decimal 
	//if it were to come to that point
	{
		if(i!=0)
		{
			double newInt=(double)i;
			while(newInt>1)
				//this loop divides the integer thats brought in until its less than one 
			{
				newInt=newInt/10;
				//System.out.println(newInt);
			}
			while(newInt!=0)
				//this does the algorithm to turn it to binary
				//it multiplies by 2 and checks to see if it's under 1
			
			{
				newInt=newInt*2;
				if(newInt<1)//if the number is still under 1 add a 0 to the stack and then continue while loop
				{
					stack2.push(0);//adds 0 to stack
				}
				else
				{
					newInt=newInt-1;//this subtracts 1 from newInt
					stack2.push(1);//add a 1 to the stack confirming the number is over or equal to 1
				}
			}
			if(doNotDoBinary1==true)
			{
				exponent=-(stack2.size());
				stack2.pop();
			}
		}
		else
		{
			doNotDoBinary2=true;
		}
		
	}

}
