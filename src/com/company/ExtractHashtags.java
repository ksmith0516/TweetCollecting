package com.company;

import java.io.*;
import java.util.Iterator;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ExtractHashtags {

    private int cnt = 0;

    public ExtractHashtags(String inFile, String outFile){

        extract(inFile, outFile);
    }

    private void extract(String inFile, String outFile){

        JSONParser parser = new JSONParser();

        try {

            File file = new File(inFile);
            BufferedReader buffReader = new BufferedReader(new FileReader(file));

            String line;

            while ((line = buffReader.readLine()) != null) {

                JSONObject jsonObject = (JSONObject) parser.parse(line);
                JSONArray hashtags = (JSONArray) jsonObject.get("hashtagEntities");

                if (!hashtags.isEmpty()) {

                    for (int i = 0; i < hashtags.size(); i++) {

                        JSONObject tag = (JSONObject) hashtags.get(i);
                        store(outFile, tag.get("text").toString());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void store(String outFile, String hashtag){

        File file = new File(outFile);
        BufferedWriter buffWriter;

        try  {

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            buffWriter = new BufferedWriter(fileWriter);

            buffWriter.write(hashtag + "\n");

            System.out.println(++cnt);

            buffWriter.close();
            fileWriter.close();


        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
