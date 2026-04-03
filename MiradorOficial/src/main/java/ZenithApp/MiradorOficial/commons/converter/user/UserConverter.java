package ZenithApp.MiradorOficial.commons.converter.user;

import ZenithApp.MiradorOficial.commons.constans.response.GeneralResponse;
import ZenithApp.MiradorOficial.commons.domains.dto.user.UserDTO;
import ZenithApp.MiradorOficial.commons.domains.entity.user.UserEntity;
import ZenithApp.MiradorOficial.commons.helpers.HelperMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Log4j2
public class UserConverter {
    //Función que convierte a una entidad "Usuario" a un DTO
    public UserDTO convertUserEntityToUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        try {
            userDTO = HelperMapper.modelMapper().map(userEntity, UserDTO.class);
            byte[] cadenaDecodificadaByte = Base64.getDecoder().decode(userEntity.getPassword());
            String cadenaDecodificada = new String(cadenaDecodificadaByte);
            userDTO.setPassword(cadenaDecodificada);
        } catch (Exception e) {
            log.error(GeneralResponse.DOCUMENT_FAIL + e);
        }
        return userDTO;
    }

    //Función que convierte a un DTO "Usuario" a una entidad
    public UserEntity convertUserDTOToUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        try {
            userEntity = HelperMapper.modelMapper().map(userDTO, UserEntity.class);
            userEntity.setPassword(
                    Base64.getEncoder().encodeToString(userDTO.getPassword().getBytes()));
        } catch (Exception e) {
            log.error(GeneralResponse.DOCUMENT_FAIL + e);
        }
        return userEntity;
    }
}
