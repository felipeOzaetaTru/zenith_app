package ZenithApp.MiradorOficial.commons.helpers;

import org.modelmapper.ModelMapper;

public class HelperMapper {
    public static ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        return mapper; // ← retorna el que tiene la configuración
    }
}
