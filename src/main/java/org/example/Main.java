package org.example;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println("_______Task1________");
        String string = readStringFromFile("file.txt");
        String s = numberValidator(string);
        System.out.println("Validated numbers:\r\n " + s);

        System.out.println("______Task2________");
        String usersData=readStringFromFile("file2.txt");
        ArrayList<User> users=parseUser(usersData);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
      //  String json ="[\r\n";
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<users.size();i++) {
            if(i==users.size()-1){
                stringBuilder.append(gson.toJson(users.get(i))).append("\r\n");
            }else {
                stringBuilder.append(gson.toJson(users.get(i))).append(",\r\n");
            }
        }

        String json="[\r\n"+stringBuilder.toString()+"]";
        writeJsonToFile("user.json",json);

        System.out.println("__________Task3________");
        String text = readStringFromFile("words.txt");
        System.out.println(wordFrequency(text));

    }
    private static void writeJsonToFile(String location, String json){
        try (FileWriter fileWriter = new FileWriter(location)) {
            fileWriter.write(json);
            fileWriter.flush();
            System.out.println("file: "+location+" has been written");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
     public static String wordFrequency(String text){

         text=text.replaceAll("\\r\\n"," ");
         HashMap<String,Integer> words = new HashMap<>();
         String[] wordArray =text.split(" ");
         for(String s: wordArray){
             if(((Integer)words.get(s))==null){
                 words.put(s,1);
             }else {
                 int temp =words.get(s);
                 words.put(s,temp+1);
             }
         }
         ArrayList<Integer> list =new ArrayList<>();
         LinkedHashMap<String,Integer> sortedWords = new LinkedHashMap<String,Integer>();
         for (Map.Entry<String, Integer> entry : words.entrySet()) {
             list.add(entry.getValue());
         }
         Collections.sort(list);
         Collections.reverse(list);
         for(Integer num: list) {
             for (Map.Entry<String, Integer> entry: words.entrySet()){
                 if (entry.getValue().equals(num)) {
                     sortedWords.put(entry.getKey(),num);
                 }
             }
         }
         StringBuilder sb=new StringBuilder();
         for (Map.Entry<String, Integer> entry: sortedWords.entrySet()){
             sb.append(entry.getKey()).append(" ").append(entry.getValue()).append("\r\n");
             }
         return sb.toString();
     }


    private static ArrayList<User> parseUser(String string){
        ArrayList<User> userArrayList= new ArrayList<User>();
        try(Scanner scanner =new Scanner(string)){
            scanner.nextLine();
            while(true){
                String s=scanner.nextLine();
                String name= s.substring(0,s.indexOf(" "));

                int age=Integer.parseInt(s.replaceAll(name," ").trim());
                User user = new User(name,age);
                userArrayList.add(user);
            }
        }catch(NoSuchElementException e){
            return userArrayList;
        }



    }
    private static String readStringFromFile(String location){
        String string="";
        try(FileReader fileReader =new FileReader(location)){
            char[] buffer = new char[256];
            int c;
            c=fileReader.read(buffer);
            if(c<256){
                buffer=Arrays.copyOf(buffer,c);
            }
            StringBuilder buf= new StringBuilder();
            for (char element: buffer){
                buf.append(element);
            }
            string=buf.toString();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return string;
    }

    private static String  numberValidator(String string){
        String result="";
        try(Scanner scanner =new Scanner(string)){
            while(true){
                String s=scanner.nextLine();
               if (s.matches("\\d{3}-\\d{3}-\\d{4}")){
                   result=result+s+"\r\n";
               }
                    if(s.matches("\\(\\d{3}\\) \\d{3}-\\d{4}")){
                    result=result+s+"\r\n";
                }
            }
        }catch(NoSuchElementException e){
            return result;
        }

    }

}