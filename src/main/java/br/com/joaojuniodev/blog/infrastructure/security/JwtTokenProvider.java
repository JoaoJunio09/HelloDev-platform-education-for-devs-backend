package br.com.joaojuniodev.blog.infrastructure.security;

import br.com.joaojuniodev.blog.data.dto.security.TokenDTO;
import br.com.joaojuniodev.blog.exceptions.InvalidJwtAuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key:57cr37}")
    private String secretKey = "57cr37";

    @Value("${security.jwt.expire-length:3600000}")
    private long validationInMilliseconds = 3600000;

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct
    void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDTO createAccessToken(String username, List<String> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validationInMilliseconds);
        String accessToken = getAccessToken(username, now, validity, roles);
        String refreshToken = getRefreshToken(username, now, roles);
        return new TokenDTO(username, true, now, validity, accessToken, refreshToken);
    }

    public TokenDTO refreshToken(String refreshToken) {
        var token = "";
        if (containsBearerToken(refreshToken)) {
            token = refreshToken.substring("Bearer ".length());
        }

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
        return createAccessToken(username, roles);
    }

    private String getRefreshToken(String username, Date now, List<String> roles) {
        Date refreshTokenValidity = new Date(now.getTime() + (validationInMilliseconds * 3));
        return JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(refreshTokenValidity)
                .withSubject(username)
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    private String getAccessToken(String username, Date now, Date validity, List<String> roles) {
        String isseurUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create()
                .withIssuer(isseurUrl)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(username)
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(alg).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (containsBearerToken(bearerToken)) return bearerToken.substring("Bearer ".length());
        return null;
    }

    private boolean containsBearerToken(String bearerToken) {
        return StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ");
    }

    public Boolean validateToken(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        try {
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Expired or Invalid JWT Token!");
        }
    }
}