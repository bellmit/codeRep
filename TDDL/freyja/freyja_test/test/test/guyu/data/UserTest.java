package test.guyu.data;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.guyu.data.bean.User;
import com.guyu.data.dao.IUserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:./config/spring/spring.xml",
		"file:./config/spring/spring-db.xml",
		"file:./config/spring/spring-cache.xml"

})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class UserTest {

	@Autowired
	private IUserDao userDao;

	@Test
	public void test2() {

	List<User> users=new ArrayList<User>();
	
		for (int i = 0; i < 1000; i++) {
			User user = new User();
			user.setOpenId("" + i);
			users.add(user);
			
//			userDao.saveUser(user);
		}
		userDao.batchSave(users);
		

	}

	// @Test
	public void test1() {
		User user = userDao.getUserByOpenId("1");

		if (user == null) {
			user = new User();
			user.setOpenId("open" + System.currentTimeMillis());
			user.setNickName("open" + System.currentTimeMillis());
			userDao.saveUser(user);
		} else {
			System.out.println(user.getNickName());

			User user2 = userDao.getUser(user.getId());

			System.out.println(user2.getNickName());
			user2.setNickName("new nickname");
			userDao.updateUser(user2);
		}

	}

}
