import com.eventico.model.entity.Country;
import com.eventico.repo.CountryRepository;
import com.eventico.service.CountryService;
import com.eventico.service.impl.CountryServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class CountryServiceTest {
    private CountryRepository mockedCountryRepository;

    private Country testCountry;

    @Before
    public void init() {
        testCountry = new Country() {{
            setName("Bulgaria");
            setFlag("BG");
            setEvents(new ArrayList<>());
            setCities(new ArrayList<>());
            setUsers(new ArrayList<>());
        }};

        mockedCountryRepository = Mockito.mock(CountryRepository.class);
    }

    @Test
    public void testGetCountry() {
        Mockito.when(mockedCountryRepository.findByName("Bulgaria"))
                .thenReturn(testCountry);

        CountryService service = new CountryServiceImpl(
                mockedCountryRepository
        );

        Assert.assertEquals(testCountry, mockedCountryRepository.findByName("Bulgaria"));
    }

    @Test
    public void testGetAllReturnsAll() {
        Mockito.when(mockedCountryRepository.findAll())
                .thenReturn(new ArrayList<>() {{
                    add(testCountry);
                }});

        CountryService service = new CountryServiceImpl(
                mockedCountryRepository
        );

        Assert.assertEquals(1, service.getAll().size());
        Assert.assertEquals(testCountry, service.getAll().get(0));
    }
}
