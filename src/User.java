package pl.project;

import java.io.*;
import java.util.Scanner;



public class User  extends Course implements UserInterface{
    static int nextId = readNextId();
    private int id;
    private String name;
    private String email;
    private String password;
    static boolean log;

    public User(String name, String email, String password) {
        this.id = nextId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User() {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static int readNextId() {
    try {
        File file = new File("nextId.txt");
        if (!file.exists()) {
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter("nextId.txt"));
            writer.write("1"); // Initialize the file with "1"
            writer.close();
        }
        BufferedReader reader = new BufferedReader(new FileReader("nextId.txt"));
        int nextId = Integer.parseInt(reader.readLine());
        reader.close();
        return nextId;
    } catch (IOException e) {
        e.printStackTrace();
        return 1; // If there's an error reading the file, start IDs at 1
    }
}

    static void writeNextId(int nextId) {
    try {
        FileHandling fileHandler = new FileHandling("nextId.txt");
        fileHandler.writeFile(Integer.toString(nextId));
    } catch (Exception e) {
        e.printStackTrace();
    }
}

   
    public void setName(String name) {
        this.name = name;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

// Login method
    @Override
    public String login(String email, String password) {
    Scanner scanner  = new Scanner(System.in);
    Scanner input = new Scanner(System.in);
    while (true) {
        try {
            FileHandling studentFileHandler = new FileHandling("student.txt");
            FileHandling adminFileHandler = new FileHandling("admins.txt");
            FileHandling teacherFileHandler = new FileHandling("instructor.txt"); // New file for teachers

            if (!studentFileHandler.checkFileExistence() && !adminFileHandler.checkFileExistence() && !teacherFileHandler.checkFileExistence()) {
                System.out.println("No users, admins, or instructor registered yet.");
                return "false";
            }

            for (int i = 1; i <= studentFileHandler.getSize(); i++) {
                String line = studentFileHandler.readLine(i);
                 if (line != null) {
                String[] userData = line.split(",");
                if (userData[2].equals(email) && userData[3].equals(password)) {
                    System.out.println("Hello, " + userData[1] + "!");
                    this.id = Integer.parseInt(userData[0]);
                    log = true;
                    return "Student";
                }
            }
            }

            for (int i = 1; i <= adminFileHandler.getSize(); i++) {
                String line = adminFileHandler.readLine(i);
                 if (line != null) {
                String[] adminData = line.split(",");
                if (adminData[2].equals(email) && adminData[3].equals(password)) {
                    System.out.println("--------------");
                    System.out.println("| Admin mode |");
                    System.out.println("--------------");
                    System.out.println("Hello " + adminData[1] + "!");
                    log = true;
                    return "Admin";
                }
            }
            }

            for (int i = 1; i <= teacherFileHandler.getSize(); i++) { // New section for teachers
                String line = teacherFileHandler.readLine(i);
                 if (line != null) {
                String[] teacherData = line.split(",");
                if (teacherData[2].equals(email) && teacherData[3].equals(password)) {
                    System.out.println("-------------------");
                    System.out.println("| instructor mode |");
                    System.out.println("-------------------");
                    System.out.println("Hello " + teacherData[1] + "!");
                    log = true;
                    return "instructor";
                }
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Wrong email or password. Please enter again.");
        System.out.print("Enter your email: ");
        email = scanner.nextLine();
        System.out.print("Enter your password: ");
        password = scanner.nextLine();
    }
}



    public boolean isEmailUnique(String email) {
    try {
        File file = new File("student.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader reader = new BufferedReader(new FileReader("student.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(",");
            if (userData[2].equals(this.email)) {
                return false;
            }
        }
        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return true;
}
    //register method
    @Override
    public void register() {
        try{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name:");
        this.setName(scanner.nextLine());

        System.out.print("Enter your email:");
        this.setEmail(scanner.nextLine());

        while (!isEmailUnique(this.email)) {
            System.out.println("Email already in use.");
            System.out.print("Enter another email:");
            this.email = scanner.nextLine();
        }

        System.out.print("Enter your password:");
        this.password = scanner.nextLine();

        this.id = nextId++;
        
            FileHandling fileHandler = new FileHandling("student.txt");
            String content = this.id + "," + this.name + "," + this.email + "," + this.password;
            fileHandler.appendFile(content);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        writeNextId(nextId);
        System.out.println("Registration successful");
        System.out.println("You can Login now ");
        menu newchoes = new menu();
        newchoes.LogAndReg();
        }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if(!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new Exception("invalid email");
        }
        this.email = email;
    }

    @Override
    public void logout(){
        log = false;
        loginAfterLogout();
    }
    
    public void loginAfterLogout(){
        menu Menu = new menu();
        Menu.LogAndReg();
    }
            
        
    }

