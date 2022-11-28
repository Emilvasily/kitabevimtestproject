package az.kitabevim.csvReader;

import com.opencsv.CSVReader;

import java.io.FileReader;

public class CSVProcessing {

    public String getSearchValue() {
        try {
            String filePath = "src/main/resources/keyword.csv";
            CSVReader reader = new CSVReader(new FileReader(filePath));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                for (String keyword : nextLine) {
                    return keyword;
                }
                System.out.print("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
