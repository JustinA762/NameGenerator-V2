import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class NG 
{
    /* --------------------------------- User Interaction ----------------------------------- */
    // Choosing a gender
    public static int choice_Gender(String gender)
    {
        // Gender name requirements
        /*
         * Male
         * Female
         * Neutral
         * 
         */

        String lowerCase = gender.toLowerCase();
        int result = 0;

        if (lowerCase.equals("male"))
            result = 1;
        if (lowerCase.equals("female"))
            result = 2;
        if (lowerCase.equals("neutral"))
            result = 3;

        return result;
    }

    // Choosing a Role 
    public static int choice_Class(String role)
    {
        // Role name requirements
        /*
         * Low
         * Middle
         * Upper
         */

        String lowerCase = role.toLowerCase();
        int result = 0;

        if (lowerCase.equals("low"))
            result = 1;
        if (lowerCase.equals("middle"))
            result = 2;
        if (lowerCase.equals("upper"))
            result = 3;

        return result;
    }

    /* ------------------------------- Other and Helpful ------------------------------------- */
    // random list of numbers
    public static Integer[] randomNum(int length, String[] array)
    {
        Random rand = new Random();
        Set<Integer> set = new LinkedHashSet<Integer>();

        while (set.size() < length)
        {
            set.add(rand.nextInt(array.length));
        }

        Integer[] randNum = set.toArray(new Integer[0]);

        return randNum;
    }

    // Converting a file to an array
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

    /* --------------------------------- Syllables and Sound ----------------------------------- */
    // Checking if a character is a vowel
    // Syllables Section part 1
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

    // Counting Syllables for a word
    // Syllables Section part 2
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

    // Selecting names depending on role (low and middle <= 2 Syllables) (upper > 2 Syllables)
    // Syllables Section part 3
    public static String[] namesForRoles(String[] names, int role) throws IOException 
    {
        List<String> list = new ArrayList<String>();

        if (role == 1 || role == 2)
        {
            for (int i = 0; i < names.length-1; i++)
            {
                if (countSyllables(names[i]) <= 2)
                    list.add(names[i]);
            }
        }
        if (role == 3)
        {
            for (int i = 0; i < names.length-1; i++)
            {
                if (countSyllables(names[i]) >= 2)
                    list.add(names[i]);
            }
        }
        String[] output = list.toArray(new String[0]);

        return output;
    }

    // Takes the first names and last names and orders them to have the same first characters or same last characters
    // Sounds part 1
    // Syllables part 4
    public static String[] sameCharacter(String position, int gender, int role, char selectedChar) throws IOException
    {
        List<String> firstSet = new ArrayList<String>();
        List<String> lastSet = new ArrayList<String>();

        String[] inital = fileToArray("names/male.txt");
        String[] last = fileToArray("names/last_name.txt");
        String[] nick = fileToArray("names/nicknames.txt");

        if (gender == 1)
            inital = fileToArray("names/male.txt");
        if (gender == 2)
            inital = fileToArray("names/female.txt");
        if (gender == 3)
            inital = fileToArray("names/neutral.txt");

        if (position == "first")
        {
            // First Name
            for (int i=0; i<inital.length; i++)
            {
                if (selectedChar == inital[i].charAt(0))
                {
                    firstSet.add(inital[i]);
                }
            }

            // Last Name
            for (int i=0; i<last.length; i++)
            {
                if (selectedChar == last[i].charAt(0))
                {
                    lastSet.add(last[i]);
                }
            }
        }
        if (position == "end")
        {
            for (int i=0; i<inital.length; i++)
            {
                if (selectedChar == inital[i].charAt(inital[i].length()-1))
                {
                    firstSet.add(inital[i]);
                }
            }

            for (int i=0; i<last.length; i++)
            {
                if (selectedChar == last[i].charAt(last[i].length()-1))
                {
                    lastSet.add(last[i]);
                }
            }
        }

        // System.out.println(firstSet);
        // System.out.println("first - " + inital.length);
        // System.out.println("firstSet - " + firstSet.size());
        // System.out.println(lastSet);
        // System.out.println("last - " + last.length);
        // System.out.println("lastSet - " + lastSet.size());

        String[] firstName = firstSet.toArray(new String[0]);
        String[] lastName = lastSet.toArray(new String[0]);

        // Roles included (Syllables)
        String[] firstNameWithRole = namesForRoles(firstName, role);
        String[] lastNameWithRole = namesForRoles(lastName, role);

        Integer[] randNumFirst = randomNum(firstNameWithRole.length, firstNameWithRole);
        Integer[] randNumLast = randomNum(lastNameWithRole.length, lastNameWithRole);
        Integer[] randNumNick = randomNum(nick.length, nick);

        String[] newfirst = new String[firstNameWithRole.length];
        String[] newlast = new String[lastNameWithRole.length];
        String[] newnick = new String[nick.length];

        for (int i = 0; i < firstNameWithRole.length; i++) {
            int ran = randNumFirst[i];
            //System.out.println(ran);
            newfirst[i] = firstNameWithRole[ran];
        }

        for (int i = 0; i < lastNameWithRole.length; i++) {
            int ran = randNumLast[i];
            //System.out.println(ran);
            newlast[i] = lastNameWithRole[ran];
        }

        for (int i = 0; i < nick.length; i++) {
            int ran = randNumNick[i];
            //System.out.println(ran);
            newnick[i] = nick[ran];
        }

        int tot = Math.min(firstNameWithRole.length, lastNameWithRole.length);
        int tot2 = Math.min(tot, nick.length);
        String[] totNames = new String[tot2];

        for (int i=0; i<totNames.length; i++)
            totNames[i] = newfirst[i] + " '"  + newnick[i] + "' " + newlast[i];

        return totNames;
    }

    /* -------------------------------- Putting it togehter ------------------------------------ */
    // Get first letters of first names
    // Final part 1
    public static String[] getAllMatching(String position, int gender, int role) throws IOException
    {
        Set<Character> theCharacter = new HashSet<Character>();
        String[] inital = fileToArray("names/male.txt");

        if (gender == 1)
            inital = fileToArray("names/male.txt");
        if (gender == 2)
            inital = fileToArray("names/female.txt");
        if (gender == 3)
            inital = fileToArray("names/neutral.txt");

        if (position == "first")
        {
            for (int i =0; i <inital.length; i++)
                theCharacter.add(inital[i].charAt(0));
        }
        if (position == "end")
        {
            for (int i =0; i <inital.length; i++)
            {
                int end = inital[i].length()-1;
                theCharacter.add(inital[i].charAt(end));
            }
        }
        //System.out.println(firstCharacter); 
        
        Object[] theChar = theCharacter.toArray();
        List<String> fullNames2 = new ArrayList<String>();

        for (int i=0; i < theChar.length; i++)
        {
            List<String> fullNames = Arrays.asList(sameCharacter(position, gender, role, (char) theChar[i]));
            //System.out.println(fullNames);
            fullNames2.addAll(fullNames);
            //System.out.println(fullNames2);
        }

        String[] finalList = fullNames2.toArray(new String[0]);

        return finalList;
    }

    // Putting the Conditions of the names together
    // Final part 2
    public static String[] namesConditions(int gender, int role) throws IOException
    {
        List<String> fullNames = new ArrayList<String>();
        List<String> frontLetterNames = Arrays.asList(getAllMatching("first", gender, role));
        //System.out.println(frontLetterNames + " - " + frontLetterNames.size());
        List<String> endLetterNames = Arrays.asList(getAllMatching("end", gender, role));
        //System.out.println(endLetterNames + " - " + endLetterNames.size());
        fullNames.addAll(frontLetterNames);
        fullNames.addAll(endLetterNames);
        //System.out.println(fullNames.size());

        // Randomize the placement of the names so it isn't mainly separated by firstLetter and endLetter
        String[] nameWithNick = fullNames.toArray(new String[0]);
        Integer[] randNumName = randomNum(nameWithNick.length, nameWithNick);
        String[] finalName = new String[nameWithNick.length];

        for (int i = 0; i < nameWithNick.length; i++) {
            int ran = randNumName[i];
            //System.out.println(ran);
            finalName[i] = nameWithNick[ran];
        }
        
        return finalName;
    }

    // Final Print
    public static void toPrint(int gender, int role) throws IOException
    {
        String[] theFullNames = namesConditions(gender, role);
        String[] name = new String[10];

        for (int i=0; i < name.length; i++)
        {
            name[i] = theFullNames[i];
        }

        for (String names: name)
        {
            System.out.println(names);
        }
    }

    public static void main(String[] args) throws IOException 
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("What is your Gender?");
        System.out.println("--------------------");
        System.out.println("* Male");
        System.out.println("* Female");
        System.out.println("* Neutral");
        System.out.println("--------------------");
        String gender = scanner.nextLine();

        System.out.println("What is your social class?");
        System.out.println("--------------------");
        System.out.println("* Low");
        System.out.println("* Middle");
        System.out.println("* Upper");
        System.out.println("--------------------");
        String role = scanner.nextLine();
        System.out.println("--------------------" + "\n");


        Scanner repeat = new Scanner(System.in);
        int toRepeat = 0;

        while (toRepeat != 3)
        {
            toPrint(choice_Gender(gender), choice_Class(role));
            System.out.println("--------------------");
            System.out.println("Would you like to get a different set of names?");
            System.out.println("--------------------");
            System.out.println("*1 Yes");
            System.out.println("*2 Yes, but with different variables");
            System.out.println("*3 No");
            System.out.println("--------------------");
            toRepeat = repeat.nextInt();
            if (toRepeat == 2)
            {
                System.out.println("What is your Gender?");
                System.out.println("--------------------");
                System.out.println("* Male");
                System.out.println("* Female");
                System.out.println("* Neutral");
                System.out.println("--------------------");
                gender = scanner.nextLine();

                System.out.println("What is your social class?");
                System.out.println("--------------------");
                System.out.println("* Low");
                System.out.println("* Middle");
                System.out.println("* Upper");
                System.out.println("--------------------");
                role = scanner.nextLine();
                System.out.println("--------------------" + "\n");

                toPrint(choice_Gender(gender), choice_Class(role));
            }
            if (toRepeat == 3)
                System.exit(0);
        }
    }   
}