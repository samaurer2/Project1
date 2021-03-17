package dev.maurer.UtilTests;

import dev.maurer.utils.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HibernateUtilTests {

    @Test
    public void createSessionFactoryTest(){
        SessionFactory sf = HibernateUtil.getSessionFactory(true);
        Assertions.assertNotNull(sf);
    }
}
