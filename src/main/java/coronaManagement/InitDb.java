package coronaManagement;

import coronaManagement.domain.person.Person;
import coronaManagement.domain.routeInformation.Address;
import coronaManagement.domain.virus.Virus;
import coronaManagement.global.dto.PersonDto;
import coronaManagement.global.enums.City;
import coronaManagement.global.enums.Gender;
import coronaManagement.global.enums.VirusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

//@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

//    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService {

        private final EntityManager em;

        public void dbInit() {
            Virus virus = Virus.createVirus(VirusType.ALPHA, "China");
            em.persist(virus);

            PersonDto personDto = createInfectedPerson(virus);
            Person infectedPerson = personDto.infectedPersonToEntity();
            em.persist(infectedPerson);
        }

        private PersonDto createInfectedPerson(Virus virus) {
            PersonDto personDto = new PersonDto();
            personDto.setName("yunho");
            personDto.setCity(City.SEOUL);
            personDto.setGender(Gender.MALE);
            personDto.setAge(27);
            personDto.setPhoneNumber(01033317551);
            personDto.setVirus(virus);
            personDto.setInfectedAddress(new Address(City.BUSAN, "Seomyun", "HaeWoonDae", "123"));

            return personDto;
        }
    }
}
