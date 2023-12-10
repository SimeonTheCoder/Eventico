import com.eventico.model.dto.EventAddBinding;
import com.eventico.model.entity.City;
import com.eventico.model.entity.Country;
import com.eventico.model.entity.Event;
import com.eventico.model.entity.User;
import com.eventico.model.enums.EventCategory;
import com.eventico.model.enums.UserRoles;
import com.eventico.repo.*;
import com.eventico.service.EventService;
import com.eventico.service.LoggedUser;
import com.eventico.service.impl.EventServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class EventServiceTests {
    private Event testEvent;
    private Event testEvent2;

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
            setParticipationEvents(new ArrayList<>());
            setAddedEvents(new ArrayList<>() {{
                add(testEvent);
            }});
            setPoints(15);
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
            setAddedBy(testUser);
        }};

        this.testUser2 = new User() {{
            setUsername("Mike");
            setEmail("John@example.com");
            setPassword("1234");
            setRole(UserRoles.USER);
            setFollowedUsers(new ArrayList<>());
            setCountry(country);
            setParticipationEvents(new ArrayList<>());
            setPoints(5);
            setFollowedUsers(new ArrayList<>() {{
                add(testUser);
            }});
        }};

        this.mockedCountryRepository = Mockito.mock(CountryRepository.class);
        this.mockedCityRepository = Mockito.mock(CityRepository.class);

        Mockito.when(this.mockedCountryRepository
                        .findByName("Bulgaria"))
                .thenReturn(country);
        Mockito.when(this.mockedCityRepository
                        .findByName("Sofia"))
                .thenReturn(city);

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
        this.mockedEventRepository = Mockito.mock(EventRepository.class);
        this.mockedReportRepository = Mockito.mock(ReportRepository.class);
    }

    @Test
    public void testGetEventReturnsCorrectEvent() {
        Mockito.when(this.mockedEventRepository
                        .findById(1L))
                .thenReturn(Optional.ofNullable(testEvent));


        EventService eventService = new EventServiceImpl(
                mockedUserRepository,
                mockedEventRepository,
                mockedReportRepository,
                mockedCountryRepository,
                mockedCityRepository,
                new LoggedUser() {{
                    login(testUser);
                }}
        );

        Assert.assertEquals(testEvent, eventService.getEvent(1L));
    }

    @Test
    public void testEnrollEventEnrollsUserIntoEvent() {
        Mockito.when(this.mockedEventRepository
                        .findById(1L))
                .thenReturn(Optional.ofNullable(testEvent));

        Mockito.when(this.mockedUserRepository
                        .findByUsername("John"))
                .thenReturn(testUser);

        Mockito.when(this.mockedUserRepository
                        .findByUsername("Mike"))
                .thenReturn(testUser2);

        LoggedUser loggedUser = new LoggedUser() {{
            login(testUser);
        }};

        EventService eventService = new EventServiceImpl(
                mockedUserRepository,
                mockedEventRepository,
                mockedReportRepository,
                mockedCountryRepository,
                mockedCityRepository,
                loggedUser
        );

        Assert.assertEquals(0, testEvent.getParticipants().size());

        eventService.enroll(1L);

        Assert.assertEquals(1, testEvent.getParticipants().size());

        loggedUser.logout();
        loggedUser.login(testUser2);

        Assert.assertEquals(1, testEvent.getParticipants().size());
        eventService.enroll(1L);
        Assert.assertEquals(1, testEvent.getParticipants().size());
    }

    @Test
    public void testEventServiceGetAll() {
        Mockito.when(this.mockedEventRepository
                        .findAll())
                .thenReturn(new ArrayList<>() {{
                    add(testEvent);
                }});

        LoggedUser loggedUser = new LoggedUser() {{
            login(testUser);
        }};

        EventService eventService = new EventServiceImpl(
                mockedUserRepository,
                mockedEventRepository,
                mockedReportRepository,
                mockedCountryRepository,
                mockedCityRepository,
                loggedUser
        );

        Assert.assertTrue(eventService.getAll().get(0).equals(testEvent));
    }

    @Test
    public void testAddEventAddsEvent() {
        Mockito.when(this.mockedUserRepository
                        .findByUsername("John"))
                .thenReturn(testUser);

        LoggedUser loggedUser = new LoggedUser() {{
            login(testUser);
        }};

        EventService eventService = new EventServiceImpl(
                mockedUserRepository,
                mockedEventRepository,
                mockedReportRepository,
                mockedCountryRepository,
                mockedCityRepository,
                loggedUser
        );

        eventService.getUserEvents();

        Assert.assertTrue(eventService.addEvent(new EventAddBinding() {{
            setName(testEvent.getName());
            setCategory(testEvent.getCategory());
            setDescription(testEvent.getDescription());
            setLocation(testEvent.getLocation());
            setCountry(testEvent.getCountry().getName());
            setCity(testEvent.getCity().getName());
            setStart(testEvent.getStart());
            setEnd(testEvent.getEnd());
            setCost(testEvent.getCost());
            setFilename(new MultipartFile() {
                @Override
                public String getName() {
                    return "alabala";
                }

                @Override
                public String getOriginalFilename() {
                    return "alabala.jpg";
                }

                @Override
                public String getContentType() {
                    return "jpg";
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public long getSize() {
                    return 25;
                }

                @Override
                public byte[] getBytes() throws IOException {
                    return new byte[5];
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return null;
                }

                @Override
                public void transferTo(File dest) throws IOException, IllegalStateException {

                }
            });
        }}));
    }

    @Test
    public void testGetAllByUser() {
        Mockito.when(this.mockedUserRepository.findByUsername("John"))
                .thenReturn(testUser);

        Mockito.when(this.mockedEventRepository
                        .findAll())
                .thenReturn(new ArrayList<>() {{
                    add(testEvent);
                }});

        LoggedUser loggedUser = new LoggedUser() {{
            login(testUser);
            setCreator(true);
        }};

        EventService eventService = new EventServiceImpl(
                mockedUserRepository,
                mockedEventRepository,
                mockedReportRepository,
                mockedCountryRepository,
                mockedCityRepository,
                loggedUser
        );

        Assert.assertEquals(1, eventService.getUserEvents().size());
        Assert.assertTrue(eventService.getUserEvents().get(0).equals(testEvent));
    }

    @Test
    public void testGetEventsForUserFeed() {
        Mockito.when(this.mockedUserRepository.findByUsername("John"))
                .thenReturn(testUser);

        Mockito.when(this.mockedUserRepository.findByUsername("Mike"))
                .thenReturn(testUser2);

        Mockito.when(this.mockedEventRepository
                        .findAll())
                .thenReturn(new ArrayList<>() {{
                    add(testEvent);
                }});

        LoggedUser loggedUser = new LoggedUser() {{
            login(testUser2);
            setCreator(true);
        }};

        EventService eventService = new EventServiceImpl(
                mockedUserRepository,
                mockedEventRepository,
                mockedReportRepository,
                mockedCountryRepository,
                mockedCityRepository,
                loggedUser
        );

        Assert.assertEquals(1, eventService.getEventsForUser("Mike").getForyou().size());
    }
}
