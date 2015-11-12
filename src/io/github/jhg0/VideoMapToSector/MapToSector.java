package io.github.jhg0.VideoMapToSector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MapToSector
{

    private static ArrayList<String> lines = new ArrayList<>();

    public static void main(String[] args)
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Josh\\Downloads\\na.xml"));
            String s = in.readLine();
            while (s != null)
            {
                lines.add(s);
                s = in.readLine();
            }
            in.close();
        } catch (Exception ignored)
        {
        }


        try
        {
            PrintWriter out = new PrintWriter(new FileWriter("C:\\Users\\Josh\\Downloads\\out.txt"));
            for (String s : lines)
            {
                String aa = "";
                String ab = "";
                String ba = "";
                String bb = "";
                String[] q = s.split(" ");
                for (String a : q)
                {
                    String SPACE = "                          ";
                    if (!aa.equals("") && !ba.equals("")) out.print("\n" + SPACE + aa + " " + ab + " " + ba + " " + bb);
                    if (a.contains("StartLat"))
                        aa = latToDMS(Double.parseDouble(a.split("=")[1].replace("\"", "")));
                    else if (a.contains("StartLon"))
                        ab = lonToDMS(Double.parseDouble(a.split("=")[1].replace("\"", "")));
                    else if (a.contains("EndLat"))
                        ba = latToDMS(Double.parseDouble(a.split("=")[1].replace("\"", "")));
                    else if (a.contains("EndLon"))
                        bb = lonToDMS(Double.parseDouble(a.split("=")[1].replace("\"", "")));

                }
            }
            out.close();
        } catch (Exception ignored)
        {
        }
    }

    private static String latToDMS(double d)
    {
        String f;
        int deg = (int) d;
        int q = deg > 0 ? 1 : -1;
        int min = (int) ((d - deg) * 60);
        int sec = (int) (((d - deg) * 60 - min) * 60);
        int ext = (int) Math.round((((((d - deg) * 60 - min) * 60) - sec) * 1000));
        f = (q == 1 ? deg < 10 ? "N00" + deg : deg < 100 ? "N0" + deg : "N" + deg : deg > -10 ? "S00" + deg * -1 : deg > -100 ? "S0" + deg * -1 : "S" + deg * -1) + "." + (min * q < 10 ? "0" + min * q : min * q) + "." + (sec * q < 10 ? "0" + sec * q : sec * q) + "." + (ext * q < 10 ? "00" + ext * q : ext * q < 100 ? "0" + ext * q : ext * q);
        return f;
    }

    private static String lonToDMS(double d)
    {
        String f;
        int deg = (int) d;
        int q = deg > 0 ? 1 : -1;
        int min = (int) ((d - deg) * 60);
        int sec = (int) (((d - deg) * 60 - min) * 60);
        int ext = (int) Math.round((((((d - deg) * 60 - min) * 60) - sec) * 1000));
        f = (q == 1 ? deg < 10 ? "E00" + deg : deg < 100 ? "E0" + deg : "E" + deg : deg > -10 ? "W00" + deg * -1 : deg > -100 ? "W0" + deg * -1 : "W" + deg * -1) + "." + (min * q < 10 ? "0" + min * q : min * q) + "." + (sec * q < 10 ? "0" + sec * q : sec * q) + "." + (ext * q < 10 ? "00" + ext * q : ext * q < 100 ? "0" + ext * q : ext * q);
        return f;
    }
}