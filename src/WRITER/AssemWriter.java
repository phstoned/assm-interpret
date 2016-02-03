package WRITER;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by PHIL on 02.12.2015.
 */
public class AssemWriter {
    private File out;
    private FileWriter fw;
    public AssemWriter() throws IOException {
        out =  new File("out.bin");
        fw = new FileWriter(out);
    }
    public void Write(String what) throws IOException {
        fw.append(what);
    }
    public void Write(String what, boolean endOfCommand) throws IOException {
        fw.append(what);
        if(endOfCommand) fw.append("\n");
    }
    public String getHash(String item) throws IOException {
        int temp = item.hashCode();
        String toBinaryHash = Integer.toBinaryString(temp);
        return toBinaryHash;
    }
    public void finalizeOut() throws IOException {
        fw.flush();
    }
}
