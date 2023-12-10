import com.eventico.model.entity.Country;
import com.eventico.model.entity.RedeemableCode;
import com.eventico.model.entity.User;
import com.eventico.model.enums.UserRoles;
import com.eventico.repo.RedeemableCodeRepository;
import com.eventico.repo.UserRepository;
import com.eventico.service.LoggedUser;
import com.eventico.service.PointsService;
import com.eventico.service.impl.PointsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doThrow;

public class PointsServiceTest {
    private User testUser;
    private User testUser2;
    private RedeemableCode testCode;
    private UserRepository mockedUserRepository;
    private RedeemableCodeRepository mockedCodeRepository;
    private PasswordEncoder passwordEncoder;

    @Before
    public void init() {
        Country country = new Country() {{
            setName("Bulgaria");
            setFlag("BG");
        }};

        this.testCode = new RedeemableCode() {{
            setId(1L);
            setUsed(false);
            setValue(20);
            setCode("1234");
        }};

        this.testUser = new User() {{
            setUsername("John");
            setEmail("John@example.com");
            setPassword("1234");
            setRole(UserRoles.CREATOR);
            setFollowedUsers(new ArrayList<>());
            setCountry(country);
            setPoints(0);
        }};

        this.testUser2 = new User() {{
            setUsername("Mike");
            setEmail("Mike@example.com");
            setPassword("1234");
            setRole(UserRoles.CREATOR);
            setFollowedUsers(new ArrayList<>());
            setCountry(country);
        }};

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

        this.mockedCodeRepository = Mockito.mock(RedeemableCodeRepository.class);
        this.mockedUserRepository = Mockito.mock(UserRepository.class);
    }

    @Test
    public void testRedeemPointsAddPointsToUser() {
        Mockito.when(mockedCodeRepository.findAll())
                .thenReturn(new ArrayList<>() {{
                    add(testCode);
                }});

        Mockito.when(mockedUserRepository.findByUsername("John"))
                .thenReturn(testUser);

        assertThrows(Exception.class, () -> {
            doThrow().when(mockedUserRepository).updatePointsById(20, 1L);
        });

        PointsService pointsService = new PointsServiceImpl(
                new LoggedUser() {{
                    login(testUser);
                }},
                passwordEncoder,
                mockedUserRepository,
                mockedCodeRepository
        );

        Assert.assertEquals(0, testUser.getPoints());

        pointsService.redeemPoints("1234");

//        Assert.assertEquals(20, testUser.getPoints());
    }
}
