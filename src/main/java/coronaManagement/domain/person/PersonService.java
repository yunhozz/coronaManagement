package coronaManagement.domain.person;

import coronaManagement.domain.person.dto.PersonRequest;
import coronaManagement.domain.person.dto.PersonResponse;
import coronaManagement.domain.person.repo.PersonRepository;
import coronaManagement.domain.record.EachRecord;
import coronaManagement.domain.record.EachRecordRepository;
import coronaManagement.domain.routeInformation.RouteInformation;
import coronaManagement.domain.routeInformation.RouteInformationRepository;
import coronaManagement.domain.routeInformation.dto.RouteInformationRequest;
import coronaManagement.domain.vaccine.Vaccine;
import coronaManagement.domain.vaccine.VaccineRepository;
import coronaManagement.domain.virus.Virus;
import coronaManagement.domain.virus.VirusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final VaccineRepository vaccineRepository;
    private final VirusRepository virusRepository;
    private final EachRecordRepository eachRecordRepository;
    private final RouteInformationRepository routeInformationRepository;

    public Long saveVaccinationPerson(PersonRequest personRequest, Long vaccineId, Long eachRecordId) {
        Optional<Vaccine> optionalVaccine = vaccineRepository.findById(vaccineId);
        Optional<EachRecord> optionalEachRecord = eachRecordRepository.findById(eachRecordId);

        if (optionalVaccine.isEmpty() || optionalEachRecord.isEmpty()) {
            throw new IllegalStateException("Vaccine or eachRecord is null.");
        }

        Vaccine vaccine = optionalVaccine.get();
        EachRecord eachRecord = optionalEachRecord.get();

        VaccinationPerson vaccinationPerson = (VaccinationPerson) personRequest.vaccinationPersonToEntity();
        vaccinationPerson.updateField(vaccine, eachRecord);

        vaccine.removeQuantity(1);
        eachRecord.addVaccination();

        personRepository.save(vaccinationPerson);

        return vaccinationPerson.getId();
    }

    public Long saveNotVaccinationPerson(PersonRequest personRequest) {
        NotVaccinationPerson notVaccinationPerson = (NotVaccinationPerson) personRequest.notVaccinationPersonToEntity();
        personRepository.save(notVaccinationPerson);

        return notVaccinationPerson.getId();
    }

    public Long saveInfectedPerson(PersonRequest personRequest, Long virusId, Long eachRecordId) {
        Optional<Virus> optionalVirus = virusRepository.findById(virusId);
        Optional<EachRecord> optionalEachRecord = eachRecordRepository.findById(eachRecordId);

        if (optionalVirus.isEmpty() || optionalEachRecord.isEmpty()) {
            throw new IllegalStateException("Virus or eachRecord is null.");
        }

        Virus virus = optionalVirus.get();
        EachRecord eachRecord = optionalEachRecord.get();

        InfectedPerson infectedPerson = (InfectedPerson) personRequest.infectedPersonToEntity();
        infectedPerson.updateField(virus, eachRecord);

        virus.addInfectionCount();
        eachRecord.addInfection();

        personRepository.save(infectedPerson);

        return infectedPerson.getId();
    }

    public Long saveContactedPerson(PersonRequest personRequest, RouteInformationRequest routeInformationRequest, Long infectedPersonId) {
        ContactedPerson contactedPerson = (ContactedPerson) personRequest.contactedPersonToEntity();
        RouteInformation routeInformation = routeInformationRequest.toEntity();
        Optional<Person> optionalPerson = this.findPerson(infectedPersonId);

        if (optionalPerson.isEmpty()) {
            throw new IllegalStateException("InfectedPerson is null.");
        }

        InfectedPerson infectedPerson = (InfectedPerson) optionalPerson.get();
        routeInformation.updateField(infectedPerson);
        contactedPerson.updateField(routeInformation);

        personRepository.save(contactedPerson); //routeInformation auto persist

        return contactedPerson.getId();
    }

    public void reVaccination(Long personId, Long eachRecordId) {
        Optional<Person> optionalPerson = personRepository.findPersonWhoCanReVaccination(personId);
        Optional<EachRecord> optionalEachRecord = eachRecordRepository.findById(eachRecordId);

        if (optionalPerson.isEmpty() || optionalEachRecord.isEmpty()) {
            throw new IllegalStateException("Person or eachRecord is null.");
        }

        VaccinationPerson vaccinationPerson = (VaccinationPerson) optionalPerson.get();
        EachRecord eachRecord = optionalEachRecord.get();

        vaccinationPerson.reVaccination();
        eachRecord.addVaccination();
    }

    public void getInfected(Long personId, Long virusId, Long eachRecordId, PersonRequest personRequest) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        Optional<Virus> optionalVirus = virusRepository.findById(virusId);
        Optional<EachRecord> optionalEachRecord = eachRecordRepository.findById(eachRecordId);

        if (optionalPerson.isEmpty() || optionalVirus.isEmpty() || optionalEachRecord.isEmpty()) {
            throw new IllegalStateException("Field is null.");
        }

        Person person = optionalPerson.get();
        Virus virus = optionalVirus.get();
        EachRecord eachRecord = optionalEachRecord.get();

        //감염자 -> true
        if (personRepository.findPersonWhoInfectedOrNot(person.getId())) {
            throw new IllegalStateException("This person is already infected.");
        }

        virus.addInfectionCount();
        eachRecord.addInfection();

        String dType = personRepository.findPersonWhereIncluded(person.getId()); //dType 조회
        String distinguishId = person.getId().toString();

        switch (dType) {
            case "V" -> {
                VaccinationPerson vaccinationPerson = (VaccinationPerson) person;
                PersonResponse personResponse = new PersonResponse(vaccinationPerson);

                createPersonRequestByResponse(personRequest, personResponse, distinguishId);
                vaccinationPerson.getInfected();

                InfectedPerson infectedPerson = (InfectedPerson) personRequest.infectedPersonToEntity();
                infectedPerson.updateField(virus, eachRecord);

                personRepository.save(infectedPerson);
            }

            case "NV" -> {
                NotVaccinationPerson notVaccinationPerson = (NotVaccinationPerson) person;
                PersonResponse personResponse = new PersonResponse(notVaccinationPerson);

                createPersonRequestByResponse(personRequest, personResponse, distinguishId);
                notVaccinationPerson.getInfected();

                InfectedPerson infectedPerson = (InfectedPerson) personRequest.infectedPersonToEntity();
                infectedPerson.updateField(virus, eachRecord);

                personRepository.save(infectedPerson);
            }

            case "C" -> {
                ContactedPerson contactedPerson = (ContactedPerson) person;
                PersonResponse personResponse = new PersonResponse(contactedPerson);

                createPersonRequestByResponse(personRequest, personResponse, distinguishId);
                contactedPerson.getInfected();

                InfectedPerson infectedPerson = (InfectedPerson) personRequest.infectedPersonToEntity();
                infectedPerson.updateField(virus, eachRecord);

                personRepository.save(infectedPerson);
            }

            default -> throw new IllegalStateException("This person is already infected.");
        }
    }

    @Transactional(readOnly = true)
    public List<VaccinationPerson> findReVaccinationPeople(int nextVaccinationCount) {
        return personRepository.findPeopleWhoMustReVaccination(nextVaccinationCount);
    }

    @Transactional(readOnly = true)
    public Optional<Person> findPerson(Long personId) {
        return (Optional<Person>) personRepository.findById(personId);
    }

    @Transactional(readOnly = true)
    public List<Person> findPeople() {
        return personRepository.findAll();
    }

    private void createPersonRequestByResponse(PersonRequest personRequest, PersonResponse personResponse, String distinguishId) {
        personRequest.setName(personResponse.getName());
        personRequest.setCity(personResponse.getCity());
        personRequest.setGender(personResponse.getGender());
        personRequest.setAge(personResponse.getAge());
        personRequest.setPhoneNumber(personResponse.getPhoneNumber());
        personRequest.setDistinguishId(distinguishId);
    }
}
