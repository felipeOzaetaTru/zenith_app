package ZenithApp.MiradorOficial.seguridad;

import java.io.IOException;  // ← correcto
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        // estas rutas no pasan por el filtro JWT
        return path.equals("/auth/iniciar-sesion") ||
                path.equals("/usuario/crear-usuario");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        log.info("Header Authorization: {}", header); // ← agrega esto
        log.info("URI: {}", request.getRequestURI()); // ← y esto

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        if (jwtUtil.isTokenValid(token)) {
            String email = jwtUtil.extractEmail(token);
            log.info("Token válido para: {}", email); // ← agrega
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            log.info("UserDetails cargado: {}", userDetails.getUsername()); // ← agrega

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

            log.info("Authorities: {}", userDetails.getAuthorities()); // ← agrega
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info("Autenticacion seteada: {}", SecurityContextHolder.getContext().getAuthentication());
        } else {
            log.info("Token INVÁLIDO"); // ← agrega
        }

        chain.doFilter(request, response);
    }
}
