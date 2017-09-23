package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        String inFile = "tweets.json";
        String outFile = "hashtags.txt";
        TweetStream tweetStream;

        File file = new File(inFile);

        if(!file.exists()) {
            tweetStream = new TweetStream(inFile, outFile);
        }
        else {
            ExtractHashtags extractHashtags = new ExtractHashtags(inFile, outFile);
        }
    }
}
