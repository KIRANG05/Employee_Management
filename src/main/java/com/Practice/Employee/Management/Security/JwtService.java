		package com.Practice.Employee.Management.Security;
		
		import java.security.Key;
		import java.util.Base64;
		import java.util.Date;
		
		import org.springframework.beans.factory.annotation.Value;
		import org.springframework.stereotype.Service;
		
		import io.jsonwebtoken.Jwts;
		import io.jsonwebtoken.SignatureAlgorithm;
		import io.jsonwebtoken.security.Keys;
		import jakarta.annotation.PostConstruct;
		
		@Service
		public class JwtService {
			
	//		private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
			
			@Value("${jwt.secret}")
			private String secret;
			
			@Value("${jwt.expiration}")
			private Long expiration;
			
			@Value("${jwt.refreshTokenExpiration}")
			private Long refreshExpiration;
			
			private Key secretKey;
			
			@PostConstruct
			public void init() {
					byte [] decodedkey = Base64.getDecoder().decode(secret);
					this.secretKey = Keys.hmacShaKeyFor(decodedkey);
			}
			
			
		
			public String generateToken(String username) {
				return Jwts.builder()
						.setSubject(username)
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + expiration))
						.signWith(secretKey, SignatureAlgorithm.HS256)
						.compact();
				
			}

			
			 public String generateRefreshToken(String username) {
			        return Jwts.builder()
			                .setSubject(username)
			                .setIssuedAt(new Date())
			                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
			                .signWith(secretKey, SignatureAlgorithm.HS256)
			                .compact();
			    }


			public String extractUserName(String token) {
				
				return Jwts.parserBuilder()
						.setSigningKey(secretKey)
						.build()
						.parseClaimsJws(token)
						.getBody()
						.getSubject();
			}



			public boolean isTokenValid(String token, String expectedUsername) {
				String actualUsername = extractUserName(token);
				Date expirationDate =  Jwts.parserBuilder()
						.setSigningKey(secretKey)
						.build()
						.parseClaimsJws(token)
						.getBody()
						.getExpiration();
				return (actualUsername.equals(expectedUsername) && !expirationDate.before(new Date()));
			}
			
			
			
		
		}
