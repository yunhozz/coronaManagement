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

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService {

        private final EntityManager em;

        public void dbInit1() {

        }

        public void dbInit2() {

        }
    }
}
