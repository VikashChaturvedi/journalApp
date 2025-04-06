package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;
    @Test
     void testSendMail(){
        emailService.sendEmail("1411vikashchaturvedi@gmail.com","Learning SpringBot Mail Functionality","My first Mail via SB mailing Service... Hoorayy!!");
    }
}
