package group20.EventLogging;

import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
/**Creates and writes to the process mining log to a CSV file */
public class CSVEventLogWriter {
    private String filePath;

    public CSVEventLogWriter(String filePath){
        this.filePath = filePath;
    }

    /**Returns blank space for null values to avoid writing "null" into the CSV */
    private String safeString(String value) {
        return value == null ? "" : value;
    }

    /**@param rows The list of {@link EventLogEntry}'s to write into the CSV
     * Writes the process mining data into a CSV file.
     */
    public void write(List<EventLogEntry> rows) throws FileNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        try (PrintWriter writer = new PrintWriter(new File(this.filePath))) {
            writer.println("Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play");

            for(EventLogEntry row : rows) {
                ZonedDateTime zdt = row.getTimestamp().atZone(ZoneId.systemDefault());
                String formattedTime = zdt.format(formatter);

                writer.println(String.join(",",
                safeString(row.getCaseID()),
                safeString(row.getPlayerID()),
                safeString(row.getActivity()),
                safeString(formattedTime != null ? formattedTime : ""),
                safeString(row.getCategory()),
                row.getQuestionValue() == 0 ? "" : String.valueOf(row.getQuestionValue()),
                safeString(row.getAnswerGiven()),
                safeString(row.getResult()),
                safeString(row.getScoreAfterPlay())
                ));
            }
        }
    }
}
