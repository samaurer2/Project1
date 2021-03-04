package dev.maurer.UtilTests;

import dev.maurer.utils.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionUtilTests {

    @Test
    void connectionTest(){
        Connection connection = ConnectionUtil.createConnection();
        Assertions.assertNotNull(connection);
    }
}
