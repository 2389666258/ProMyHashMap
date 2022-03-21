package CHC5223;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author I_Cov_Etous
 */

public class CHC5223 {
    static Scanner kb;  // Scan

    static IMemberDB db = new MemberHash();

    static String fileName = "D:\\jetbrains\\Pro\\JavaDemo\\src\\CHC5223\\sampleMembersUS.csv";

    private static String readNonEmpty() {
        // read non-null, non-empty string;
        String s = kb.nextLine().trim();
        while (s.equals("")) {
            System.out.print("must not be blank -- try again: ");
            s = kb.nextLine().trim();
        }
        assert !s.trim().equals("");
        return s;
    }

    public static void main(String[] args) {
        String load, option;
        String name, affiliation;
        Member resp;
        kb = new Scanner(System.in);
        System.out.print("Load file? Y/N ");
        load = readNonEmpty();
        if (load.charAt(0) == 'Y' || load.charAt(0) == 'y')
            loadFile();
        System.out.print("D)isplay  P)ut  G)et  C)ontains  S)ize  R)emove  Q)uit? ");
        option = readNonEmpty();
        while (option.charAt(0) != 'Q' && option.charAt(0) != 'q') {
            switch (option.charAt(0)) {
                case 'D', 'd' ->  // display
                        db.displayDB();
                case 'P', 'p' -> {  // put
                    System.out.print("Name? ");
                    name = readNonEmpty();
                    System.out.print("Affiliation? ");
                    affiliation = readNonEmpty();
                    Member member = new Member(name, affiliation);
                    resp = db.put(member);
                    System.out.print(name);
                    if (resp == null) System.out.println(" : new member added");
                    else System.out.println(" : member overridden");
                }
                case 'S', 's' ->  // size   success!
                        System.out.println("Size " + db.size());
                case 'G', 'g' -> {  // get   success!
                    System.out.print("Name? ");
                    name = readNonEmpty();
                    resp = db.get(name);
                    if (resp != null) System.out.println("affiliation: " + resp.getAffiliation());
                    else System.out.println(name + " not found");
                }
                case 'C', 'c' -> {  // contains   success!
                    System.out.print("Name? ");
                    name = readNonEmpty();
                    System.out.print(name);
                    if (db.containsName(name)) System.out.println(" found");
                    else System.out.println(" not found");
                }
                case 'R', 'r' -> {  // remove
                    System.out.print("Name? ");
                    name = readNonEmpty();
                    resp = db.remove(name);
                    System.out.print(name);
                    if (resp != null) System.out.println(" : deleted");
                    else System.out.println(" not found");
                }
                default -> // ?
                        System.out.println("unknown option");

            } // switch
            System.out.print("D)isplay  P)ut  G)et  C)ontains  S)ize  R)emove  Q)uit? ");
            option = readNonEmpty();
        } // while
    }

    private static void loadFile() {
        String cvsSplitBy = ",";
        Scanner file;
        Member member;
        try {
            FileInputStream streamIn = new FileInputStream(fileName);
            file = new Scanner(streamIn);
            while (file.hasNextLine()) {
                String line = file.nextLine();
                String[] parts = line.split(cvsSplitBy);
                String surname = parts[0].trim();
                String firstNames = parts[1].trim();
                String affiliation = parts[2].trim();
                member = new Member(surname + ", " + firstNames, affiliation);
                db.put(member);
            }
            System.out.println("Loading completed!");
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Can't find file " + fileName);
        }
    }
}
