package com.google.engedu.ghost;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    private ArrayList<String> evenWords;
    private ArrayList<String> oddWords;
    private ArrayList<String> prefixWords;
    private  String word;


    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        String word;
        if(prefix.equals(""))
        {
            word = words.get(new Random().nextInt(words.size()));
            return word;
        }
        else
        {
            return binarySearch(words, prefix);
        }
    }

    @Override
    public String getGoodWordStartingWith(String prefix,boolean userTurn) {
        String selected = null;
        evenWords = new ArrayList<>();
        oddWords = new ArrayList<>();
        prefixWords = new ArrayList<>();

        for(int i =0; i<words.size();i++) {

            selected = words.get(i);

            if (selected.startsWith(prefix))
            {
                prefixWords.add(selected);
            }
        }
        for(int i =0;i<prefixWords.size();i++)
        {
            selected = prefixWords.get(i);
            if(selected.length() %2 == 0)
            {
                evenWords.add(selected);
            }
            else
            {
                oddWords.add(selected);
            }
        }


        if(userTurn == true)
        {
            return binarySearch(evenWords, prefix);
        }
        else
        {
            if(prefix.equals(""))
            {
                word = oddWords.get(new Random().nextInt(words.size()));
                return word;
            }
            else
            {
                return binarySearch(oddWords, prefix);
            }
        }
    }
    public String binarySearch(ArrayList <String> selectedList, String pFix)
    {
        int low = 0;
        int high = selectedList.size() - 1;
        while (high >= low) {
            int middle = (high + low) / 2;
            word = selectedList.get(middle);
            if (word.startsWith(pFix))
            {
                return word;
            }
            if (word.compareTo(pFix) < 0)
            {
                low = middle + 1;
            }
            else
            {
                high = middle - 1;
            }
        }
        return null;
    }

}
