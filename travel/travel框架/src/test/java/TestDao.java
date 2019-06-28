import com.yun.domain.User;
import com.yun.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestDao {
    @Autowired
    private UserService userService;
    @Test
    public void run(){
        List<User> users = userService.findAll();
        for (User u: users
                ) {
            System.out.println(u);
        }
    }
    @Test
    public void run1(){
        List<User> users = userService.findByName("王");
        for (User u: users
                ) {
            System.out.println(u);
        }
    }
}
