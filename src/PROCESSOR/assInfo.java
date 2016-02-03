package PROCESSOR;

/**
 * Created by phil on 02.10.2015.
 */
public class assInfo {


    public static void ronInitializerInfo(RON ronInf) {
        int anotherBit;
        if ((ronInf.mask == 0) || (ronInf.mask == 1)) {
            anotherBit = 0;
            System.out.println("Регистр общего назначения №" + ronInf.index + "\n" + "Имя: " + ronInf.name + "\n" + "Маска " +
                            +anotherBit + Integer.toBinaryString(ronInf.mask) + "\n" + "Значение памяти равно: " + ronInf.value + "\n" + "Был инициализирован."
                            + "\n" + "--------------------------------------"
            );
        } else {
            System.out.println("Регистр общего назначения №" + ronInf.index + "\n" + "Имя: " + ronInf.name + "\n" + "Маска " +
                            Integer.toBinaryString(ronInf.mask) + "\n" + "Значение памяти равно: " + ronInf.value + "\n" + "Был инициализирован."
                            + "\n" + "--------------------------------------"
            );
        }//else if
    }

    public static String  MOVRInfo(RON ronInf) {
        String complete ="";
        complete = "Код операции: " + Integer.toBinaryString(ALU.MOVRMask) + "\n";
        complete = complete + "Перемещаем значение оперативной памяти " + PROC.RAM1.getPrevMemory() + "\n" + "из адреса: "
                + PROC.RAM1.getAddr() + "\n" + "В регистр общего назначения №" + ronInf.index + "\n" + "c именем " + ronInf.name
                + "\n" + "--------------------------------------";
        return complete;
    }

    public static String JMPInfo(int index) {
        String complete = "";

        complete = "Код операции: " + Integer.toBinaryString(ALU.JMPMask);
        complete = complete + "Берем значение " + PROC.RAM1.getMemory(index) + "\n" + "по адресу: " + index +
                "\n" + "из ОЗУ" +"\n--------------------------------------\"";
        return complete;
    }

    public static String HaltInfo() {
        String complete = "";
        complete = "\n" + "--------------------------------------" + "\n";
        complete = complete + "Код операции: " + Integer.toBinaryString(ALU.haltMask) + "\n";
        complete = complete + "Останова процессора.";
        return complete;
    }

    public static String XORInfo(RON ron1, RON ron2) {
        String complete = "";
        complete = "Код операции: " + Integer.toBinaryString(ALU.ORMask);
        complete = complete + "Выполняемую логическую операцию побитового сравнения с" + "\n" +
                "регистром общего назначения № " + ron1.index + "\n" +
                "с именем " + ron1.name + "\n" + "его значение " + ron1.value + "\n" +
                "сравниваем с " + "регистром общего назначения № " + ron2.index + "\n" +
                "с именем " + ron2.name + "\n" + "его значение " + ron2.value + "\n" +
                "результат выполнения в двоичном представлении: ";
        return complete;
    }

}
