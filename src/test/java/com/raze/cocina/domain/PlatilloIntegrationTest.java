package com.raze.cocina.domain;
import com.raze.cocina.repository.PlatilloRepository;
import com.raze.cocina.service.PlatilloService;
import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@RooIntegrationTest(entity = Platillo.class)
public class PlatilloIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    PlatilloDataOnDemand dod;

	@Autowired
    PlatilloService platilloService;

	@Autowired
    PlatilloRepository platilloRepository;

	@Test
    public void testCountAllPlatilloes() {
        Assert.assertNotNull("Data on demand for 'Platillo' failed to initialize correctly", dod.getRandomPlatillo());
        long count = platilloService.countAllPlatilloes();
        Assert.assertTrue("Counter for 'Platillo' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindPlatillo() {
        Platillo obj = dod.getRandomPlatillo();
        Assert.assertNotNull("Data on demand for 'Platillo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Platillo' failed to provide an identifier", id);
        obj = platilloService.findPlatillo(id);
        Assert.assertNotNull("Find method for 'Platillo' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Platillo' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllPlatilloes() {
        Assert.assertNotNull("Data on demand for 'Platillo' failed to initialize correctly", dod.getRandomPlatillo());
        long count = platilloService.countAllPlatilloes();
        Assert.assertTrue("Too expensive to perform a find all test for 'Platillo', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Platillo> result = platilloService.findAllPlatilloes();
        Assert.assertNotNull("Find all method for 'Platillo' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Platillo' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindPlatilloEntries() {
        Assert.assertNotNull("Data on demand for 'Platillo' failed to initialize correctly", dod.getRandomPlatillo());
        long count = platilloService.countAllPlatilloes();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Platillo> result = platilloService.findPlatilloEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Platillo' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Platillo' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Platillo obj = dod.getRandomPlatillo();
        Assert.assertNotNull("Data on demand for 'Platillo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Platillo' failed to provide an identifier", id);
        obj = platilloService.findPlatillo(id);
        Assert.assertNotNull("Find method for 'Platillo' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPlatillo(obj);
        Integer currentVersion = obj.getVersion();
        platilloRepository.flush();
        Assert.assertTrue("Version for 'Platillo' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdatePlatilloUpdate() {
        Platillo obj = dod.getRandomPlatillo();
        Assert.assertNotNull("Data on demand for 'Platillo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Platillo' failed to provide an identifier", id);
        obj = platilloService.findPlatillo(id);
        boolean modified =  dod.modifyPlatillo(obj);
        Integer currentVersion = obj.getVersion();
        Platillo merged = platilloService.updatePlatillo(obj);
        platilloRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Platillo' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSavePlatillo() {
        Assert.assertNotNull("Data on demand for 'Platillo' failed to initialize correctly", dod.getRandomPlatillo());
        Platillo obj = dod.getNewTransientPlatillo(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Platillo' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Platillo' identifier to be null", obj.getId());
        try {
            platilloService.savePlatillo(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        platilloRepository.flush();
        Assert.assertNotNull("Expected 'Platillo' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeletePlatillo() {
        Platillo obj = dod.getRandomPlatillo();
        Assert.assertNotNull("Data on demand for 'Platillo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Platillo' failed to provide an identifier", id);
        obj = platilloService.findPlatillo(id);
        platilloService.deletePlatillo(obj);
        platilloRepository.flush();
        Assert.assertNull("Failed to remove 'Platillo' with identifier '" + id + "'", platilloService.findPlatillo(id));
    }
}
