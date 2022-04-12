package coronaManagement.domain.person;

import java.util.List;

public interface PersonRepositoryCustom<T extends Person> {

    List<InfectedPerson> findAllWithVirusHospital(int offset, int limit);
    List<InfectedPerson> findAllWithContactedRoute(int offset, int limit);
}
