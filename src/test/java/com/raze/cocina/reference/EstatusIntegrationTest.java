package com.raze.cocina.reference;
import com.raze.cocina.repository.EstatusRepository;
import com.raze.cocina.service.EstatusService;
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
@RooIntegrationTest(entity = Estatus.class)
public class EstatusIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    EstatusDataOnDemand dod;

	@Autowired
    EstatusService estatusService;

	@Autowired
    EstatusRepository estatusRepository;

	@Test
    public void testCountAllEstatuses() {
        Assert.assertNotNull("Data on demand for 'Estatus' failed to initialize correctly", dod.getRandomEstatus());
        long count = estatusService.countAllEstatuses();
        Assert.assertTrue("Counter for 'Estatus' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindEstatus() {
        Estatus obj = dod.getRandomEstatus();
        Assert.assertNotNull("Data on demand for 'Estatus' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Estatus' failed to provide an identifier", id);
        obj = estatusService.findEstatus(id);
        Assert.assertNotNull("Find method for 'Estatus' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Estatus' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllEstatuses() {
        Assert.assertNotNull("Data on demand for 'Estatus' failed to initialize correctly", dod.getRandomEstatus());
        long count = estatusService.countAllEstatuses();
        Assert.assertTrue("Too expensive to perform a find all test for 'Estatus', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Estatus> result = estatusService.findAllEstatuses();
        Assert.assertNotNull("Find all method for 'Estatus' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Estatus' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEstatusEntries() {
        Assert.assertNotNull("Data on demand for 'Estatus' failed to initialize correctly", dod.getRandomEstatus());
        long count = estatusService.countAllEstatuses();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Estatus> result = estatusService.findEstatusEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Estatus' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Estatus' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Estatus obj = dod.getRandomEstatus();
        Assert.assertNotNull("Data on demand for 'Estatus' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Estatus' failed to provide an identifier", id);
        obj = estatusService.findEstatus(id);
        Assert.assertNotNull("Find method for 'Estatus' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyEstatus(obj);
        Integer currentVersion = obj.getVersion();
        estatusRepository.flush();
        Assert.assertTrue("Version for 'Estatus' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateEstatusUpdate() {
        Estatus obj = dod.getRandomEstatus();
        Assert.assertNotNull("Data on demand for 'Estatus' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Estatus' failed to provide an identifier", id);
        obj = estatusService.findEstatus(id);
        boolean modified =  dod.modifyEstatus(obj);
        Integer currentVersion = obj.getVersion();
        Estatus merged = estatusService.updateEstatus(obj);
        estatusRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Estatus' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveEstatus() {
        Assert.assertNotNull("Data on demand for 'Estatus' failed to initialize correctly", dod.getRandomEstatus());
        Estatus obj = dod.getNewTransientEstatus(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Estatus' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Estatus' identifier to be null", obj.getId());
        try {
            estatusService.saveEstatus(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        estatusRepository.flush();
        Assert.assertNotNull("Expected 'Estatus' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteEstatus() {
        Estatus obj = dod.getRandomEstatus();
        Assert.assertNotNull("Data on demand for 'Estatus' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Estatus' failed to provide an identifier", id);
        obj = estatusService.findEstatus(id);
        estatusService.deleteEstatus(obj);
        estatusRepository.flush();
        Assert.assertNull("Failed to remove 'Estatus' with identifier '" + id + "'", estatusService.findEstatus(id));
    }
}
