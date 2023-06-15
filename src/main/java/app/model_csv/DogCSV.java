package app.model_csv;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DogCSV {
    
    @CsvBindByName
    private String name;

    @CsvBindByName
    private String breed;

    @CsvBindByName
    private String sex;

    @CsvBindByName
    private String colour;
}
