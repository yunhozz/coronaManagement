package coronaManagement.domain.hospital;

import coronaManagement.domain.hospital.repo.HospitalRepository;
import coronaManagement.domain.person.InfectedPerson;
import coronaManagement.domain.person.Person;
import coronaManagement.domain.person.repo.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        List<Hospital> hospitals = this.findHospitals();

        //병원 이름 중복 불가
        if (hospitals.size() != 0) {
            if (hospitals.stream().anyMatch(h -> h.getName().equals(hospital.getName()))) {
                throw new IllegalStateException("This hospital is already exist.");
            }
        }

        hospitalRepository.save(hospital);

        return hospital.getId();
    }

    /*
    입원 처리
     */
    public void hospitalize(Long hospitalId, Long... personIds) {
        List<InfectedPerson> infectedPersonList = new ArrayList<>();
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalStateException("Hospital is empty."));

        for (Long personId : personIds) {
            Optional<Person> optionalPerson = personRepository.findById(personId);

            if (optionalPerson.isEmpty()) {
                throw new IllegalStateException("Person is empty.");
            }

            infectedPersonList.add((InfectedPerson) optionalPerson.get());
        }

        if (!infectedPersonList.isEmpty()) {
            hospital.hospitalize(infectedPersonList);

        } else {
            throw new IllegalStateException("There are not person to hospitalize.");
        }
    }

    /*
    치료 성공
     */
    public void completeTreatment(Long hospitalId, Long... personIds) {
        List<InfectedPerson> infectedPersonList = new ArrayList<>();
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalStateException("Hospital is empty."));

        for (Long personId : personIds) {
            Optional<Person> optionalPerson = personRepository.findById(personId);

            if (optionalPerson.isEmpty()) {
                throw new IllegalStateException("Person is empty.");
            }

            if (!personRepository.findInfectedPersonWhoHospitalized()) {
                throw new IllegalStateException("This person is not in hospital.");
            }

            infectedPersonList.add((InfectedPerson) optionalPerson.get());
        }

        hospital.completeTreatment(infectedPersonList);
    }

    /*
    치료 실패
     */
    public void failToTreatment(Long hospitalId, Long... personIds) {
        List<InfectedPerson> infectedPersonList = new ArrayList<>();
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalStateException("Hospital is empty."));

        for (Long personId : personIds) {
            Optional<Person> optionalPerson = personRepository.findById(personId);

            if (optionalPerson.isEmpty()) {
                throw new IllegalStateException("Person is empty.");
            }

            if (!personRepository.findInfectedPersonWhoHospitalized() || personRepository.findInfectedPersonWhoDead()) {
                throw new IllegalStateException("This person is not in hospital or already dead.");
            }

            infectedPersonList.add((InfectedPerson) optionalPerson.get());
        }

        hospital.failTreatment(infectedPersonList);
    }

    @Transactional(readOnly = true)
    public Hospital findHospital(Long hospitalId) {
        return hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalStateException("Hospital is empty."));
    }

    @Transactional(readOnly = true)
    public List<Hospital> findHospitals() {
        return hospitalRepository.findAll();
    }
}
