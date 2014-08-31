package com.raze.cocina.domain;
import com.raze.cocina.repository.Platillo_OrdenRepository;
import com.raze.cocina.service.Platillo_OrdenService;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
@RooIntegrationTest(entity = Platillo_Orden.class)
public class Platillo_OrdenIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    Platillo_OrdenDataOnDemand dod;

	@Autowired
    Platillo_OrdenService platillo_OrdenService;

	@Autowired
    Platillo_OrdenRepository platillo_OrdenRepository;

	@Test
    public void testCountAllPlatillo_Ordens() {
        Assert.assertNotNull("Data on demand for 'Platillo_Orden' failed to initialize correctly", dod.getRandomPlatillo_Orden());
        long count = platillo_OrdenService.countAllPlatillo_Ordens();
        Assert.assertTrue("Counter for 'Platillo_Orden' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindPlatillo_Orden() {
        Platillo_Orden obj = dod.getRandomPlatillo_Orden();
        Assert.assertNotNull("Data on demand for 'Platillo_Orden' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Platillo_Orden' failed to provide an identifier", id);
        obj = platillo_OrdenService.findPlatillo_Orden(id);
        Assert.assertNotNull("Find method for 'Platillo_Orden' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Platillo_Orden' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllPlatillo_Ordens() {
        Assert.assertNotNull("Data on demand for 'Platillo_Orden' failed to initialize correctly", dod.getRandomPlatillo_Orden());
        long count = platillo_OrdenService.countAllPlatillo_Ordens();
        Assert.assertTrue("Too expensive to perform a find all test for 'Platillo_Orden', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Platillo_Orden> result = platillo_OrdenService.findAllPlatillo_Ordens();
        Assert.assertNotNull("Find all method for 'Platillo_Orden' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Platillo_Orden' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindPlatillo_OrdenEntries() {
        Assert.assertNotNull("Data on demand for 'Platillo_Orden' failed to initialize correctly", dod.getRandomPlatillo_Orden());
        long count = platillo_OrdenService.countAllPlatillo_Ordens();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Platillo_Orden> result = platillo_OrdenService.findPlatillo_OrdenEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Platillo_Orden' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Platillo_Orden' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Platillo_Orden obj = dod.getRandomPlatillo_Orden();
        Assert.assertNotNull("Data on demand for 'Platillo_Orden' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Platillo_Orden' failed to provide an identifier", id);
        obj = platillo_OrdenService.findPlatillo_Orden(id);
        Assert.assertNotNull("Find method for 'Platillo_Orden' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPlatillo_Orden(obj);
        Integer currentVersion = obj.getVersion();
        platillo_OrdenRepository.flush();
        Assert.assertTrue("Version for 'Platillo_Orden' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdatePlatillo_OrdenUpdate() {
        Platillo_Orden obj = dod.getRandomPlatillo_Orden();
        Assert.assertNotNull("Data on demand for 'Platillo_Orden' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Platillo_Orden' failed to provide an identifier", id);
        obj = platillo_OrdenService.findPlatillo_Orden(id);
        boolean modified =  dod.modifyPlatillo_Orden(obj);
        Integer currentVersion = obj.getVersion();
        Platillo_Orden merged = platillo_OrdenService.updatePlatillo_Orden(obj);
        platillo_OrdenRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Platillo_Orden' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSavePlatillo_Orden() {
        Assert.assertNotNull("Data on demand for 'Platillo_Orden' failed to initialize correctly", dod.getRandomPlatillo_Orden());
        Platillo_Orden obj = dod.getNewTransientPlatillo_Orden(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Platillo_Orden' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Platillo_Orden' identifier to be null", obj.getId());
        try {
            platillo_OrdenService.savePlatillo_Orden(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        platillo_OrdenRepository.flush();
        Assert.assertNotNull("Expected 'Platillo_Orden' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeletePlatillo_Orden() {
        Platillo_Orden obj = dod.getRandomPlatillo_Orden();
        Assert.assertNotNull("Data on demand for 'Platillo_Orden' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Platillo_Orden' failed to provide an identifier", id);
        obj = platillo_OrdenService.findPlatillo_Orden(id);
        platillo_OrdenService.deletePlatillo_Orden(obj);
        platillo_OrdenRepository.flush();
        Assert.assertNull("Failed to remove 'Platillo_Orden' with identifier '" + id + "'", platillo_OrdenService.findPlatillo_Orden(id));
    }
}
