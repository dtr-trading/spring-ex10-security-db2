import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dtr.oas.config.DatabaseConfig;
import com.dtr.oas.dao.DuplicateUserException;
import com.dtr.oas.dao.UserNotFoundException;
import com.dtr.oas.model.Role;
import com.dtr.oas.model.User;
import com.dtr.oas.service.RoleService;
import com.dtr.oas.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfig.class})
public class TestUser {
	
    private static final String DUP_USER_CREATE  = "user";
    private static final String DUP_PASS_CREATE  = "password";
    private static final String TEST_USER_CREATE = "tester001";
	private static final String INIT_PASSWORD    = "password01";
	private static final String MOD_PASSWORD     = "password02";
	private static final String INIT_ROLE        = "ROLE_USER";
    private static final String MOD_ROLE         = "ROLE_ADMIN";
    private static final int    MOD_ROLE_ID      = 1;
	
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test(expected=DuplicateUserException.class) 
    public void insertDuplicate() throws Exception {
        System.out.println("\n\n### Starting: insertDuplicate()");
        
        Role role = (Role) roleService.getRole(INIT_ROLE);
        
        User user = new User();
        user.setUsername(DUP_USER_CREATE);
        user.setPassword(DUP_PASS_CREATE);
        user.setEnabled(true);
        user.setRole(role);
        
        userService.addUser(user);
        
        System.out.println("\n\n### Ending: insertDuplicate()");
    }
    
    @Test(expected=UserNotFoundException.class)
	public void getNonExistentUser() throws Exception {
		System.out.println("\n\n### Starting: getNonExistentUser()");
		
		User user = userService.getUser("RogueTrader");
		System.out.println("User [" + user.toString() + "]");
		
		System.out.println("\n\n### Ending: getNonExistentUser()");
	}
	
    @Test(expected=UserNotFoundException.class)
    public void getNonExistentUserByID() throws Exception {
        System.out.println("\n\n### Starting: getNonExistentUserByID()");
        
        User user = userService.getUser(99);
        System.out.println("User [" + user.toString() + "]");
        
        System.out.println("\n\n### Ending: getNonExistentUserByID()");
    }
    
	@Test
	public void testUser() throws Exception {
		System.out.println("\n\n### Starting: testInsertUser()");
        Role role = (Role) roleService.getRole(INIT_ROLE);
		
		User user = new User();
		user.setUsername(TEST_USER_CREATE);
		user.setPassword(INIT_PASSWORD);
		user.setEnabled(true);
        user.setRole(role);
		
		userService.addUser(user);
		
		user = userService.getUser(TEST_USER_CREATE);
		System.out.println("  " + user.toString());

		System.out.println("### Ending: testInsertUser()");
	
		
		
		System.out.println("\n\n### Starting: testGetUser()");
		
		user = userService.getUser(TEST_USER_CREATE);
		System.out.println("  " + user.toString());

		System.out.println("### Ending: testGetUser()");

		
		
		System.out.println("\n\n### Starting: testUpdateUser()");
		
		user = userService.getUser(TEST_USER_CREATE);
	    role = user.getRole();
	    System.out.println("  Init user: " + user.toString());

		user.setPassword(MOD_PASSWORD);
        role.setId(MOD_ROLE_ID);
        role.setRolename(MOD_ROLE);
        user.setRole(role);
		userService.updateUser(user);
		
		User userMod = userService.getUser(TEST_USER_CREATE);
		System.out.println("  Mod user: " + userMod.toString());

		System.out.println("### Ending: testUpdateUser()");

		
		
		System.out.println("\n\n### Starting: testDeleteUser()");

		user = userService.getUser(TEST_USER_CREATE);
		System.out.println("  " + user.toString());

		userService.deleteUser(user.getId());

		System.out.println("### Ending: testDeleteUser()\n\n\n");
	}

	@Test
	public void testGetAllUsers() throws Exception {
		System.out.println("\n\n### Starting: testGetAllUsers()");
		List<User> usersList = userService.getUsers();
		
		System.out.println("  List:");
		for (User user : usersList) {
			System.out.println("    " + user.toString());
		}
		System.out.println("### Ending: testGetAllUsers()\n\n");
	}
	
	public void testListAllBeans() throws Exception {
		final String[] beanNames = applicationContext.getBeanDefinitionNames();
		for (final String beanName : beanNames) {   
            System.out.println("\tbean name:" + beanName); 
		}
	}
}
