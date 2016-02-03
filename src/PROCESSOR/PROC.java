package PROCESSOR;


import java.io.IOException;
import java.util.Random;


public class PROC {


    private static int ron0addr = 0b00;
    private static int ron1addr = 0b01;
    private static int ron2addr = 0b10;
    private static int ron3addr = 0b11;
    private static int RAMaddr = 0b00;
    public static RON RON0 = new RON(ron0addr);
    public static RON RON1 = new RON(ron1addr);
    public static RON RON2 = new RON(ron2addr);
    public static RON RON3 = new RON(ron3addr);
    public static RAM RAM1 = new RAM();
    public static flags OF = new flags("OF");
    public static flags CF = new flags("CF");
    public static flags SF = new flags("SF");
    public static flags ZF = new flags("ZF");

    public static void main(String[] args) throws IOException {
//        //инициализация
//        initializeRON();
//        //перемещение значений регистра
//        RON2.setValue(322);
//        ALU.MOV(RON0,RON2);
//        assInfo.MOVInfo(RON0, RON2);
//        //ОП и РОН
//        RON0.setValue(23);
//        RAM1.setMemory(4,421);
//        ALU.MOVR(RON0,RAM1,4);
//        assInfo.MOVRInfo(RAM1, RON0,4);
//        System.out.println();
//        System.out.println();
//        //JMP
//        ALU.JMP(RAM1,232,214);
//        assInfo.JMPInfo(RAM1,232,214);
//        //or
//        RON0.setValue(0);
//        ALU.OR(RON0, RON2);
//        //jnz
//        ALU.JNZ(RON1);
//        //Останова
//        ALU.HALT();

    }
    public static void initializeRON(){
        Random sd = new Random();
        int r = sd.nextInt(24);
        RON0.setValue(r);
        r = sd.nextInt(124);
        RON1.setValue(r);
        r = sd.nextInt(524);
        RON2.setValue(r);
        r = sd.nextInt(1024);
        RON3.setValue(r);
    }
}
