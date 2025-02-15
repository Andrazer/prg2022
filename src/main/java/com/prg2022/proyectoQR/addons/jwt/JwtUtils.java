package com.prg2022.proyectoQR.addons.jwt;

import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import com.prg2022.proyectoQR.services.UserDetailsImpl;
import io.jsonwebtoken.*;


@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
  @Value("${eeae.app.jwtSecret}")
  private String jwtSecret;
  @Value("${eeae.app.jwtExpirationMs}")
  private int jwtExpirationMs;
  @Value("${eeae.app.jwtCookieName}")
  private String jwtCookie;
  public String getJwtFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return null;
    }
  }
  public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
    String jwt = generateTokenFromUsername(userPrincipal.getUsername());
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/").maxAge(24 * 60 * 60).httpOnly(true).build();
    return cookie;
  }
  public ResponseCookie getCleanJwtCookie() {
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/").build();
    return cookie;
  }
  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      //logger.error("JWT: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      //logger.error("JWT: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      //logger.error("JWT: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      //logger.error("JWT: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      //logger.error("JWT: {}", e.getMessage());
    }
    return false;
  }
  
  public String generateTokenFromUsername(String username) {   
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }
}