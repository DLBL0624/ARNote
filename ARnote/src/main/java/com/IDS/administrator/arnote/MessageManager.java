package com.IDS.administrator.arnote;

import java.util.ArrayList;

public class MessageManager {

    public static ArrayList<Message> messList = new ArrayList<Message>();

    public static double latitude;

    public static double longitude;

    public static int index = 0;

    public static void reorderMessage()
    {
        for(int i =0; i<messList.size();i++) if(messList.get(i).getIndex()!=i)messList.get(i).setIndex(i);
        index = messList.size()-1;
    }

}

