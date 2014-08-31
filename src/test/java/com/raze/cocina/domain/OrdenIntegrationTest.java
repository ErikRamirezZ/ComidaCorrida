package com.raze.cocina.domain;
import com.raze.cocina.repository.OrdenRepository;
import com.raze.cocina.service.OrdenService;
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
@RooIntegrationTest(entity = Orden.class)
public class OrdenIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    OrdenDataOnDemand dod;

	@Autowired
    OrdenService ordenService;

	@Autowired
    OrdenRepository ordenRepository;

	@Test
    public void testCountAllOrdens() {
        Assert.assertNotNull("Data on demand for 'Orden' failed to initialize correctly", dod.getRandomOrden());
        long count = ordenService.countAllOrdens();
        Assert.assertTrue("Counter for 'Orden' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindOrden() {
        Orden obj = dod.getRandomOrden();
        Assert.assertNotNull("Data on demand for 'Orden' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Orden' failed to provide an identifier", id);
        obj = ordenService.findOrden(id);
        Assert.assertNotNull("Find method for 'Orden' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Orden' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllOrdens() {
        Assert.assertNotNull("Data on demand for 'Orden' failed to initialize correctly", dod.getRandomOrden());
        long count = ordenService.countAllOrdens();
        Assert.assertTrue("Too expensive to perform a find all test for 'Orden', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Orden> result = ordenService.findAllOrdens();
        Assert.assertNotNull("Find all method for 'Orden' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Orden' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindOrdenEntries() {
        Assert.assertNotNull("Data on demand for 'Orden' failed to initialize correctly", dod.getRandomOrden());
        long count = ordenService.countAllOrdens();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Orden> result = ordenService.findOrdenEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Orden' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Orden' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Orden obj = dod.getRandomOrden();
        Assert.assertNotNull("Data on demand for 'Orden' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Orden' failed to provide an identifier", id);
        obj = ordenService.findOrden(id);
        Assert.assertNotNull("Find method for 'Orden' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyOrden(obj);
        Integer currentVersion = obj.getVersion();
        ordenRepository.flush();
        Assert.assertTrue("Version for 'Orden' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateOrdenUpdate() {
        Orden obj = dod.getRandomOrden();
        Assert.assertNotNull("Data on demand for 'Orden' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Orden' failed to provide an identifier", id);
        obj = ordenService.findOrden(id);
        boolean modified =  dod.modifyOrden(obj);
        Integer currentVersion = obj.getVersion();
        Orden merged = ordenService.updateOrden(obj);
        ordenRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Orden' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveOrden() {
        Assert.assertNotNull("Data on demand for 'Orden' failed to initialize correctly", dod.getRandomOrden());
        Orden obj = dod.getNewTransientOrden(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Orden' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Orden' identifier to be null", obj.getId());
        try {
            ordenService.saveOrden(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        ordenRepository.flush();
        Assert.assertNotNull("Expected 'Orden' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteOrden() {
        Orden obj = dod.getRandomOrden();
        Assert.assertNotNull("Data on demand for 'Orden' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Orden' failed to provide an identifier", id);
        obj = ordenService.findOrden(id);
        ordenService.deleteOrden(obj);
        ordenRepository.flush();
        Assert.assertNull("Failed to remove 'Orden' with identifier '" + id + "'", ordenService.findOrden(id));
    }
}
