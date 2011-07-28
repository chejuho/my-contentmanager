package jp.co.gmod.manage.service;

import static org.junit.Assert.*;
import jp.co.gmod.manage.entity.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.PreparationType;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.TestContext;

@RunWith(Seasar2.class)
public class UserInfoServiceTest {

	private TestContext ctx;
	private UserInfoService userInfoService;


	public void before() {

		  ctx.setPreparationType(PreparationType.ALL_REPLACE);
	}


    @Test
    public void testLogin() {
    	User user = userInfoService.login("test1", "test");

    	assertEquals("test1", user.user_id);
    	assertNotNull(user);

    }

    @Test
    public void testFirstlogin() {
    	User user = userInfoService.firstlogin("choi", "choi");

    	assertNotNull(user);
    	assertEquals("choi", user.user_id);
    	assertEquals("choi", user.password);
    }

    @Test
    public void testChangePassword() {
    	boolean check = userInfoService.changePassword("choi", "choi");
    	assertTrue(check);
    	User user = userInfoService.login("choi", "choi");
    	assertEquals("choi", user.user_id);
    	assertEquals("a5af3c3f6914c2d7aeb0fb702b940a06", user.password);
    }

}
