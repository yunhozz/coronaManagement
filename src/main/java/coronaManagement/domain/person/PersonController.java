package coronaManagement.domain.person;

import coronaManagement.domain.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
}
