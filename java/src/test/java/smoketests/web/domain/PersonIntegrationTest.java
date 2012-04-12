package smoketests.web.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
public class PersonIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private PersonDataOnDemand dod;

	@Test
    public void testCountPeople() {
        org.junit.Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", dod.getRandomPerson());
        long count = smoketests.web.domain.Person.countPeople();
        org.junit.Assert.assertTrue("Counter for 'Person' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindPerson() {
        smoketests.web.domain.Person obj = dod.getRandomPerson();
        org.junit.Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Person' failed to provide an identifier", id);
        obj = smoketests.web.domain.Person.findPerson(id);
        org.junit.Assert.assertNotNull("Find method for 'Person' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Person' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllPeople() {
        org.junit.Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", dod.getRandomPerson());
        long count = smoketests.web.domain.Person.countPeople();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Person', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<smoketests.web.domain.Person> result = smoketests.web.domain.Person.findAllPeople();
        org.junit.Assert.assertNotNull("Find all method for 'Person' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Person' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindPersonEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", dod.getRandomPerson());
        long count = smoketests.web.domain.Person.countPeople();
        if (count > 20) count = 20;
        java.util.List<smoketests.web.domain.Person> result = smoketests.web.domain.Person.findPersonEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Person' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Person' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        smoketests.web.domain.Person obj = dod.getRandomPerson();
        org.junit.Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Person' failed to provide an identifier", id);
        obj = smoketests.web.domain.Person.findPerson(id);
        org.junit.Assert.assertNotNull("Find method for 'Person' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPerson(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Person' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMerge() {
        smoketests.web.domain.Person obj = dod.getRandomPerson();
        org.junit.Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Person' failed to provide an identifier", id);
        obj = smoketests.web.domain.Person.findPerson(id);
        boolean modified =  dod.modifyPerson(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        smoketests.web.domain.Person merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Person' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", dod.getRandomPerson());
        smoketests.web.domain.Person obj = dod.getNewTransientPerson(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Person' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Person' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Person' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        smoketests.web.domain.Person obj = dod.getRandomPerson();
        org.junit.Assert.assertNotNull("Data on demand for 'Person' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Person' failed to provide an identifier", id);
        obj = smoketests.web.domain.Person.findPerson(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Person' with identifier '" + id + "'", smoketests.web.domain.Person.findPerson(id));
    }
}
