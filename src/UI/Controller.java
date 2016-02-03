package UI;

import WRITER.AssemWriter;
import javafx.event.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import PROCESSOR.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


/**
 * Created by PHIL on 21.10.2015.
 */
public class Controller {

    @FXML public MenuItem LoadListingItem;
    public File translatedCode;
    public TextField org;
    public TextField bitwiseGrid;
    public TextArea ronTerminal;
    public TextArea flagTerminal;
    public TextArea ramTerminal;
    public TextField orgAK;
    public TextField outPath;
    public ProgressIndicator progress;
    boolean labelJmpAccess= true;
    public String [][] rawListing;
    private String [] labelListing = new String[5];
    public static int orgCount;
    private int debugCounter;
    boolean handled=false;


    @FXML private TextArea Terminal;
    private boolean stateRon = false;

    public static int haltMask = 0b1111;
    public static int MOVRMask = 0b0001;
    public static int MOVMask = 0b0100;
    public static int JMPMask = 0b0011;
    public static int JCMask = 0b0100;
    public static int XORmask = 0b01011;


    static{
        PROCESSOR.PROC.initializeRON();
    }
    public void checkRONUpdates(){

        ronTerminal.appendText(dateToTerminal()+ "\t" + " Обновляем значение РОН\n");
        ronTerminal.appendText(PROC.RON0.name +  " " + String.valueOf(PROC.RON0.value)+"\n");
        ronTerminal.appendText(PROC.RON1.name +  " " +String.valueOf(PROC.RON1.value)+"\n");
        ronTerminal.appendText(PROC.RON2.name +  " " + String.valueOf(PROC.RON2.value)+"\n");
        ronTerminal.appendText(PROC.RON3.name +  " " + String.valueOf(PROC.RON3.value)+"\n");

    }
    public void terminalPrint(String message){

        Terminal.appendText(dateToTerminal() + "\t" + message + "\n");
    }




    public void clearTerminal(){
        Terminal.setText("");
    }

