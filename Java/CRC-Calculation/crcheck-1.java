import java.io.IOException;
import java.util.Arrays;
import java.io.File;
import java.io.FileReader;

public class crcheck 
{
	static int poly = 0xA053;	// x15+x13+x6+x4+x1+1 => 1010 0000 0101 0011
	static boolean invalidFile = false;

	public static void main(String[] args) throws IOException
	{
		if (args.length != 2 || (!args[0].equals("c") && !args[0].equals("v")))
		{
			System.out.println("Invalid arguments!\n\nUsage <flag> <filename> where <flag> is:\n    c - calculate CRC\n    v - verify CRC\n\n");
			return;
		}
		
		File f = new File(args[1]);
		
		if (!f.exists())		//checks if file was found.
		{
			System.out.println("File was not found.");
			return;
		}
		
		// at this point, all argument are good and file exists...
		
		char[] input = readFile(f);

		if (invalidFile)			//checks if invalid characters are contained within file.
		{
			System.out.println("An invalid character was found in the file. Please only input letters and digits");
			return;
		}
		
		if (args[0].equals("c"))
			calcCRC(input);
		else if (args[0].equals("v"))
			verifyCRC(input);
	}
	
	static char[] readFile(File filename) throws IOException
	{
		FileReader fileReader = new FileReader(filename);
		char[] dirtyBuffer = new char[1024];
		char[] inputBuffer = new char[512];
		
		int charsRead = fileReader.read(dirtyBuffer);
		fileReader.close();

		int i;
		int j = 0;
		
		// copy dirty buffer to input buffer, but remove \n and \r which I was in the input files
		for (i = 0; i < dirtyBuffer.length && j < 512; i++)
		{
			if (dirtyBuffer[i] == '\n' || dirtyBuffer[i] == '\r')
				continue;
		
			inputBuffer[j++] = dirtyBuffer[i];
		}
		
		// pad the input buffer with periods if it's less than 512 bytes
		for (i = charsRead; i < inputBuffer.length - 8; i++)
			inputBuffer[i] = 0x2e;
		
		for (i = 0; i < inputBuffer.length; i++)
			if (inputBuffer[i] > 127)
				invalidFile = true;
		
		System.out.println("CRC-15 Input text from file\n");
		
		for (i = 0; i < inputBuffer.length; i++)
		{
			System.out.print(inputBuffer[i]);
			
			if (i % 64 == 63)
				System.out.println();
		}

		System.out.println();
		
		return inputBuffer;
	}
	
	static int calcCRC(char[] input)
	{
		return crc15(input, 0);
	}
	
	static void verifyCRC(char[] input)
	{
		char[] embeddedCRC = Arrays.copyOfRange(input, input.length - 8, input.length);
		String crcString = new String(embeddedCRC);
		
		int crcInFile = (int)Long.parseLong(crcString, 16);
		
		int newCRC = crc15(input, 0);
		
		if (newCRC == crcInFile)
			System.out.println("CRC-15 verification passed");
		else
			System.out.println("CRC-15 verification FAILED");
	}
	
	static int crc15(char[] input, int startingCRC)
	{
		int crc = startingCRC;
		int i, j;
		
		for (j = 0; j < input.length - 8; j++)
		{
			int b = (int)input[j];

			crc ^= (b << 7);

			for (i = 0; i < 8; i++)
			{
				if ((crc & 0x4000) != 0)
				{
					crc = (int)((crc << 1) ^ poly);
				}
				else
				{
					crc <<= 1;
				}
			}
			
			System.out.print(input[j]);
			
			if (j % 64 == 63)
				System.out.println(String.format(" - 0000%04x", crc));
		}

		System.out.println(String.format("0000%04x - 0000%04x\n", crc, crc));
		System.out.println(String.format("CRC-15 result : 0000%04x\n", crc));

		return crc;
	}

	static void PrintBinary(int value, int totalBits, int startBit, int bitsToPrint, String spacer)
	{
		for (int i = totalBits - 1; i >= 0; i--)
		{
			if (i % 4 == 3)
				System.out.print(spacer);

			if (i >= startBit || i < startBit - bitsToPrint)
				System.out.print(" ");
			else
				System.out.print((value & (1 << i)) > 0 ? "1" : "0");
		}
	}
}
