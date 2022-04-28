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

            if (!personRepository.findInfectedPersonWhoInfectedOrNot()) {
                throw new IllegalStateException("This person is already hospitalized or isolated.");
            }

            infectedPersonList.add((InfectedPerson) optionalPerson.get());
        }

        hospital.hospitalize(infectedPersonList);
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

        hospital.completeTreatment(infectedPersonList);
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
