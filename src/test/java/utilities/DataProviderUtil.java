package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

public class DataProviderUtil {

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        List<String[]> rows = new ArrayList<>();
        System.out.println(">>> DataProvider getLoginData() was called");

        try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/testdata/loginData.csv"))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                rows.add(line.split(","));
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read test data file", e);
        }

        System.out.println(">>> Rows loaded: " + rows.size());
        return rows.toArray(new Object[0][0]);
    }
}