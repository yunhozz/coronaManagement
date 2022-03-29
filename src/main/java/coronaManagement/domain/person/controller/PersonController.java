package coronaManagement.domain.person.controller;

import coronaManagement.domain.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
}
