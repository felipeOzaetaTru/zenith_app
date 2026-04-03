package ZenithApp.MiradorOficial.webApi.user;

import ZenithApp.MiradorOficial.commons.domains.dto.user.UserDTO;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IUserApi {
    @PostMapping()
    ResponseEntity<GenericResponseDTO> createUser(@RequestBody UserDTO userDTO);
    @GetMapping()
    ResponseEntity<GenericResponseDTO> readUser(@PathVariable Integer userId);

    @GetMapping()
    ResponseEntity<GenericResponseDTO> readUsers();
    @PutMapping()
    ResponseEntity<GenericResponseDTO> updateUser(@RequestBody UserDTO userDTO);
    @DeleteMapping()
    ResponseEntity<GenericResponseDTO> deleteUser(@PathVariable Integer userId);

}
