package com.medvid.andriy.housemanager.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Андрій on 6/3/2015.
 */
public class FileUtils {
    public static void writeGrammarToFile(String grammarName, File file, List<String> grammarList) {
        String header = "#JSGF V1.0;\n\n";
        String grammarTitle = String.format("grammar %s;\n\n", grammarName);
        String grammarListAdvertisement = String.format("public <%s> = ", grammarName);
        String end = ";";

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);

            fileWriter.write(header);
            fileWriter.write(grammarTitle);
            fileWriter.write(grammarListAdvertisement);

            for (int i = 0; i < grammarList.size(); ++i) {
                fileWriter.write((grammarList.get(i)));
                if (i < grammarList.size() - 1) {
                    fileWriter.write("|\n");
                }
            }
            fileWriter.write(end);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
