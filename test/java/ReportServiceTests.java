import com.eventico.model.dto.EventReportBinding;
import com.eventico.model.entity.*;
import com.eventico.model.enums.EventCategory;
import com.eventico.model.enums.UserRoles;
import com.eventico.repo.*;
import com.eventico.service.LoggedUser;
import com.eventico.service.ReportService;
import com.eventico.service.impl.ReportServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class ReportServiceTests {
    private Event testEvent;
    private Report testReport;
    private User testUser;
    private User testUser2;
    private City city;
    private PasswordEncoder passwordEncoder;
    private UserRepository mockedUserRepository;
    private EventRepository mockedEventRepository;
    private CountryRepository mockedCountryRepository;
    private CityRepository mockedCityRepository;
    private ReportRepository mockedReportRepository;

    @Before
    public void init() {
        Country country = new Country() {{
            setName("Bulgaria");
            setFlag("BG");
        }};

        this.city = new City() {{
            setName("Sofia");
            setCountry(country);
            setCoordinates("coords");
            setEvents(new ArrayList<>());
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

        this.testEvent = new Event() {{
            setName("Test");
            setDescription("Test_event_desc");
            setCategory(EventCategory.ART);
            setCost(10);
            setLocation("ABQ, New Mexico");
            setCity(city);
            setCountry(country);
            setStart(LocalDateTime.now().plusDays(2));
            setEnd(LocalDateTime.now().plusDays(3));
            setParticipants(new ArrayList<>());
            setId(1L);
        }};

        this.testReport = new Report() {{
            setReason("Test");
            setDescription("Test_report_desc.");
            setReportedEvent(testEvent);
            setReportedBy(testUser);
            setReportTime(LocalDateTime.now());
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

        this.mockedEventRepository = Mockito.mock(EventRepository.class);
        this.mockedUserRepository = Mockito.mock(UserRepository.class);
        this.mockedCountryRepository = Mockito.mock(CountryRepository.class);
        this.mockedCityRepository = Mockito.mock(CityRepository.class);
        this.mockedReportRepository = Mockito.mock(ReportRepository.class);
    }

    @Test
    public void testGetReportByIdReturnsCorrectReport() {
        Mockito.when(this.mockedReportRepository.findById(1L))
                .thenReturn(Optional.ofNullable(testReport));

        ReportService reportService = new ReportServiceImpl(
                mockedEventRepository,
                mockedUserRepository,
                mockedReportRepository,
                mockedCityRepository,
                new LoggedUser()
        );

        Assert.assertEquals(testReport, reportService.getReport(1L));
    }

    @Test
    public void testReportEventReportsEvent() {
        Mockito.when(this.mockedReportRepository.findById(1L))
                .thenReturn(Optional.ofNullable(testReport));

        Mockito.when(this.mockedEventRepository.findById(1L))
                .thenReturn(Optional.ofNullable(testEvent));

        ReportService reportService = new ReportServiceImpl(
                mockedEventRepository,
                mockedUserRepository,
                mockedReportRepository,
                mockedCityRepository,
                new LoggedUser(){{
                    setLogged(true);
                }}
        );

        Assert.assertTrue(reportService.reportEvent(1L, new EventReportBinding(){{
            setReason(testReport.getReason());
            setDescription(testReport.getDescription());
        }}));
    }

    @Test
    public void testApproveReport() {
        Mockito.when(this.mockedReportRepository.findById(1L))
                .thenReturn(Optional.ofNullable(testReport));

        Mockito.when(this.mockedEventRepository.findById(1L))
                .thenReturn(Optional.ofNullable(testEvent));

        Mockito.when(this.mockedReportRepository.findById(1L))
                .thenReturn(Optional.ofNullable(testReport));

        LoggedUser loggedUser = new LoggedUser() {{
            setLogged(true);
        }};

        ReportService reportService = new ReportServiceImpl(
                mockedEventRepository,
                mockedUserRepository,
                mockedReportRepository,
                mockedCityRepository,
                loggedUser
        );

        Assert.assertFalse(reportService.approveReport(1L));

        loggedUser.setAdmin(true);

        Assert.assertTrue(reportService.approveReport(1L));
    }
}
