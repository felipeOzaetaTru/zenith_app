package ZenithApp.MiradorOficial.service.user;

import ZenithApp.MiradorOficial.commons.domains.dto.user.UserDTO;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<GenericResponseDTO> createUser(UserDTO userDTO);
    ResponseEntity<GenericResponseDTO> readUser(Integer userId);
    ResponseEntity<GenericResponseDTO> readUsers();
    ResponseEntity<GenericResponseDTO> updateUser(UserDTO userDTO);
    ResponseEntity<GenericResponseDTO> deleteUser(Integer userId);

}
