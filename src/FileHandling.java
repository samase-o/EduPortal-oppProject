package pl.project;

import java.io.*;
import java.nio.file.*;
import java.util.*;
public class FileHandling {

    final private String filePath;
    File file;

    public FileHandling(String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
    }

    public String readFile() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return content.toString();
    }

    public void writeFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            writer.newLine();
            System.out.println("Content written to the file successfully.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void appendFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(content);
            writer.newLine();
            System.out.println("Content appended to the file successfully.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createFile() {
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to delete the file
    public void deleteFile() {
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("File doesn't exist.");
        }
    }

    public boolean checkFileExistence() {
        return file.exists();
    }


    public void renameFile(String newFileName) {
        File newFile = new File(file.getParent(), newFileName);
        if (file.exists()) {
            boolean success = file.renameTo(newFile);
            if (success) {
                System.out.println("File renamed successfully.");
                this.file = newFile;
            } else {
                System.out.println("Failed to rename the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void copyFile(String destinationPath) {
        File destinationFile = new File(destinationPath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("File copied successfully.");

        } catch (IOException e) {
            System.out.println("Error copying the file: " + e.getMessage());
        }
    }

    public long getSize() {
        if (file.exists()) {
            return file.length();
        } else {
            System.out.println("File doesn't exist.");
            return -1;
        }
    }

    public void moveFile(String destinationPath) {
        Path source = Paths.get(this.filePath);
        Path destination = Paths.get(destinationPath);try {
            Files.move(source, destination);
            System.out.println("File moved successfully.");
        } catch (FileAlreadyExistsException fae) {
            System.out.println("Destination file already exists.");
        } catch (IOException e) {
            System.out.println("Error occurred while moving the file: " + e.getMessage());
        }
    }


    public String readLine(int lineNumber) {
        String lineContent = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int lineCount = 1;
            String line;
            while ((line = br.readLine()) != null) {
                if (lineCount == lineNumber) {
                    lineContent = line;
                    break;
                }
                lineCount++;
                //return lineContent;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lineContent;
    }


    public void appendLine(int lineNumber, String dataToAppend) {
        try {
            File fl = new File(filePath);
            List<String> lines;
            try (BufferedReader reader = new BufferedReader(new FileReader(fl))) {
                lines = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }

            if (lineNumber <= lines.size() + 1) {
                lines.add(lineNumber - 1, dataToAppend); // Subtract 1 as ArrayList index starts from 0
            } else {
                System.out.println("Line number out of range!");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String lineToWrite : lines) {
                    writer.write(lineToWrite);
                    writer.newLine();
                }
            }
            System.out.println("String "+dataToAppend+" appended successfully at line "+ lineNumber+".");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteLine(int lineNumber) {
        try {
            File fl = new File(filePath);
            List<String> lines;
            try (BufferedReader reader = new BufferedReader(new FileReader(fl))) {
                lines = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }

            if (lineNumber <= lines.size()) {
                lines.remove(lineNumber - 1);
            } else {
                System.out.println("Line number out of range!");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String lineToWrite : lines) {
                    writer.write(lineToWrite);
                    writer.newLine();
                }
            }
            System.out.println("Line number "+lineNumber+" deleted successfully.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void updateLine(int lineNumber, String dataToAppend) {
            
        this.deleteLine(lineNumber);
        this.appendLine(lineNumber, dataToAppend);
    }
    
    public boolean search(String index)
    {
        try {
            File fl = new File(filePath);
            try (BufferedReader reader = new BufferedReader(new FileReader(fl))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if(line.contains(index))
                        return true;
                }
            }
        } catch (IOException e) {
        }
        return false;
}
    public String getPath()
    {
        return this.filePath;
    }

}