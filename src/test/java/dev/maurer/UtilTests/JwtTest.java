package dev.maurer.UtilTests;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.maurer.entities.Employee;
import dev.maurer.entities.EmployeeType;
import dev.maurer.utils.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JwtTest {

    @Test
    void jwtTest(){
        Employee employee = new Employee("Annie", "The Dark Child");
        employee.setEmployeeId(1);
        employee.setType(EmployeeType.EMPLOYEE);
        String jwt = JwtUtil.generate(employee.getType().toString(), employee.getName(), employee.getEmployeeId());
        Assertions.assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbXBsb3llZU5hbWUiOiJBbm5pZSIsImVtcGxveWVlSWQiOjEsInR5cGUiOiJFTVBMT1lFRSJ9.FfnN6un8nuPExtIwTWycHfjssq8S9IxmVGvn-u_NbYA", jwt);
    }
    @Test
    void decodeJwtTest(){
        Employee employee = new Employee("Annie", "The Dark Child");
        employee.setEmployeeId(1);
        employee.setType(EmployeeType.EMPLOYEE);
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbXBsb3llZU5hbWUiOiJBbm5pZSIsImVtcGxveWVlSWQiOjEsInR5cGUiOiJFTVBMT1lFRSJ9.FfnN6un8nuPExtIwTWycHfjssq8S9IxmVGvn-u_NbYA";
        DecodedJWT djwt = JwtUtil.isValidJWT(jwt);
        Assertions.assertEquals("Annie", djwt.getClaim("employeeName").asString());
        Assertions.assertEquals("EMPLOYEE", djwt.getClaim("type").asString());
        Assertions.assertEquals(1, djwt.getClaim("employeeId").asInt());

    }
}
