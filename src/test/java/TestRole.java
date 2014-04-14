import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dtr.oas.config.DatabaseConfig;
import com.dtr.oas.dao.DuplicateRoleException;
import com.dtr.oas.dao.RoleNotFoundException;
import com.dtr.oas.model.Role;
import com.dtr.oas.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfig.class})
public class TestRole {
    
    private static final String DUP_ROLE_CREATE  = "ROLE_USER";
    private static final String ROLE_CREATE      = "ROLE_TESTER";
    private static final String ROLE_MOD         = "ROLE_TESTER_01";
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private RoleService roleService;

    @Test(expected=DuplicateRoleException.class) 
    public void insertDuplicateRole() throws Exception {
        System.out.println("\n\n### Starting: insertDuplicateRole()");
        
        Role role = new Role();
        role.setRolename(DUP_ROLE_CREATE);
        
        roleService.addRole(role);
        
        System.out.println("\n\n### Ending: insertDuplicateRole()");
    }

    @Test(expected=RoleNotFoundException.class)
    public void getNonExistentRole() throws Exception {
        System.out.println("\n\n### Starting: getNonExistentRole()");
        
        Role role = roleService.getRole("ROGUE_ROLE");
        System.out.println("Role: [" + role.toString() +"]");
        System.out.println("\n\n### Ending: getNonExistentRole()");
    }
    
    @Test(expected=RoleNotFoundException.class)
    public void getNonExistentRoleByID() throws Exception {
        System.out.println("\n\n### Starting: getNonExistentRoleByID()");
        
        Role role = roleService.getRole(99);
        System.out.println("Role: [" + role.toString() +"]");
        System.out.println("\n\n### Ending: getNonExistentRoleByID()");
    }
    
    @Test
    public void testRole() throws Exception {
        System.out.println("\n\n### Starting: testInsertRole()");

        Role role = new Role();
        role.setRolename(ROLE_CREATE);
        
        roleService.addRole(role);
        
        System.out.println("### Ending: testInsertRole()");
    
        
        
        System.out.println("\n\n### Starting: testGetRoleByInt(): " + ROLE_CREATE);
        
        role = roleService.getRole(ROLE_CREATE);
        System.out.println("  " + role.toString());

        System.out.println("### Ending: testGetRoleByInt()");

        
        
        System.out.println("\n\n### Starting: testGetRoleByName(): " + ROLE_CREATE);
        
        role = roleService.getRole(ROLE_CREATE);
        System.out.println("  " + role.toString());

        System.out.println("### Ending: testGetRoleByName()");


        
        System.out.println("\n\n### Starting: testUpdateRole()");
        
        role = roleService.getRole(ROLE_CREATE);
        System.out.println("  Init user: " + role.toString());

        role.setRolename(ROLE_MOD);
        roleService.updateRole(role);
        
        roleService.updateRole(role);
        role = roleService.getRole(role.getId());
        System.out.println("  Mod user: " + role.toString());

        System.out.println("### Ending: testUpdateRole()");

        
        
        System.out.println("\n\n### Starting: testDeleteRole()");

        role = roleService.getRole(role.getId());
        System.out.println("  " + role.toString());

        roleService.deleteRole(role.getId());

        System.out.println("### Ending: testDeleteRole()\n\n\n");
    }

    @Test
    public void testGetAllRoles() throws Exception {
        System.out.println("\n\n### Starting: testGetAllRoles()");
        List<Role> rolesList = roleService.getRoles();
        
        System.out.println("  List:");
        for (Role role : rolesList) {
            System.out.println("    " + role.toString());
        }
        System.out.println("### Ending: testGetAllRoles()\n\n");
    }
    
    public void testListAllBeans() throws Exception {
        final String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (final String beanName : beanNames) {   
            System.out.println("\tbean name:" + beanName); 
        }
    }
}
