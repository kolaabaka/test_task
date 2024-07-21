package com.banturov.jwt;

import java.util.Base64;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtConverter {

	public JwtConverter() {
	}

	public String jwtMaker(String subject) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + 10000);
		String secretKey = "mySecretKeymySecretKeymySecretKey";

		String token = Jwts.builder().setSubject(subject).setIssuedAt(now).setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS256, secretKey.getBytes()).compact();
		return token;
	}

	public String jwtDecoderLogin(String jwt) {
		String[] strIn = jwt.split("\\.");
		String codeString = strIn[1];
		String decodeString = new String(Base64.getUrlDecoder().decode(codeString));
		JSONObject json = new JSONObject(decodeString);
		String userLogin = (String) json.get("sub");
		return userLogin;
	}
}
