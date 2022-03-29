package coronaManagement;

import coronaManagement.domain.person.Person;
import coronaManagement.domain.routeInformation.Address;
import coronaManagement.domain.virus.Virus;
import coronaManagement.global.dto.RequestPersonDto;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.VirusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService {

        private final EntityManager em;

        public void dbInit() {
            Virus virus = Virus.createVirus(VirusType.ALPHA, "China");
            em.persist(virus);

            RequestPersonDto requestPersonDto = createInfectedPerson(virus);
            Person infectedPerson = requestPersonDto.infectedPersonToEntity();
            em.persist(infectedPerson);
        }

        private RequestPersonDto createInfectedPerson(Virus virus) {
            RequestPersonDto requestPersonDto = new RequestPersonDto();
            requestPersonDto.setName("yunho");
            requestPersonDto.setCity(City.SEOUL);
            requestPersonDto.setGender(Gender.MALE);
            requestPersonDto.setAge(27);
            requestPersonDto.setPhoneNumber(01033317551);
            requestPersonDto.setVirus(virus);
            requestPersonDto.setInfectedAddress(new Address(City.BUSAN, "Seomyun", "HaeWoonDae", "123"));

            return requestPersonDto;
        }
    }
}
