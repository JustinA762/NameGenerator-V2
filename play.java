import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class play 
{
    // Syllables Section
    public static boolean isVowel(char c)
    {
        boolean bVowel = false;

        String temp = (c + "").toLowerCase();
        c = temp.charAt(0);

        if (c == 'a' || 
            c == 'e' || 
            c == 'i' || 
            c == 'o' || 
            c == 'u' || 
            c == 'y')
            bVowel = true;
        else
            bVowel = false;

        return bVowel;
    }

    // Syllables Section
    public static int countSyllables(String word)
    {
        int count = 0;

        if (isVowel(word.charAt(0)) == true)
            count++;

        for (int i = 0; i < word.length()-1; i++)
        {
            if (isVowel(word.charAt(i)) == false && 
                isVowel(word.charAt(i+1)) == true)
                count++;
            //System.out.println(count);
        }

        return count;
    }

    public static String[] fileToArray(String filepath) throws IOException
    {
        List<String> listOfStrings = new ArrayList<String>();
        BufferedReader bf = new BufferedReader(new FileReader(filepath));
        String line = bf.readLine();

        while (line != null) {
            listOfStrings.add(line);
            line = bf.readLine();
        }

        bf.close();

        String[] array = listOfStrings.toArray(new String[0]);

        //System.out.println(Arrays.toString(array) + "\n" + array.length);

        return array;
    }

    public static String[] differentSyllables(String[] firstNames, String[] lastNames, int first, int last) throws IOException
    {
        List<String> theFirstNames = new ArrayList<String>();
        List<String> theLastNames = new ArrayList<String>();

        for (int i = 0; i < firstNames.length-1; i++)
        {
            if (countSyllables(firstNames[i]) == first)
                theFirstNames.add(firstNames[i]);
        }

        for (int i = 0; i < lastNames.length-1; i++)
        {
            if (countSyllables(lastNames[i]) == last)
                theLastNames.add(lastNames[i]);
        }

        String[] firstName = theFirstNames.toArray(new String[0]);
        String[] lastName = theLastNames.toArray(new String[0]);

        int tot = Math.min(firstName.length, lastName.length);
        String[] totNames = new String[tot];

        for (int i=0; i<totNames.length; i++)
            totNames[i] = firstName[i] + " " + lastName[i];

        return totNames;
    }
    
    public static void main(String[] args) throws IOException
    {
        String[] inital = fileToArray("names/male.txt");
        String[] last = fileToArray("names/last_name.txt");

        System.out.println(Arrays.toString(differentSyllables(inital, last, 1, 2)));
    }
}
