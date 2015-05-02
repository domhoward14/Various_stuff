package CS2ProgrammingWeek11;

public class CeaserCipher
{
	static double[] table = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0, 0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0, 6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};
	
	int let2nat(char c)
	{
		c -= 97;
		return (int) c;
	}
	
	char nat2let(int c)
	{
		c += 97;
		return (char) c;
	}
	
	char shift(int shftAmt, char c)
	{
		int letter = let2nat(c);
		if(letter <= 25 && letter >= 0 )
			letter = (letter + shftAmt) % 26;
		else
			return c;
		if(letter < 0)
			letter += 26;
		return nat2let(letter);
	}
	
	String encode (int shftAmt, String str)
	{
		char [] array = str.toCharArray();
		for(int i = 0; i < array.length; i ++)
		{
			array[i] = shift(shftAmt, array[i]);
		}
		//System.out.println(array);
		String answer = new String (array);
		return answer;
	}
	
	String decode (int shftAmt, String str)
	{
		char [] array = str.toCharArray();
		int inverse = (-2 * shftAmt) + shftAmt;
		for(int i = 0; i < array.length; i ++)
		{
			array[i] = shift(inverse, array[i]);
		}
		//System.out.println(array);
		String answer = new String (array);
		return answer;
	}
	
	
	int lowers (String str)
	{
		char [] array = str.toCharArray();
		int counter = 0;
		for (int i = 0; i < str.length(); i ++)
		{
			if(array[i] >= 'a' && array[i] <='z')
				counter ++;
		}
		//System.out.println(counter);
		return counter;
	}
	
	int count (char c, String str)
	{
		char [] array = str.toCharArray();
		int counter = 0;
		for (int i = 0; i < str.length(); i ++)
		{
			if(array[i] == c)
				counter ++;
		}
		//System.out.println(counter);
		return counter;
	}
	double percent (int num1, int num2)
	{
		double value =(double) (num1 * 100) / num2;
		double roundedValue = (double)Math.round(value * 100000) / 100000;
		return roundedValue;
	}
	
	double [] freqs (String str)
	{
		int totalCount, charCount,numPosition;
		double percentage;
		double [] percentages = new double [26];
		char [] array = str.toCharArray();
		totalCount = lowers(str);
		
		for(int i = 0; i < str.length(); i++)
		{
			charCount = count(array[i], str);
			percentage = percent(charCount, totalCount);
			if(isletter(array[i]))
			{
				numPosition = let2nat(array[i]);
				percentages[numPosition] = percentage;
			}
		}
		//System.out.println(percentages[0]);
		return percentages;
	}
	
	double [] rotate (int n, double [] list) 
	{
		int position;
		double [] array = new double [list.length];
		for (int i = 0; i < list.length; i ++)
		{
			position = i - n;
			if(position < 0)
				position += list.length;
			array[position] = list[i];
		}
		//System.out.println(list[1]);
		//System.out.println(array[7]);
		return array;
	}
	
	double chisqr (double [] os)
	{
		double chiSum = 0;
		for(int i = 0; i < os.length; i++)
		{
			chiSum += chiSqrHelper(os[i], table[i]);
		}
		return chiSum;
	}
	
	double chiSqrHelper(double a, double b)
	{
		double x;
		x = a - b;
		x *= x;
		return x = x/b;
	}
	
	int position (double a, double [] list)
	{
		int i = 0;
		while(list[i] != a)
			i ++;
		return i;
	}
	
	String crack(String str)
	{
		double [] frequency = freqs(str);
		double [] tempFreq = frequency; 
		double [] stdDev = new double [25];
		for(int i = 0; i < 25; i ++)
		{
			tempFreq = frequency;
			tempFreq = rotate(i, tempFreq);
			stdDev[i] = chisqr(tempFreq);
		}
		int position;
		double lowest = stdDev[0];
		for(int j = 0; j < 25; j++)
		{
			if(lowest > stdDev[j])
				lowest = stdDev[j];
		}
		position = position(lowest, stdDev);
		String answer = decode(position, str);
		System.out.println(answer);
		return answer;
			
	}
	boolean isletter(char c)
	{
		if(c <= 'z' && c >= 'a' )
			return true;
		return false;
			
	}
	
	

	
	public static void main(String args[])
	{
		CeaserCipher test = new CeaserCipher();
		/*int number = test.let2nat('z');
		System.out.println(number);
		
		char c  = test.nat2let(25);
		System.out.println(c);
		
		char letter = test.shift(3, 'H');
		System.out.println(letter);
		
		test.encode(3, "xyzabcdefghijklmnopqrstuvw");
		test.decode(3, "defghijklmnopqrstuvwxyzabc");
		test.lowers("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		test.count('e', "ereEic");
		test.percent(0, 12);
		test.freqs("abcaa");
		double[] list = {0,1,2,3,4,5,6,8};
		test.rotate(2, list);*/
		
		double [] os = {8.33333,0.0,0.0,0.0,8.33333,8.33333,0.0,8.33333,8.33333,0.0,8.33333,16.6667,0.0,8.33333,0.0,0.0,0.0,0.0,16.6667,0.0,8.33333,0.0,0.0,0.0,0.0,0.0};
		//test.rotate(10, os);
		test.crack(test.encode(3, "haskellisfun"));
		//double chi = test.chisqr(test.freqs("haskellisfun"));
		//test.decode(10, "myxqbkdevkdsyxc yx mywzvodsxq dro ohkw!");
		//System.out.println(chi);
		
	}
}
