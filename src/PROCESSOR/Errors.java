package PROCESSOR;

/**
 * Created by phil on 02.10.2015.
 */
public class Errors extends PROC {
    public int codeOfError;
    private String msg;
    public Errors(int codeOfError) {
        this.codeOfError = codeOfError;
        switch (codeOfError) {
            case 0:
                setMessage("Успешно выполнено");
                break;
            case -1:
                setMessage("Ошибка в значении маски.");
//                ALU.HALT();
                break;
            case -2:
                setMessage("Неверная мнемоника команды.");
//                ALU.HALT();
                break;
            case -3:
                setMessage("Неверный адрес памяти.");
                break;
            case -4:
                setMessage("Неверное обращение к РОН.");
//                ALU.HALT();
                break;
            case -5:
                setMessage("Не загружен исходный файл.");
                break;
            case -6:
                setMessage("Рон уже был инициализирован!");
                break;
            case -7:
                setMessage("Рон не был инициализирован.");
                break;
            case -8:
                setMessage("Неверно указано имя флага.");
                break;
            case -9:
                setMessage("Ошибка в листинге");
                break;
            case -10:
                setMessage("Ошибка в операндах команды");
                break;
            case 124:
                setMessage("Листинг уже был выполнен.");
                break;
            case 404:
                setMessage("Не могу записать в файл.");
                break;
            default:
                setMessage("Успешно выполнено.");
                break;
        }
    }
        public void setMessage(String errMsg){
            this.msg = errMsg;
        }
        public String getMessage(){
            return this.msg;
        }
}
