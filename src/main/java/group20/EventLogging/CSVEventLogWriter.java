package group20.EventLogging;

import java.io.PrintWriter;
import java.io.File;
import java.util.List;

public class CSVEventLogWriter {
    private String filePath;

    public CSVEventLogWriter(String filePath){
        this.filePath = filePath;
    }
private String safeString(String value) {
    return value == null ? "" : value;
}
    public void write(List<EventLogEntry> rows){
        
        try (PrintWriter writer = new PrintWriter(new File(this.filePath))) {
            writer.println("Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play");

            for(EventLogEntry row : rows) {
                writer.println(String.join(",",
            safeString(row.getCaseID()),
            safeString(row.getPlayerID()),
            safeString(row.getActivity()),
            safeString(row.getTimestamp() != null ? row.getTimestamp().toString() : ""),
            safeString(row.getCategory()),
            row.getQuestionValue() == 0 ? "" : String.valueOf(row.getQuestionValue()),
            safeString(row.getAnswerGiven()),
            safeString(row.getResult()),
            String.valueOf(row.getScoreAfterPlay())
        ));
            }
        } catch (Exception e){
            System.out.println("Error writing log: " + e.getMessage());
        }
    }
}
