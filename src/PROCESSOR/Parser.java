package PROCESSOR;


import java.io.*;
import java.util.Objects;


public class Parser {
    public int FileLength;

    String inputFile;
    String [][] clearCode;
    public Parser(String inputFile) throws IOException {
        this.inputFile = inputFile;
    }

    public String[][]  parseAsCode() throws IOException {

        File ListCode = new File(inputFile);
        String lineOfCode;

        if (ListCode.exists()) {
            BufferedReader in = new BufferedReader(new FileReader(inputFile));
            this.FileLength = (int)(ListCode.length()/5);
            clearCode = new String[FileLength][];
            String massOfCode[] = new String[FileLength];
            System.out.println();
            int i = 0;
            try {
                while ((lineOfCode = in.readLine()) != null) {
                    massOfCode[i] = lineOfCode;
                    i++;
                }
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Первый этап парсинга выполнен.");

            }
            int k=0;
            for (String aMassOfCode : massOfCode) {
                if (aMassOfCode != null) {
                    String [] temp = checkListingAndReturn(formatCode(aMassOfCode));
                    this.clearCode [k] = temp;
                    k++;
                }
            }
        } else {
            System.out.println("Working Directory = " +
                    System.getProperty("user.dir"));
        }
        return clearCode;
    }
    public static String[] formatCode(String lineOfCode){
        String commandName = lineOfCode.replaceAll("[^a-zA-Z]","");
        boolean JCis = commandName.contains("JC");
        if(JCis){
            commandName = "JC";
        }
        String label = lineOfCode.replaceAll(":","");
        String valuesRaw = lineOfCode.replaceAll("[^0-9,-]","");
        //todo JC not working now
        boolean findComma = valuesRaw.contains(",");
        boolean labelFound = false;
        if(!findComma && !Objects.equals(commandName, "JMP") && !Objects.equals(commandName, "JNZ") && !(Objects.equals(commandName,"JC"))) {
            for (int i = 1; i < 6; i++) {
                String check = "m" + Integer.toString(i);
                labelFound = label.contains(check);
                if (labelFound) {
                    break;
                }
            }
        }
        String [] labelIs = new String[1];
        String [] commandInf;
        if (findComma && !labelFound) {
            commandInf = new String [3];
            commandInf[0] = commandName;
            String cristalValue[] = valuesRaw.split(",");
            commandInf[1] = cristalValue[0];
            commandInf[2] = cristalValue [1];
        } else if(!labelFound){
            commandInf = new String [2];
            commandInf[0] = commandName;
            if(Objects.equals(commandName,"JC")) {
                commandInf[1] = "m" + valuesRaw;
            } else{
                commandInf[1] = valuesRaw;
            }
        } else{
            labelIs[0] = label;
            return labelIs;
        }


        return commandInf;
    }
    public static String[] checkListingAndReturn(String[] formattedCode){
        switch (formattedCode[0]){
            case "MOV":
                break;
            case "MOVR":
                break;
            case "OR":
                break;
            case "JMP":
                break;
            case "HALT":
                break;
            case "JC":
                break;
            case "JNZ":
                break;
            case "ORG":
                break;
            case "m1":
                break;
            case "m2":
                break;
            case "m3":
                break;
            case "m4":
                break;
            case "m5":
                break;
                default:
                    Errors e = new Errors(-2);
                    System.out.println(e.getMessage() + "\t"+formattedCode[0]);
                    ALU.HALT();
        }
        return formattedCode;
    }
}
