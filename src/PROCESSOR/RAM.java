package PROCESSOR;

/**
 * Created by phil on 02.10.2015.
 */
public class RAM {
    public int addr=0;
    int [] memory = new int[64];

    public RAM() {
        initMemory();
    }

    public int getMemory(int index) {
        int r=0;
        try {
            r = memory[index];
        } catch (ArrayIndexOutOfBoundsException exc){
            System.out.println(new Errors(-4));
            ALU.HALT();
        } catch (IndexOutOfBoundsException e){
            System.out.println(new Errors(-4));
            ALU.HALT();
        }
        return r;
    }
    public int getMemory(){
        return memory[addr];
    }
    public int getPrevMemory(){
        return memory[addr-1];
    }

    public void setMemory(int value) {
        this.memory[addr] = value;
        addr++;
    }
    public void initMemory(){
        for (int aMemory : memory) {
            setMemory(0);
        }
        addr=0;
    }

    public int getAddr() {
        return addr-1;
    }

    public int[] getAllMemory(){
        return memory;
    }



//todo tableview for viewing current ram, ram is fucking working, but we can't display it yet!
}
