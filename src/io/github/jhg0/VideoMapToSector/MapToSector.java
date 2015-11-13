package io.github.jhg0.VideoMapToSector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MapToSector
{

    private ArrayList<String> lines = new ArrayList<>();

    private String latToDMS(double d)
    {
        int deg = (int) d;
        int q = deg > 0 ? 1 : -1;
        int min = (int) ((d - deg) * 60);
        int sec = (int) (((d - deg) * 60 - min) * 60);
        int ext = (int) Math.floor((((((d - deg) * 60 - min) * 60) - sec) * 1000));
        if (ext == 1000) ext--;
        else if (ext == -1000) ext++;
        return (q == 1 ? "N" : "S") + String.format("%03d.", deg * q) + String.format("%02d.", min * q) + String.format("%02d.", sec * q) + String.format("%03d", ext * q);
    }

    private String lonToDMS(double d)
    {
        int deg = (int) d;
        int q = deg > 0 ? 1 : -1;
        int min = (int) ((d - deg) * 60);
        int sec = (int) (((d - deg) * 60 - min) * 60);
        int ext = (int) Math.floor((((((d - deg) * 60 - min) * 60) - sec) * 1000));
        if (ext == 1000) ext--;
        else if (ext == -1000) ext++;
        return (q == 1 ? "E" : "W") + String.format("%03d.", deg * q) + String.format("%02d.", min * q) + String.format("%02d.", sec * q) + String.format("%03d", ext * q);
    }

    public String convert(File fileIn, File fileOut)
    {
        long z = System.currentTimeMillis();
        String name = "Sector Output";
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(fileIn));
            String s = in.readLine();
            while (s != null)
            {
                lines.add(s);
                s = in.readLine();
            }
            in.close();
        } catch (Exception e)
        {
            return "0 0";
        }

        if (lines.size() >= 2 && lines.get(1).contains("LongName") && lines.get(1).split("LongName=\"").length > 1)
            name = lines.get(1).split("LongName=\"")[1].split("\"")[0];

        try
        {
            PrintWriter out = new PrintWriter(new FileWriter(fileOut + File.separator + name + ".sct2"));
            final String SPACE = "                          ";

            if (name.length() > SPACE.length() - 1) name = name.substring(0, SPACE.length() - 1);
            out.print(name + SPACE.substring(name.length()) + "N000.00.00.000 E000.00.00.000 N000.00.00.000 E000.00.00.000");
            for (String s : lines)
            {
                String aa = "";
                String ab = "";
                String ba = "";
                String bb = "";
                String[] q = s.split(" ");
                for (String a : q)
                {
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
        } catch (Exception e)
        {
            return "0 0";
        }
        return lines.size() + " " + (System.currentTimeMillis() - z);
    }
}