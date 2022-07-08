import java.util.Scanner;
import java.io.*;
/**
 * This is a program that copies a directory to a user-identified location.
 * @author hayleywieland
 * @version 1.0
 * @since 2022-3-9
 * */
public class Main {

    public static void main (String[] args) {
        /** Create a Scanner for user input */
        Scanner kb = new Scanner(System.in);

        /** Get the location of the directory to be copied.
         * Save it to a file object*/
        System.out.println("Enter the directory to be copied: ");
        String toBeCopied = kb.nextLine();
        File toCopy = new File(toBeCopied);

        /**Get the name of the directory to be copied by splitting the location
         * then saving the last item of the array as the name of the directory
         * */
        String [] split = toBeCopied.split("/");
        String directoryName = split[split.length-1];

        /** Establish the location of the parent for the new set of directories with user input*/
        System.out.println("Enter the location to copy to: ");
        String copyLocation = kb.nextLine();

        /**if the user doesn't create an absolute path, create a relative path from the current location */
        if(!copyLocation.endsWith("/") && !copyLocation.endsWith("\\")){
            copyLocation = "./" + copyLocation;
        }

        /** make a new directory in the new location with the same name as the source directory
         * */
        File copyDirectory = new File(copyLocation + directoryName);
        try{
            copyDirectory.mkdir();
        }catch(Exception e){
            e.printStackTrace();
        }

        /** Create a destination String that tells the program where to copy the files to
         * */
        String destination = copyLocation + directoryName + "/";

        /** create a File array with the file class objects for each item in the contents of the source directory
         * */
        try {
            File[] filesToCopy = toCopy.listFiles();
            createFiles(filesToCopy, destination);
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    /**This is a method that iterates through the array of files
     * If it's a file, it copies the file to the new directory
     * If it's a directory, it creates the directory, and then calls the method again
     *
     * @param toCopy is an array of the files to copy
     * @param destination is a String with the path to the file
     * */
    public static void createFiles(File [] toCopy, String destination) {
         try {
             String parentDirectory = destination; /**save the parent directory*/
             for(File file : toCopy)
                 if (file.isFile()) {
                     copyFile(file, destination + file.getName());
                 } else if(file.isDirectory()){
                     destination = parentDirectory; /**Make the directory part of its parent directory*/
                     destination += file.getName() + "/"; /**add the name of the directory to the destination*/
                     File copyDirectory = new File(destination);/**create the directory*/
                     copyDirectory.mkdir();
                     toCopy = file.listFiles();
                     createFiles(toCopy, destination);
                 }
             destination = parentDirectory; /**return to the parent directory when done*/
         } catch (Exception e){
         System.out.println(e.getStackTrace());
         }

         }

    /** This is a method that copies a file.
     * It is based off of CopyExampleB from the demo files for this module
     * It uses a buffer stream to copy files
     *
     * @param file is the file to be copied
     * @param destination is a String with the path to the file
     * */
    public static void copyFile(File file, String destination){
        try {
            File fileToCopy = file;
            File copiedFile = new File(destination); //copies the destination path, but need to copy the name of the file too

            /**Create file streams for both files
             * */
            FileInputStream copyingStream = new FileInputStream(file);
            FileOutputStream destinationStream = new FileOutputStream(copiedFile);

            /**Create buffer streams for both file streams
             * */

            BufferedInputStream bufferedCopyingStream = new BufferedInputStream(copyingStream, 8000);
            BufferedOutputStream bufferedDestinationStream = new BufferedOutputStream(destinationStream, 6000);

            /**Transfer the files & then close the streams
             * */

            int transfer;
            while((transfer = bufferedCopyingStream.read()) != -1 ){
                bufferedDestinationStream.write(transfer);
            }

            bufferedCopyingStream.close();
            bufferedDestinationStream.close();

        } catch (Exception e){
            System.out.println(e.getStackTrace());
        }

    }
    }