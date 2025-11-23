package group20.FileParsing;

public abstract class AbstractQuestionParser {
    protected File file;

    public final run() {}

    protected abstract determineFileType();

    protected boolean openFile() {}
    
    protected abstract readFile();

    protected parseFile() {}

    protected closeFile() {}
}