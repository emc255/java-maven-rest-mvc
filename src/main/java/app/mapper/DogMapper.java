package app.mapper;

import app.entity.Dog;
import app.model.DogDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DogMapper {
    Dog convertCustomerDTOToDog(DogDTO dto);

    DogDTO convertDogToDogDTO(Dog dog);
}
