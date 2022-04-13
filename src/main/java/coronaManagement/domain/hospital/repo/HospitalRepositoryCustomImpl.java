package coronaManagement.domain.hospital.repo;

import coronaManagement.domain.hospital.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HospitalRepositoryCustomImpl implements HospitalRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Hospital> findHospitalWithInfectedPerson(int offset, int limit) {
        return em.createQuery(
                "select distinct h from Hospital h" +
                        " join fetch h.infectedPerson ip", Hospital.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
