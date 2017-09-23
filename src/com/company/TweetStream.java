package com.company;

/* Code author:         David Crowley
   Originally posted:   10/2/12
   Taken from web:      9/16/17
   Link:                http://davidcrowley.me/?p=435

   Note: The code written by David Crowley was put in his Main class.
 */

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.io.*;

public class TweetStream {

    private ConfigurationBuilder cb;
    private StatusListener listener;
    private int tweetCnt = 0;
    private int tweetCntMax = 100000;

    public TweetStream(String inFile, String outFile) {

        cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("sXQYoMhhaJX0NDKEk3oukFNgq");
        cb.setOAuthConsumerSecret("pLcDLsayVwamDi7bXHiwVOJoZaiWV1UbclOT90jINCA4zqGvmR");
        cb.setOAuthAccessToken("906308020849823744-eF6NKO4Ld5u0qOXduwBzcKdTEYN5Rko");
        cb.setOAuthAccessTokenSecret("bxZGwmOhzEuBhA6HuSNgnUYPVCfQ6R4YLx6huzopk6zd8");

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            @Override
            public void onStatus(Status status) {

                try {

                    JSONObject jsonObj = new JSONObject(status);
                    File file = new File(inFile);

                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
                    BufferedWriter buffWriter = new BufferedWriter(fileWriter);

                    buffWriter.write(jsonObj + "\n");

                    System.out.println(tweetCnt);

                    buffWriter.close();
                    fileWriter.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (++tweetCnt == tweetCntMax){

                    System.out.println(Integer.toString(tweetCnt) + " tweets collected");

                    ExtractHashtags extractTags = new ExtractHashtags(inFile, outFile);

                    twitterStream.cleanUp();
                    twitterStream.shutdown();
                }
            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub
            }
        };

        FilterQuery fq = new FilterQuery();

        String keywords[] = {"sports", "movies", "tv", "politics"};

        fq.track(keywords);

        twitterStream.addListener(listener);
        twitterStream.filter(fq);
    }
}
