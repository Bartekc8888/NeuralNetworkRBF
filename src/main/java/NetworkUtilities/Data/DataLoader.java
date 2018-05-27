package NetworkUtilities.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public DataLoader() {
    }

    public List<DataContainer> loadData(File file, DataInterpreter interpreter) {
        List<DataContainer> loadedData = new ArrayList<DataContainer>();
        
        try (FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);) {

            String lineWithData;
            while ((lineWithData = bufferedReader.readLine()) != null) {
                loadedData.add(interpreter.interpretString(lineWithData));
            }

            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return loadedData;
    }
}
