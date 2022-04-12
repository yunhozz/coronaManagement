package coronaManagement.domain.hospital;

import coronaManagement.domain.person.repo.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
