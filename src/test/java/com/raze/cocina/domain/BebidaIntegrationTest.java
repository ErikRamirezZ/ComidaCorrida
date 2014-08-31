package com.raze.cocina.domain;
import com.raze.cocina.repository.BebidaRepository;
import com.raze.cocina.service.BebidaService;
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
@RooIntegrationTest(entity = Bebida.class)
public class BebidaIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    BebidaDataOnDemand dod;

	@Autowired
    BebidaService bebidaService;

	@Autowired
    BebidaRepository bebidaRepository;

	@Test
    public void testCountAllBebidas() {
        Assert.assertNotNull("Data on demand for 'Bebida' failed to initialize correctly", dod.getRandomBebida());
        long count = bebidaService.countAllBebidas();
        Assert.assertTrue("Counter for 'Bebida' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindBebida() {
        Bebida obj = dod.getRandomBebida();
        Assert.assertNotNull("Data on demand for 'Bebida' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Bebida' failed to provide an identifier", id);
        obj = bebidaService.findBebida(id);
        Assert.assertNotNull("Find method for 'Bebida' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Bebida' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllBebidas() {
        Assert.assertNotNull("Data on demand for 'Bebida' failed to initialize correctly", dod.getRandomBebida());
        long count = bebidaService.countAllBebidas();
        Assert.assertTrue("Too expensive to perform a find all test for 'Bebida', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Bebida> result = bebidaService.findAllBebidas();
        Assert.assertNotNull("Find all method for 'Bebida' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Bebida' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindBebidaEntries() {
        Assert.assertNotNull("Data on demand for 'Bebida' failed to initialize correctly", dod.getRandomBebida());
        long count = bebidaService.countAllBebidas();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Bebida> result = bebidaService.findBebidaEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Bebida' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Bebida' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Bebida obj = dod.getRandomBebida();
        Assert.assertNotNull("Data on demand for 'Bebida' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Bebida' failed to provide an identifier", id);
        obj = bebidaService.findBebida(id);
        Assert.assertNotNull("Find method for 'Bebida' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyBebida(obj);
        Integer currentVersion = obj.getVersion();
        bebidaRepository.flush();
        Assert.assertTrue("Version for 'Bebida' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateBebidaUpdate() {
        Bebida obj = dod.getRandomBebida();
        Assert.assertNotNull("Data on demand for 'Bebida' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Bebida' failed to provide an identifier", id);
        obj = bebidaService.findBebida(id);
        boolean modified =  dod.modifyBebida(obj);
        Integer currentVersion = obj.getVersion();
        Bebida merged = bebidaService.updateBebida(obj);
        bebidaRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Bebida' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveBebida() {
        Assert.assertNotNull("Data on demand for 'Bebida' failed to initialize correctly", dod.getRandomBebida());
        Bebida obj = dod.getNewTransientBebida(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Bebida' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Bebida' identifier to be null", obj.getId());
        try {
            bebidaService.saveBebida(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        bebidaRepository.flush();
        Assert.assertNotNull("Expected 'Bebida' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteBebida() {
        Bebida obj = dod.getRandomBebida();
        Assert.assertNotNull("Data on demand for 'Bebida' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Bebida' failed to provide an identifier", id);
        obj = bebidaService.findBebida(id);
        bebidaService.deleteBebida(obj);
        bebidaRepository.flush();
        Assert.assertNull("Failed to remove 'Bebida' with identifier '" + id + "'", bebidaService.findBebida(id));
    }
}
