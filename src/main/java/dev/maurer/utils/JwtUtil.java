package dev.maurer.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.maurer.entities.EmployeeType;

public class JwtUtil {
    private static final String SECRET ="I always ban Yasuo.";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    public static String generate(String type, String employeeName, int employeeId){
        return JWT.create()
                .withClaim("type",type)
                .withClaim("employeeName",employeeName)
                .withClaim("employeeId", employeeId)
                .sign(ALGORITHM);
    }

    public static DecodedJWT isValidJWT(String token){
        return JWT.require(ALGORITHM).build().verify(token);
    }
}
