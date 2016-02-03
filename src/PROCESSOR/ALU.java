package PROCESSOR;

/**
 * Created by phil on 02.10.2015.
 */
public class ALU extends PROC {
    /**
     * Assembler simulator functions
     */
    public static byte haltMask = 0b1111;
    public static byte MOVRMask = 0b0001;
    public static byte MOVMask = 0b0100;
    public static byte JMPMask = 0b0011;
    public static byte JCMask = 0b0100;
    public static byte JNZMask = 0b1010;
    public static byte ORMask = 0b01011;

    public static String HALT(){
        String complete = "";
        complete  =  assInfo.HaltInfo();
        System.out.println(complete);
        System.exit(0);
        return complete;
    }
    public static String MOVR(int ronMask, int RAMval){

        RON temp = new RON(1);
        switch (ronMask){
            case 0:
                temp = RON0;
                break;
            case 1:
                temp = RON1;
                break;
            case 2:
                temp = RON2;
                break;
            case 3:
                temp = RON3;
                break;
            default:
                Errors err = new Errors(-4);
                System.out.println(err.getMessage());
                HALT();
        }
        PROC.RAM1.setMemory(RAMval);
        int ramValue = PROC.RAM1.getPrevMemory();
        String complete;
        complete = "\t MOVR\n" +
                "\n";
        complete = complete + "перемещаем из ОП в РОН\n";
        if((temp.getValue()<0) || (ramValue<0)){
            SF.value = 1;
        }
        if((temp.getValue()==0)||(ramValue==0)){
            ZF.value = 1;
        }
        String ronStr = Integer.toString(temp.value);
        String ramStr = Integer.toString(ramValue);
        int ronStrLen = ronStr.length();
        int ramStrLen = ramStr.length();
        String addToRon = "";
        if(ronStrLen>1) {
            OF.value = 1;
                addToRon = ronStr.substring(0, ronStrLen - 1);
        }
        String addToRam = "";
        if(ramStrLen>1){
            OF.value = 1;
            addToRam = ramStr.substring(1,ramStrLen);
        }
        String ronCon = ronStr.substring(0,1) + addToRon;
        String ramCon = ramStr.substring(0,1) + addToRam;
        temp.value = Integer.parseInt(ramCon);
        complete = complete + " мы взяли значение памяти " + ramValue + "\n" + " и положили его в  " + temp.name;
        return complete;
    }



    public static String MOV(int ronMask, int ronMask2){
        RON temp = new RON(1);
        RON temp1 = new RON(1);
        switch (ronMask){
            case 0:
                temp = RON0;
                break;
            case 1:
                temp = RON1;
                break;
            case 2:
                temp = RON2;
                break;
            case 3:
                temp = RON3;
                break;
            default:
                Errors err = new Errors(-4);
                System.out.println(err.getMessage());
                HALT();
        }
        switch (ronMask2){
            case 0:
                temp1 = RON0;
                break;
            case 1:
                temp1 = RON1;
                break;
            case 2:
                temp1 = RON2;
                break;
            case 3:
                temp1 = RON3;
                break;
            default:
                Errors err = new Errors(-4);
                System.out.println(err.getMessage());
                HALT();
        }
        String complete="\t MOV\n" +
                "\n";
        complete = complete + " перемещаем из РОНА в РОН\n";
        if((temp.value==0)||(temp1.value==0)){
            ZF.value = 1;
        }
        if((temp.value<0) || (temp1.value<0)){
            SF.value = 1;
        }
        String ron1Str = Integer.toString(temp.value);
        String ron2Str = Integer.toString(temp1.value);
        int ronStrLen = ron1Str.length();
        int ron2StrLen = ron2Str.length();
        String addToRon = "";
        if(ronStrLen>1) {
            OF.value = 1;
            addToRon = ron1Str.substring(1, ronStrLen );
            complete = complete + "Значение взятое из флага: " + addToRon + "\n";
        }
        String addToRon2 = "";
        if(ron2StrLen>1){
            OF.value = 1;
            addToRon2 = ron2Str.substring(1,ron2StrLen);
            complete = complete + "Значение взятое из флага: " + addToRon2 + "\n";
        }



        String ronCon = ron1Str.substring(0,1) + addToRon;
        String ron2Con = ron2Str.substring(0,1) + addToRon2;
        temp.value = Integer.parseInt(ron2Con);
        temp1.value = Integer.parseInt(ronCon);
        complete = complete + "Переместили значение из " + temp.name + " в " + temp1.name;
        return complete;

    }
    public static String JMP(int index){
        int value = PROC.RAM1.getMemory(index);
        String complete="\t JMP\n" +
                "\n";
        complete = complete + " обращаемся к ячейке оперативной памяти" + "\n";
        if(value<0){
            SF.value = 1;
        }
        if((value == 0)){
            ZF.value = 1;
        }
        if(value > 9){
            OF.value = 1;
        }

        return complete;
    }
    public static String JC(String label){
        CF.value = 1;
        String complete="\t JC\n" +
                "\n";
        complete = complete + " переход по метке\n";

        return complete;
    }
    public static String OR(int ronMask, int ronMask2){
        RON temp = new RON(1);
        RON temp1 = new RON(1);
        switch (ronMask){
            case 0:
                temp = RON0;
                break;
            case 1:
                temp = RON1;
                break;
            case 2:
                temp = RON2;
                break;
            case 3:
                temp = RON3;
                break;
            default:
                Errors err = new Errors(-4);
                System.out.println(err.getMessage());
                HALT();
        }
        switch (ronMask2){
            case 0:
                temp1 = RON0;
                break;
            case 1:
                temp1 = RON1;
                break;
            case 2:
                temp1 = RON2;
                break;
            case 3:
                temp1 = RON3;
                break;
            default:
                Errors err = new Errors(-4);
                System.out.println(err.getMessage());
                HALT();
        }

        int first = temp.value;
        int second = temp1.value;
        String complete="\t OR \n\n";
        complete = complete + "побитовое сравнение значений двух РОН\n";
        if((temp.value == 0)||(temp1.value == 0)){
            ZF.value = 1;

        }
        if((temp.value<0)||(temp1.value<0)){
            SF.value = 1;
        }
        String ron1Str = Integer.toString(temp.value);
        String ron2Str = Integer.toString(temp1.value);
        int ronStrLen = ron1Str.length();
        int ron2StrLen = ron2Str.length();
        String addToRon = "";
        if(ronStrLen>1) {
            OF.value = 1;
            addToRon = ron1Str.substring(0, ronStrLen - 1);
            complete = complete + "Значение взятое из флага: " + addToRon + "\n";
        }
        String addToRon2 = "";
        if(ron2StrLen>1){
            OF.value = 1;
            addToRon2 = ron2Str.substring(0,ron2StrLen - 1);
            complete = complete + "Значение взятое из флага: " + addToRon2 + "\n";
        }

        String ronCon = ron1Str.substring(0,1) + addToRon;
        String ron2Con = ron2Str.substring(0,1) + addToRon2;

        complete = complete + "Результат побитового сравнения и его анализ" + "\n";
        int combined = (first) | (second);
        String combinedStr = Integer.toString(combined);
        int combinedLength = combinedStr.length();
        if(combined == 0){
            ZF.value = 1;
        }
        String addToComb = "";
        if(combinedLength>1){
            OF.value = 1;
            addToComb = combinedStr.substring(0,combinedLength - 1);
            complete = complete + "Значение взятое из флага: " + addToComb + "\n";
        }
        temp.value = combined;
        complete = complete + Integer.toBinaryString(combined);
        return complete;
    }
}