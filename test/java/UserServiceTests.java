import com.eventico.model.dto.UserLoginBinding;
import com.eventico.model.dto.UserRegisterBinding;
import com.eventico.model.entity.Country;
import com.eventico.model.entity.User;
import com.eventico.model.enums.UserRoles;
import com.eventico.repo.CountryRepository;
import com.eventico.repo.EventRepository;
import com.eventico.repo.UserRepository;
import com.eventico.service.LoggedUser;
import com.eventico.service.UserService;
import com.eventico.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

public class UserServiceTests {
    private User testUser;
    private User testUser2;
    private PasswordEncoder passwordEncoder;
    private UserRepository mockedUserRepository;
    private EventRepository mockedEventRepository;
    private CountryRepository mockedCountryRepository;

    @Before
    public void init() {
        Country country = new Country() {{
            setName("Bulgaria");
            setFlag("BG");
        }};

        this.testUser = new User() {{
            setUsername("John");
            setEmail("John@example.com");
            setPassword("1234");
            setRole(UserRoles.CREATOR);
            setFollowedUsers(new ArrayList<>());
            setCountry(country);
        }};

        this.testUser2 = new User() {{
            setUsername("Mike");
            setEmail("Mike@example.com");
            setPassword("1234");
            setRole(UserRoles.CREATOR);
            setFollowedUsers(new ArrayList<>());
            setCountry(country);
        }};

        this.mockedCountryRepository = Mockito.mock(CountryRepository.class);
        Mockito.when(this.mockedCountryRepository
                        .findByName("Bulgaria"))
                .thenReturn(country);

        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        };

        this.mockedUserRepository = Mockito.mock(UserRepository.class);
    }

    @Test
    public void testGetUserReturnsCorrectCredentials() {
        Mockito.when(this.mockedUserRepository
                        .findByUsername("John"))
                .thenReturn(testUser);

        UserService userService = new UserServiceImpl(
                this.mockedUserRepository,
                this.mockedEventRepository,
                this.mockedCountryRepository,
                this.passwordEncoder,
                new LoggedUser() {{
                    setLogged(true);
                }}
        );

        Assert.assertEquals(testUser, userService.findByUsername("John"));
    }

    @Test
    public void testUserLoginLogsUserAndSetsData() {
        Mockito.when(this.mockedUserRepository
                        .findByUsername("John"))
                .thenReturn(testUser);

        LoggedUser loggedUser = new LoggedUser(){{
            setFollowedUsers(new ArrayList<>());
        }};

        UserService userService = new UserServiceImpl(
                this.mockedUserRepository,
                this.mockedEventRepository,
                this.mockedCountryRepository,
                this.passwordEncoder,
                loggedUser
        );

        userService.login(
                new UserLoginBinding(){{
                    setUsername("John");
                    setPassword("1234");
                }}
        );

        Assert.assertTrue(loggedUser.isLogged());
        Assert.assertEquals(loggedUser.getUsername(), testUser.getUsername());
        Assert.assertTrue(loggedUser.isCreator());
        Assert.assertFalse(loggedUser.isAdmin());
    }

    @Test
    public void testUserRegisterAddUserToDatabase() {
        LoggedUser loggedUser = new LoggedUser(){{
            setFollowedUsers(new ArrayList<>());
        }};

        UserService userService = new UserServiceImpl(
                this.mockedUserRepository,
                this.mockedEventRepository,
                this.mockedCountryRepository,
                this.passwordEncoder,
                loggedUser
        );

        Assert.assertEquals(mockedUserRepository.count(), 0);

        Assert.assertTrue(userService.register(
                new UserRegisterBinding(userService){{
                    setUsername(testUser.getUsername());
                    setEmail(testUser.getEmail());
                    setPassword(testUser.getPassword());
                    setRepeat(testUser.getPassword());
                    setRole(testUser.getRole());
                    setCountry(testUser.getCountry().getName());
                }}
        ));

        Assert.assertFalse(userService.register(
                new UserRegisterBinding(userService){{
                    setUsername("");
                    setEmail("");
                    setPassword(testUser.getPassword());
                    setRepeat(testUser.getPassword());
                    setRole(testUser.getRole());
                    setCountry(null);
                }}
        ));
    }

    @Test
    public void testFollowUserAddFollowedUserToUserList() {
        Mockito.when(this.mockedUserRepository
                        .findByUsername("John"))
                .thenReturn(testUser);

        Mockito.when(this.mockedUserRepository
                        .findByUsername("Mike"))
                .thenReturn(testUser2);

        UserService userService = new UserServiceImpl(
                this.mockedUserRepository,
                this.mockedEventRepository,
                this.mockedCountryRepository,
                this.passwordEncoder,
                new LoggedUser() {{
                    setLogged(false);
                }}
        );

        userService.login(new UserLoginBinding(){{
            setUsername("John");
            setPassword("1234");
        }});

        Assert.assertEquals(0, userService.findByUsername("John").getFollowedUsers().size());

        userService.follow("Mike");

        Assert.assertEquals(1, userService.findByUsername("John").getFollowedUsers().size());
    }
}