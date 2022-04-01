package coronaManagement.domain.hospital;

import coronaManagement.domain.person.Person;
import coronaManagement.domain.person.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final PersonRepository personRepository;

    public Long makeHospital(HospitalForm form) {
        Hospital hospital = new Hospital(form.getName(), form.getNumberOfBed());
        hospitalRepository.save(hospital);

        return hospital.getId();
    }

    //입원 처리
    public void hospitalize(Long hospitalId, Long... personIds) {
        Optional<Hospital> findHospital = hospitalRepository.findById(hospitalId);

        if (findHospital.isEmpty()) {
            throw new IllegalStateException("There's no hospital you found.");
        }

        Hospital hospital = findHospital.get();

        for (Long personId : personIds) {
            Person person = personRepository.findById(personId).get();
        }
    }

    @Transactional(readOnly = true)
    public Hospital findHospital(Long hospitalId) {
        return hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalStateException("Can't find hospital."));
    }

    @Transactional(readOnly = true)
    public List<Hospital> findHospitals() {
        return hospitalRepository.findAll();
    }
}
