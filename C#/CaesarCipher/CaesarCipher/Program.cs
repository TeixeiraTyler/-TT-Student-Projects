// Tyler Teixeira

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CaesarCipher
{
    static class StringExtensions
    {
        static void Main(string[] args)
        {
            string cracked = StringExtensions.Cracked("myxqbkdevkdsyxc yx mywzvodsxq dro ohkw!");

            Console.WriteLine(cracked);
            Console.Read();
        }

        static int let2nat(char c)
        {
            if (c == ' ' || c == '_')
                return (int)c;
            else
                return (int)c - 'a';
        }

        static char nat2let(int x)
        {
            if (x == ' ' || x == '_')
                return (char)x;
            else
                return (char)(x + 'a');
        }

        static char shift(int x, char c)
        {
            if (let2nat(c) >= 0 && let2nat(c) <= 25)
                return nat2let((let2nat(c) + x) % 26);
            else
                return c;
        }

        static char[] encode(int x, char[] code)
        {
            char[] newCode = new char[code.Length];

            for (int i = 0; i < newCode.Length; i++)
            {
                newCode[i] = shift(x, code[i]);
            }

            return newCode;
        }

        static char[] decode(int x, char[] code)
        {
            char[] newCode = new char[code.Length];

            for (int i = 0; i < newCode.Length; i++)
            {
                newCode[i] = shift(26 - x, code[i]);
            }

            return newCode;
        }

        static int lowers(char[] code)
        {
            int count = 0;

            foreach(char c in code)
            {
                if (c >= 97 && c <= 122)
                    count++;
            }

            return count;
        }

        static int count(char ch, char[] code)
        {
            int count = 0;

            foreach(char c in code)
            {
                if (c == ch)
                    count++;
            }

            return count;
        }

        static double percent(int x, int y)
        {
            return ((double)x / (double)y) *100;
        }

        static double[] freqs(char[] code)
        {
            char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            double[] freq = new double[26];

            for (int i = 0; i < 26; i++)
            {
                freq[i] = percent(count(alphabet[i], code), lowers(code));
            }

            return freq;
        }

        static char[] rotate(int x, char[] code)
        {
            char[] newCode = new char[code.Length];

            for (int i = 0; i < code.Length; i++)
            {
                newCode[i] = code[(i + x) % code.Length];
            }

            return newCode;
        }

        static double Chisqr(double[] x, double[] y)
        {
            //chisqr:: [Float]-> [Float]->Float
            //chisqr os es = sum[(x - y) ^ 2 / y | (x, y) < -zip os es]

            double sum = 0;

            for (var i = 0; i < x.Length; i++)
            {
                sum += Math.Pow(x[i] - y[i], 2) / y[i];
            }

            return sum;
        }

        static int position(char c, char[] code)
        {
            int pos = 0;
 
            for (int i = 0; i < code.Length; i++)
            {
                if (c == code[i])
                {
                    pos = i;
                    break;
                }
            }

            return pos;
        }

        static string Cracked(string code)
        {
            double[] table = { 8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0, 0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0, 6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1 };

            double[] sums = new double[26];
            int minimumIndex = int.MaxValue;
            double minimum = double.MaxValue;
            char[] newCode = code.ToCharArray();

            for (var index = 0; index < 26; index++)
            {
                sums[index] = Chisqr(freqs(decode(index, newCode)), table);

                if (sums[index] < minimum)
                {
                    minimum = sums[index];
                    minimumIndex = index;
                }
            }

            string result = new string(decode(minimumIndex, newCode));
            return result;
        }
    }
}
