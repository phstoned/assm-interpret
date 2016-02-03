package PROCESSOR;

/**
 * Created by phil on 02.10.2015.
 */
public class RON extends PROC {
    public int mask;
    public String name;
    public byte index;
    public byte addr;
    public int value;

    public RON(int mask){
        this.mask = mask;
        this.value = 0;
        //check for errors
        switch (mask){
            case 0b00 :
                this.mask = 0b00;
                this.name = "RON0";
                this.index = 0;
                break;
            case 0b01:
                this.mask = 0b01;
                this.name = "RON1";
                this.index = 1;
                break;
            case 0b10:
                this.mask = 0b10;
                this.name = "RON2";
                this.index = 2;
                break;
            case 0b11:
                this.mask = 0b11;
                this.name = "RON3";
                this.index = 3;
                break;
            default:
                new Errors(-1);
                break;
        }
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