    public void LoadListing(ActionEvent actionEvent) throws IOException {
        //open file
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузите исходный листинг");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Assembler file", "*.asm"));
        translatedCode = fileChooser.showOpenDialog(primaryStage);

    }
    public void Translate() throws IOException {
        handleCommands(translatedCode);
        checkRONUpdates();
    }
    public void handleCommands(File selectedFile) throws IOException{
        AssemWriter aw = new AssemWriter();
        if(handled){
            Errors err = new Errors(124);
            terminalPrint(err.getMessage());return;
        }

        handled = true;
        String command = "";
        int value1=0;
        int value2=0;
        String forLabel = "";
        if(selectedFile != null) {
            Parser listing = new Parser(selectedFile.getAbsolutePath());

            rawListing = listing.parseAsCode();

            int i=0;
            int j=0;
            for(String [] arr : rawListing){
                try {
                    if (arr.length == 1) {
                        labelListing[j]=arr[0];
                        terminalPrint("Метка: " + labelListing[j]);
                        j++;
                    }
                }
                catch (NullPointerException exc){
                    return;
                }
                try {
                    for (String val : arr) {

                        switch (i) {
                            case 0:
                                command = val;
                                i++;
                                break;
                            case 1:
                                if(Objects.equals(command, "JC")&&labelJmpAccess){
                                    forLabel = val;
                                    i = 0;
                                    flushOut("JC",aw);
                                    writeCommandsAssen(forLabel,aw);
                                    JCHelper(forLabel);
                                    ramTerminalUpdate();
                                    labelJmpAccess = false;
                                    continue;
                                }
                                value1 = Integer.parseInt(val);
                                i++;
                                if(Objects.equals(command, "JMP") || Objects.equals(command, "HALT") || Objects.equals(command, "ORG")){
                                    i = 0;
                                }
                                break;
                            case 2:
                                value2 = Integer.parseInt(val);
                                i = 0;
                                break;
                        }
                    }
                    }
                catch (NullPointerException e){
                    return;
                }
                catch (NumberFormatException exc){
                    i = 0;
                }


                switch (command){
                    case "MOVR":
                        resetFlags();
                       terminalPrint(ALU.MOVR(value1, value2));
                        checkRONUpdates();
                        checkFlags();
                        ramTerminalUpdate();
                        orgCount();
                        flushOut("MOVR",aw);
                        writeCommandsAssen(value1,value2,aw);
                        break;
                    case "MOV":
                        resetFlags();
                        terminalPrint(ALU.MOV(value1, value2));
                        checkRONUpdates();
                        checkFlags();
                        ramTerminalUpdate();
                        orgCount();
                        flushOut("MOV",aw);
                        writeCommandsAssen(value1,value2,aw);
                        break;
                    case "OR":
                        resetFlags();
                        terminalPrint(ALU.OR(value1, value2));
                        checkRONUpdates();
                        checkFlags();
                        ramTerminalUpdate();
                        orgCount();
                        flushOut("OR",aw);
                        writeCommandsAssen(value1,value2,aw);
                        break;
                    case "JMP":
                        resetFlags();
                        terminalPrint(ALU.JMP(value1));
                        checkRONUpdates();
                        checkFlags();
                        ramTerminalUpdate();
                        orgCount();
                        flushOut("JMP",aw);
                        writeCommandsAssen(value1,value2,aw);
                        break;
                    case "ORG":
                        writeCommandsAssen(value1,aw);
                        orgStart(String.valueOf(value1));
                        break;
                    case "HALT":
                        resetFlags();
                        orgCount();
                        ramTerminalUpdate();
                        flushOut("HALT",aw);
                        writeCommandsAssen(value1,value2,aw);
                        terminalPrint(ALU.HALT());
                }
            }
        } else {
            terminalPrint("Исходный файл не был загружен.");
        }
    }
    public void checkFlags(){
        flagTerminal.appendText(dateToTerminal() + "\t" + " Обновляем значение флагов\n");
        flagTerminal.appendText("Флаг нуля: " + String.valueOf(PROC.ZF.value) + "\n");
        flagTerminal.appendText("Флаг переполнения: " + String.valueOf(PROC.OF.value) + "\n");
        flagTerminal.appendText("Флаг переноса: " + String.valueOf(PROC.CF.value) + "\n");
        flagTerminal.appendText("Флаг знака: " + String.valueOf(PROC.SF.value) + "\n");
    }
    public void checkProgress(){

        int counter=0;
        for(int item : PROC.RAM1.getAllMemory()){
            if(item != 0){
                counter++;
            }
        }//for
        double first = counter;
        double second = PROC.RAM1.getAllMemory().length;
        double res = (first/second);
        progress.setProgress(res);
    }

    public void doStep(ActionEvent actionEvent) throws IOException {
        try {
            handleCommandsByStep(translatedCode);
        } catch (ArrayIndexOutOfBoundsException e){
            terminalPrint("Данный листинг закончился, загрузите другой.");
            return;
        }
        catch (NullPointerException e){
            terminalPrint("Данный листинг закончился, загрузите другой.");
            return;
        }

        debugCounter++;
    }
    public void handleCommandsByStep(File selectedFile) throws IOException {

        String command = "";
        int value1=0;
        int value2=0;
        if(selectedFile != null) {

            Parser listing = new Parser(selectedFile.getAbsolutePath());
            String rawCommands[][] = listing.parseAsCode();
            int lengthOfRaw;
            String[] useOnceAndThenDestroy;

            try {
                lengthOfRaw = rawCommands[debugCounter].length;
                useOnceAndThenDestroy = new String [lengthOfRaw];
                System.arraycopy(rawCommands[debugCounter], 0, useOnceAndThenDestroy, 0, lengthOfRaw);
            }
            catch (NullPointerException e){
                terminalPrint("Данный листинг закончился, загрузите другой.");
                return;
            }


            int i=0;
            String forLabel ="";
                try {
                    for (String val : useOnceAndThenDestroy) {
                        switch (i) {
                            case 0:
                                command = val;
                                i++;
                                break;
                            case 1:
                                if(Objects.equals(command, "JC")&&labelJmpAccess){
                                    forLabel = val;
                                    i = 0;
                                    JCHelper(forLabel);
                                    orgCount();
                                    ramTerminalUpdate();
                                    labelJmpAccess = false;
                                    continue;
                                }
                                value1 = Integer.parseInt(val);
                                i++;
                                if(Objects.equals(command, "JMP") || Objects.equals(command, "HALT")){
                                    i = 0;
                                }
                                break;
                            case 2:
                                value2 = Integer.parseInt(val);
                                i = 0;
                                break;
                        }
                    }
                }
                catch (NullPointerException e){
                    terminalPrint("----------");
                }
                catch (NumberFormatException exc){
                    System.out.println("--");
                }
                switch (command){
                    case "ORG":
                        orgStart(String.valueOf(value1));

                        break;
                    case "MOVR":
                        resetFlags();
                        ramTerminalUpdate();
                        orgCount();
                        terminalPrint(ALU.MOVR(value1, value2));
                        checkRONUpdates();
                        checkFlags();
                        break;
                    case "MOV":
                        resetFlags();
                        ramTerminalUpdate();
                        orgCount();
                        terminalPrint(ALU.MOV(value1, value2));
                        checkRONUpdates();
                        checkFlags();
                        break;
                    case "OR":
                        resetFlags();
                        ramTerminalUpdate();
                        orgCount();
                        terminalPrint(ALU.OR(value1, value2));
                        checkRONUpdates();
                        checkFlags();
                        break;
                    case "JMP":
                        resetFlags();
                        ramTerminalUpdate();
                        orgCount();
                        terminalPrint(ALU.JMP(value1));
                        checkRONUpdates();
                        checkFlags();
                        break;
                    case "HALT":
                        ramTerminalUpdate();
                        orgCount();
                        resetFlags();
                        terminalPrint(ALU.HALT());
                }

        } else {
            terminalPrint("Исходный файл не был загружен.");
        }
    }
    public void JCHelper(String label){
        terminalPrint(ALU.JC(label));
        PROC.CF.value = 1;
        int i = 0;
        int doCommands = 0;
        int check = 0;
        boolean flag = false;
        String command = "";
        for(;i<rawListing.length;i++){
            for(int j=0;j<rawListing[i].length;j++){
                if(Objects.equals(rawListing[i][0], label)) {
                    doCommands = i+1;
                    check = 0;
                    flag =true;

                }
                if(flag) {
                    int value1 = 0;
                    int value2 = 0;
                    for (int l=0;l<rawListing[doCommands].length;l++) {
                                command = rawListing[doCommands][l];
                                l++;
                                value1 = Integer.parseInt(rawListing[doCommands][l]);
                                l++;
                                value2 = Integer.parseInt(rawListing[doCommands][l]);

                        }
                        switch (command) {
                            case "MOVR":
                                ramTerminalUpdate();
                                resetFlags();
                                terminalPrint(ALU.MOVR(value1, value2));
                                orgCount();
                                checkRONUpdates();
                                checkFlags();
                                return;
                            case "MOV":
                                ramTerminalUpdate();
                                resetFlags();
                                terminalPrint(ALU.MOV(value1, value2));
                                orgCount();
                                checkRONUpdates();
                                checkFlags();
                                return;
                            case "OR":
                                ramTerminalUpdate();
                                resetFlags();
                                terminalPrint(ALU.OR(value1, value2));
                                checkRONUpdates();
                                orgCount();
                                checkFlags();
                                return;
                            case "JMP":
                                ramTerminalUpdate();
                                resetFlags();
                                terminalPrint(ALU.JMP(value1));
                                checkRONUpdates();
                                checkFlags();
                                orgCount();
                                return;
                            case "HALT":
                                ramTerminalUpdate();
                                resetFlags();
                                terminalPrint(ALU.HALT());
                                orgCount();
                                return;
                        }

                    }//for val
                }//flag
            }//for
        }
    void resetFlags(){
        PROC.ZF.value = 0;
        PROC.OF.value = 0;
        PROC.SF.value = 0;
    }
    @FXML
    void resetAll(){
        ronTerminal.clear();
        flagTerminal.clear();
        ramTerminal.clear();
        Terminal.clear();
        handled = false;
        org.clear();
        orgAK.clear();
        progress.setProgress(0);
        bitwiseGrid.clear();
    }
    void flushOut(String command, AssemWriter aw) throws IOException {
        switch (command){
            case "JC":
                aw.Write(aw.getHash(String.valueOf(JCMask)) + "\t");
                aw.finalizeOut();
                break;
            case "OR":
                aw.Write(aw.getHash(String.valueOf(XORmask)) + "\t");
                aw.finalizeOut();
                break;
            case "MOV":
                aw.Write(aw.getHash(String.valueOf(MOVMask)) + "\t");
                aw.finalizeOut();
                break;
            case "MOVR":
                aw.Write( aw.getHash(String.valueOf(MOVRMask)) + "\t");
                aw.finalizeOut();
                break;
            case "HALT":
                aw.Write(aw.getHash(String.valueOf(haltMask)) + "\t");
                aw.finalizeOut();
                break;
            case "JMP":
                aw.Write(aw.getHash(String.valueOf(JMPMask)) + "\t");
                aw.finalizeOut();
                break;
            default:
                Errors err = new Errors(404);
                System.out.println(err.getMessage());
        }
    }
    void writeCommandsAssen(int value1, int value2, AssemWriter aw) throws IOException {
        aw.Write(aw.getHash(String.valueOf(value1)) + " ");
        aw.Write(aw.getHash(String.valueOf(value2)) + " ",true);
        aw.finalizeOut();
    }
    void writeCommandsAssen(int value1, AssemWriter aw) throws IOException {
        aw.Write((String.valueOf(value1)) + " ",true);
        aw.finalizeOut();
    }
    void writeCommandsAssen(String label, AssemWriter aw) throws IOException{
        aw.Write(aw.getHash(label) + " ",true);
        aw.finalizeOut();
    }
    void orgStart(String where){
        int i = Integer.parseInt(where,2);
        orgCount = i;
        String toBinary = Integer.toBinaryString(i);
        org.setText(toBinary);
    }
    void orgCount(){
        orgCount ++;
        String binary = Integer.toBinaryString(orgCount);
        orgAK.setText(binary);
    }
    String dateToTerminal(){
        Date d;
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        d = new Date();
        return dateFormat.format(d);
    }

    public void openOutFile(ActionEvent actionEvent) {
        File f = new File("out.bin");
        if(f.exists()){
            try {
                Desktop.getDesktop().open(f);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        } else{
            String msg = "Сначала транслируй программу";
            System.out.println(msg);
            terminalPrint(msg);
        }
    }

    public void openInFile(ActionEvent actionEvent) {

        try {
            Desktop.getDesktop().open(translatedCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e){
            String msg = "Не могу открыть пустой файл, сначало его нужно загрузить на главной странице";
            terminalPrint(msg);
            System.out.println(msg);
        }
    }

    public void getPath(Event event) {
        if(outPath != null) {
            File f = new File("out.bin");
            String path = f.getAbsolutePath();
            outPath.setText(path);
        }
    }

    public void orgChanged(ActionEvent actionEvent) {
        orgCount = Integer.parseInt(org.getText(),2);
        terminalPrint("Изменен ORG " + orgCount);
    }

    public void bitwiseChanged(ActionEvent actionEvent) {
        terminalPrint("Изменена длина разрядной сетки " + bitwiseGrid.getText());
    }
    public void ramTerminalUpdate(){
        ramTerminal.appendText(dateToTerminal() + "\t" + "Обновляем значения памяти" + "\n");
        int [] mem = PROC.RAM1.getAllMemory();
        int i = 0;
        for(int item : mem){
            ramTerminal.appendText("Значение памяти по адресу " + i  + " равно: " + item + "\n");
            i++;
        }
        checkProgress();
    }
}

//todo check for println once command is called



//todo preload