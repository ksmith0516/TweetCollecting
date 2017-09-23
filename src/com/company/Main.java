package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        String inFile = "tweets.json";
        String outFile = "hashtags.txt";
        TweetStream tweetStream;
        ExtractHashtags extractHashtags;

        File in = new File(inFile);
        File out = new File(outFile);

        if(!in.exists()) {
            tweetStream = new TweetStream(inFile, outFile);
        }
        else if (!out.exists()){
            extractHashtags = new ExtractHashtags(inFile, outFile);
        }
    }
}
