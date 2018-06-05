package com.jsfexample.model.converters;

import com.jsfexample.model.Question;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileUtils {


    public static List<Question> parse(File file) {
        List<Question> questions = new ArrayList<>();
        try {
            Reader reader = new BufferedReader(new FileReader(file));
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withTrim());
            for (CSVRecord record : parser) {
                Question question = new Question();
                question.setText(record.get(0));
                question.setAnswer1(record.get(1));
                question.setAnswer2(record.get(2));
                question.setAnswer3(record.get(3));
                question.setAnswer4(record.get(4));
                question.setCorrectAnswer(record.get(5));
                questions.add(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public static File createFile(Part filePart) {
        String path = "D:/Facultate/JSFExample/temp/";
        String fileName = getFileName(filePart);
        OutputStream out = null;
        InputStream filecontent = null;
        File file = new File(path + File.separator + fileName);

        try {
            out = new FileOutputStream(file);
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private static String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
