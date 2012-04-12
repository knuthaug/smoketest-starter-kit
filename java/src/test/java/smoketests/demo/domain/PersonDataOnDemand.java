package smoketests.demo.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class PersonDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Person> data;

	public Person getNewTransientPerson(int index) {
        Person obj = new Person();
        setBirthYear(obj, index);
        setFirstName(obj, index);
        setLastName(obj, index);
        return obj;
    }

	public void setBirthYear(Person obj, int index) {
        int birthYear = index;
        obj.setBirthYear(birthYear);
    }

	public void setFirstName(Person obj, int index) {
        String firstName = "firstName_" + index;
        obj.setFirstName(firstName);
    }

	public void setLastName(Person obj, int index) {
        String lastName = "lastName_" + index;
        obj.setLastName(lastName);
    }

	public Person getSpecificPerson(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Person obj = data.get(index);
        return Person.findPerson(obj.getId());
    }

	public Person getRandomPerson() {
        init();
        Person obj = data.get(rnd.nextInt(data.size()));
        return Person.findPerson(obj.getId());
    }

	public boolean modifyPerson(Person obj) {
        return false;
    }

	public void init() {
        data = Person.findPersonEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Person' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<smoketests.demo.domain.Person>();
        for (int i = 0; i < 10; i++) {
            Person obj = getNewTransientPerson(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator(); it.hasNext();) {
                    ConstraintViolation<?> cv = it.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
}
