package group20.FileParsing;
import java.io.File;


public abstract class AbstractQuestionParser {
    protected File file;

    public final void run() {}

    protected abstract void determineFileType();

    protected void openFile() {}
    
    protected abstract void readFile();

    protected void parseFile() {}

    protected void closeFile() {}
}