package PROCESSOR;

/**
 * Created by PHIL on 14.10.2015.
 */
public class flags {
    public String name;
    public int value;
    public flags(String ErrorFlP){
        switch(ErrorFlP){
            case "CF":
                this.name = "Флаг переноса";
                this.value = 0;
                break;
            case "OF":
                this.name = "Флаг переполнения";
                this.value = 0;
                break;
            case "SF":
                this.name = "Флаг знака";
                this.value = 0;
                break;
            case "ZF":
                this.name = "Флаг нуля";
                this.value = 0;
                break;
            default:
                Errors err = new Errors(-9);
                break;
        }
    }
}
