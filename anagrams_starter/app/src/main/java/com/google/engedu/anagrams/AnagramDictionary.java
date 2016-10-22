package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import android.util.Log;
import java.util.*;
import java.util.Map.Entry;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private static int wordLength = DEFAULT_WORD_LENGTH;

    private Random random = new Random();
    HashSet wordSet = new HashSet();
    HashMap<String, ArrayList<String>> lettersToWord= new HashMap<String, ArrayList<String>>();
    HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();


    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        ArrayList<String> wordMapList;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            ArrayList<String> wordList = new ArrayList<String>();
            wordList.add(word);
            wordSet.add(word);


            if (sizeToWords.containsKey(word.length())) {
                wordMapList = sizeToWords.get(word.length());
                wordMapList.add(word);
                sizeToWords.put(word.length(), wordMapList);
            } else {
                ArrayList<String> newWordList = new ArrayList<>();
                newWordList.add(word);
                sizeToWords.put(word.length(), newWordList);
            }


            String sortedWord = sortLetters(word);
            ArrayList<String> list = new ArrayList<>();
            if (!lettersToWord.containsKey(sortedWord)) {
                list.add(word);
                lettersToWord.put(sortedWord,list);
            }
            else {
                list = lettersToWord.get(sortedWord);
                list.add(word);
                lettersToWord.put(sortedWord, list);
            }
        }

    }

    public boolean isGoodWord(String word, String base) {


        if(wordSet.contains(word) && !(word.contains(base)))
        {
            Log.i("GoodWord","true");
            return true;
        }
        else
        {
            Log.i("GoodWord","False");
            return false;
        }

    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<>();
        Log.i("TargetWord",targetWord);
        String sortedString = sortLetters(targetWord);
        Log.i("NewString",sortedString);
        boolean status = true;
        if(targetWord.length() == sortedString.length()) {
            char[] s1Array = targetWord.toLowerCase().toCharArray();
            char[] s2Array = sortedString.toLowerCase().toCharArray();
            Arrays.sort(s1Array);
            Arrays.sort(s2Array);
            if(Arrays.equals(s1Array, s2Array))
            {
                result = lettersToWord.get(sortedString);
            }
        }
        return result;
    }



    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> tempList;
        ArrayList<String> result = new ArrayList<String>();
        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            String anagram = word + alphabet;
            String sortedAnagram = sortLetters(anagram);
            if (lettersToWord.containsKey(sortedAnagram)) {
                tempList = lettersToWord.get(sortedAnagram);
                for (int i = 0; i < tempList.size(); i++) {
                    if (!(tempList.get(i).contains(word))) {
                        result.add(tempList.get(i));
                    }
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        int randomNumber;
        String starterWord;

        do {
            randomNumber = random.nextInt(sizeToWords.get(wordLength).size());
            starterWord = sizeToWords.get(wordLength).get(randomNumber);
        } while (getAnagramsWithOneMoreLetter(starterWord).size() < MIN_NUM_ANAGRAMS);

        if (wordLength < MAX_WORD_LENGTH) {
            wordLength++;
        }

        return starterWord;
    }

    public String sortLetters(String sortString)
    {
        int wordLength=sortString.length();
        char b[] = new char[wordLength];
        for(int i=0;i<wordLength;i++)
            b[i] = sortString.charAt(i);
        char t;
        for(int j=0;j<wordLength-1;j++)
        {
            for(int k=0;k<wordLength-1-j;k++)
            {
                if(b[k]>b[k+1])
                {
                    t=b[k];
                    b[k]=b[k+1];
                    b[k+1]=t;
                }
            }
        }
        StringBuffer result = new StringBuffer();
        for(int m=0;m<wordLength;m++)
            result.append( b[m] );
        String mynewstring = result.toString();
    return mynewstring;
    }
}
