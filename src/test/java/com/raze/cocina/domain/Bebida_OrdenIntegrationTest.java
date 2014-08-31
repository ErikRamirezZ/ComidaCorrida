package com.raze.cocina.domain;
import com.raze.cocina.repository.Bebida_OrdenRepository;
import com.raze.cocina.service.Bebida_OrdenService;
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
@RooIntegrationTest(entity = Bebida_Orden.class)
public class Bebida_OrdenIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    Bebida_OrdenDataOnDemand dod;

	@Autowired
    Bebida_OrdenService bebida_OrdenService;

	@Autowired
    Bebida_OrdenRepository bebida_OrdenRepository;

	@Test
    public void testCountAllBebida_Ordens() {
        Assert.assertNotNull("Data on demand for 'Bebida_Orden' failed to initialize correctly", dod.getRandomBebida_Orden());
        long count = bebida_OrdenService.countAllBebida_Ordens();
        Assert.assertTrue("Counter for 'Bebida_Orden' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindBebida_Orden() {
        Bebida_Orden obj = dod.getRandomBebida_Orden();
        Assert.assertNotNull("Data on demand for 'Bebida_Orden' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Bebida_Orden' failed to provide an identifier", id);
        obj = bebida_OrdenService.findBebida_Orden(id);
        Assert.assertNotNull("Find method for 'Bebida_Orden' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Bebida_Orden' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllBebida_Ordens() {
        Assert.assertNotNull("Data on demand for 'Bebida_Orden' failed to initialize correctly", dod.getRandomBebida_Orden());
        long count = bebida_OrdenService.countAllBebida_Ordens();
        Assert.assertTrue("Too expensive to perform a find all test for 'Bebida_Orden', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Bebida_Orden> result = bebida_OrdenService.findAllBebida_Ordens();
        Assert.assertNotNull("Find all method for 'Bebida_Orden' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Bebida_Orden' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindBebida_OrdenEntries() {
        Assert.assertNotNull("Data on demand for 'Bebida_Orden' failed to initialize correctly", dod.getRandomBebida_Orden());
        long count = bebida_OrdenService.countAllBebida_Ordens();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Bebida_Orden> result = bebida_OrdenService.findBebida_OrdenEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Bebida_Orden' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Bebida_Orden' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Bebida_Orden obj = dod.getRandomBebida_Orden();
        Assert.assertNotNull("Data on demand for 'Bebida_Orden' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Bebida_Orden' failed to provide an identifier", id);
        obj = bebida_OrdenService.findBebida_Orden(id);
        Assert.assertNotNull("Find method for 'Bebida_Orden' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyBebida_Orden(obj);
        Integer currentVersion = obj.getVersion();
        bebida_OrdenRepository.flush();
        Assert.assertTrue("Version for 'Bebida_Orden' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateBebida_OrdenUpdate() {
        Bebida_Orden obj = dod.getRandomBebida_Orden();
        Assert.assertNotNull("Data on demand for 'Bebida_Orden' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Bebida_Orden' failed to provide an identifier", id);
        obj = bebida_OrdenService.findBebida_Orden(id);
        boolean modified =  dod.modifyBebida_Orden(obj);
        Integer currentVersion = obj.getVersion();
        Bebida_Orden merged = bebida_OrdenService.updateBebida_Orden(obj);
        bebida_OrdenRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Bebida_Orden' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveBebida_Orden() {
        Assert.assertNotNull("Data on demand for 'Bebida_Orden' failed to initialize correctly", dod.getRandomBebida_Orden());
        Bebida_Orden obj = dod.getNewTransientBebida_Orden(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Bebida_Orden' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Bebida_Orden' identifier to be null", obj.getId());
        try {
            bebida_OrdenService.saveBebida_Orden(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        bebida_OrdenRepository.flush();
        Assert.assertNotNull("Expected 'Bebida_Orden' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteBebida_Orden() {
        Bebida_Orden obj = dod.getRandomBebida_Orden();
        Assert.assertNotNull("Data on demand for 'Bebida_Orden' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Bebida_Orden' failed to provide an identifier", id);
        obj = bebida_OrdenService.findBebida_Orden(id);
        bebida_OrdenService.deleteBebida_Orden(obj);
        bebida_OrdenRepository.flush();
        Assert.assertNull("Failed to remove 'Bebida_Orden' with identifier '" + id + "'", bebida_OrdenService.findBebida_Orden(id));
    }
}
