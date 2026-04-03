package ZenithApp.MiradorOficial.webApi.auth;

import ZenithApp.MiradorOficial.commons.constans.endPoints.auth.AuthEndPoint;
import ZenithApp.MiradorOficial.commons.constans.response.GeneralResponse;
import ZenithApp.MiradorOficial.commons.constans.response.user.IUserResponse;
import ZenithApp.MiradorOficial.commons.domains.dto.auth.AuthResponse;
import ZenithApp.MiradorOficial.commons.domains.dto.auth.LoginRequest;

import ZenithApp.MiradorOficial.commons.domains.dto.auth.LoginResponse;
import ZenithApp.MiradorOficial.commons.domains.entity.user.UserEntity;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import ZenithApp.MiradorOficial.repository.user.IUserRepository;
import ZenithApp.MiradorOficial.seguridad.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(AuthEndPoint.AUTH_BASE_URL)
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final IUserRepository userRepository;

    @PostMapping(AuthEndPoint.AUTH_LOGIN)
    public ResponseEntity<GenericResponseDTO> login(@RequestBody LoginRequest request) {
        try {
            // Try-catch interno solo para la autenticación
            try {
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(), request.getPassword())
                );
            } catch (BadCredentialsException e) {
                log.error("Credenciales inválidas: " + e);
                return new ResponseEntity<>(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(IUserResponse.USER_FAIL)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build(), HttpStatus.BAD_REQUEST);
            }

            // Si la autenticación fue exitosa, continúa el flujo normal
            Optional<UserEntity> user = userRepository.findUserByEmail(request.getEmail());
            if (user.isPresent()) {
                String token = jwtUtil.generateToken(request.getEmail());

                return new ResponseEntity<>(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(new LoginResponse(token, request.getEmail()))// Retorna el token, no el password
                        .statusCode(HttpStatus.OK.value())
                        .build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(IUserResponse.USER_UPDATE_FAIL)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build(), HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            log.error(GeneralResponse.INTERNAL_SERVER + e);
            return new ResponseEntity<>(GenericResponseDTO.builder()
                    .message(GeneralResponse.INTERNAL_SERVER)
                    .objectResponse(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(AuthEndPoint.ME)
    public ResponseEntity<GenericResponseDTO> me(Authentication auth) {
        try {
            UserEntity user = userRepository.findUserByEmail(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            System.out.println(user);
            return new ResponseEntity<>(GenericResponseDTO.builder()
                    .message(GeneralResponse.OPERATION_SUCCESS)
                    .objectResponse(AuthResponse.builder().name(user.getName()).email(user.getEmail()).build())
                    .statusCode(HttpStatus.OK.value())
                    .build(), HttpStatus.OK);

        } catch (Exception e) {
            log.error(GeneralResponse.INTERNAL_SERVER + e);
            return new ResponseEntity<>(GenericResponseDTO.builder()
                    .message(GeneralResponse.INTERNAL_SERVER)
                    .objectResponse(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
