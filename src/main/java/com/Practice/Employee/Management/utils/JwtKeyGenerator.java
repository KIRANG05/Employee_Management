//package com.Practice.Employee.Management.utils;
//
//import java.security.Key;
//import java.util.Base64;
//
//import javax.crypto.SecretKey;
//
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//
//
//public class JwtKeyGenerator {
//
//	public static void main(String[] args) {
//		
//		Key key =Keys.secretKeyFor(SignatureAlgorithm.HS256);//normal key
//		String base64key = Base64.getEncoder().encodeToString(key.getEncoded());//key.getEncode() => this is in byte form completely then convert to String
//		System.out.println("Secret key " + base64key);
//		
//
//	}
//
//}
