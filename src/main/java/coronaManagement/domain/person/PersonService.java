package coronaManagement.domain.person;

import coronaManagement.domain.hospital.repo.HospitalRepository;
import coronaManagement.domain.person.dto.PersonRequest;
import coronaManagement.domain.person.dto.PersonResponse;
import coronaManagement.domain.person.repo.PersonRepository;
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
    private final RouteInformationRepository routeInformationRepository;

    public Long saveVaccinationPerson(PersonRequest personRequest, Long vaccineId) {
        Optional<Vaccine> optionalVaccine = vaccineRepository.findById(vaccineId);

        if (optionalVaccine.isEmpty()) {
            throw new IllegalStateException("Vaccine is null.");
        }

        Vaccine vaccine = optionalVaccine.get();
        personRequest.setVaccine(vaccine);
        Person person = personRequest.vaccinationPersonToEntity();

        personRepository.save(person);

        return person.getId();
    }

    public Long saveNotVaccinationPerson(PersonRequest personRequest) {
        Person person = personRequest.notVaccinationPersonToEntity();
        personRepository.save(person);

        return person.getId();
    }

    public Long saveInfectedPerson(PersonRequest personRequest, Long virusId) {
        Optional<Virus> optionalVirus = virusRepository.findById(virusId);

        if (optionalVirus.isEmpty()) {
            throw new IllegalStateException("Virus is null.");
        }

        Virus virus = optionalVirus.get();
        personRequest.setVirus(virus);
        Person person = personRequest.infectedPersonToEntity();

        personRepository.save(person);

        return person.getId();
    }

    public Long saveContactedPerson(PersonRequest personRequest, RouteInformationRequest routeInformationRequest) {
        Person person = personRequest.contactedPersonToEntity();
        RouteInformation routeInformation = routeInformationRequest.toEntity();

        personRepository.save(person);
        routeInformationRepository.save(routeInformation);

        return person.getId();
    }

    public void reVaccination(Long personId) {
        Optional<VaccinationPerson> findPerson = personRepository.findPersonWhoCanReVaccination(personId);

        if (findPerson.isEmpty()) {
            throw new IllegalStateException("This person can't be re vaccinated.");
        }

        VaccinationPerson vaccinationPerson = findPerson.get();
        vaccinationPerson.reVaccination();
    }

    public void getInfected(Long personId, Long virusId, PersonRequest personRequest) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        Optional<Virus> optionalVirus = virusRepository.findById(virusId);

        if (optionalPerson.isEmpty() || optionalVirus.isEmpty()) {
            throw new IllegalStateException("Can't find person or virus.");
        }

        Person person = optionalPerson.get();
        Virus virus = optionalVirus.get();

        //감염자 -> true
        if (personRepository.findPersonWhoInfectedOrNot(person.getId())) {
            throw new IllegalStateException("This person is already infected.");
        }

        String dType = personRepository.findPersonWhereIncluded(person.getId()); //dType 조회
        String distinguishId = person.getId().toString();

        switch (dType) {
            case "V" -> {
                VaccinationPerson vaccinationPerson = (VaccinationPerson) person;
                PersonResponse personResponse = new PersonResponse(vaccinationPerson);

                createPersonRequestByResponse(personRequest, personResponse, virus, distinguishId);
                vaccinationPerson.getInfected();

                personRepository.save(personRequest.infectedPersonToEntity());
            }

            case "NV" -> {
                NotVaccinationPerson notVaccinationPerson = (NotVaccinationPerson) person;
                PersonResponse personResponse = new PersonResponse(notVaccinationPerson);

                createPersonRequestByResponse(personRequest, personResponse, virus, distinguishId);
                notVaccinationPerson.getInfected();

                personRepository.save(personRequest.infectedPersonToEntity());
            }

            case "C" -> {
                ContactedPerson contactedPerson = (ContactedPerson) person;
                PersonResponse personResponse = new PersonResponse(contactedPerson);

                createPersonRequestByResponse(personRequest, personResponse, virus, distinguishId);
                contactedPerson.getInfected();

                personRepository.save(personRequest.infectedPersonToEntity());
            }

            default -> throw new IllegalStateException("This person is already infected.");
        }
    }

    @Transactional(readOnly = true)
    public List<VaccinationPerson> findReVaccinationPeople(int nextVaccinationCount) {
        return personRepository.findPeopleWhoMustReVaccination(nextVaccinationCount);
    }

    @Transactional(readOnly = true)
    public Person findPerson(Long personId) throws Throwable {
        return (Person) personRepository.findById(personId)
                .orElseThrow(() -> new IllegalStateException("Can't find person."));
    }

    @Transactional(readOnly = true)
    public List<Person> findPeople() {
        return personRepository.findAll();
    }

    private void createPersonRequestByResponse(PersonRequest personRequest, PersonResponse personResponse, Virus virus, String distinguishId) {
        personRequest.setName(personResponse.getName());
        personRequest.setCity(personResponse.getCity());
        personRequest.setGender(personResponse.getGender());
        personRequest.setAge(personResponse.getAge());
        personRequest.setPhoneNumber(personResponse.getPhoneNumber());
        personRequest.setVirus(virus);
        personRequest.setDistinguishId(distinguishId);
    }
}
