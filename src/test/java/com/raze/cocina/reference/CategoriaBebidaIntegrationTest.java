package com.raze.cocina.reference;
import com.raze.cocina.repository.CategoriaBebidaRepository;
import com.raze.cocina.service.CategoriaBebidaService;
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
@RooIntegrationTest(entity = CategoriaBebida.class)
public class CategoriaBebidaIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    CategoriaBebidaDataOnDemand dod;

	@Autowired
    CategoriaBebidaService categoriaBebidaService;

	@Autowired
    CategoriaBebidaRepository categoriaBebidaRepository;

	@Test
    public void testCountAllCategoriaBebidas() {
        Assert.assertNotNull("Data on demand for 'CategoriaBebida' failed to initialize correctly", dod.getRandomCategoriaBebida());
        long count = categoriaBebidaService.countAllCategoriaBebidas();
        Assert.assertTrue("Counter for 'CategoriaBebida' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindCategoriaBebida() {
        CategoriaBebida obj = dod.getRandomCategoriaBebida();
        Assert.assertNotNull("Data on demand for 'CategoriaBebida' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CategoriaBebida' failed to provide an identifier", id);
        obj = categoriaBebidaService.findCategoriaBebida(id);
        Assert.assertNotNull("Find method for 'CategoriaBebida' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'CategoriaBebida' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllCategoriaBebidas() {
        Assert.assertNotNull("Data on demand for 'CategoriaBebida' failed to initialize correctly", dod.getRandomCategoriaBebida());
        long count = categoriaBebidaService.countAllCategoriaBebidas();
        Assert.assertTrue("Too expensive to perform a find all test for 'CategoriaBebida', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<CategoriaBebida> result = categoriaBebidaService.findAllCategoriaBebidas();
        Assert.assertNotNull("Find all method for 'CategoriaBebida' illegally returned null", result);
        Assert.assertTrue("Find all method for 'CategoriaBebida' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindCategoriaBebidaEntries() {
        Assert.assertNotNull("Data on demand for 'CategoriaBebida' failed to initialize correctly", dod.getRandomCategoriaBebida());
        long count = categoriaBebidaService.countAllCategoriaBebidas();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<CategoriaBebida> result = categoriaBebidaService.findCategoriaBebidaEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'CategoriaBebida' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'CategoriaBebida' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        CategoriaBebida obj = dod.getRandomCategoriaBebida();
        Assert.assertNotNull("Data on demand for 'CategoriaBebida' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CategoriaBebida' failed to provide an identifier", id);
        obj = categoriaBebidaService.findCategoriaBebida(id);
        Assert.assertNotNull("Find method for 'CategoriaBebida' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyCategoriaBebida(obj);
        Integer currentVersion = obj.getVersion();
        categoriaBebidaRepository.flush();
        Assert.assertTrue("Version for 'CategoriaBebida' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateCategoriaBebidaUpdate() {
        CategoriaBebida obj = dod.getRandomCategoriaBebida();
        Assert.assertNotNull("Data on demand for 'CategoriaBebida' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CategoriaBebida' failed to provide an identifier", id);
        obj = categoriaBebidaService.findCategoriaBebida(id);
        boolean modified =  dod.modifyCategoriaBebida(obj);
        Integer currentVersion = obj.getVersion();
        CategoriaBebida merged = categoriaBebidaService.updateCategoriaBebida(obj);
        categoriaBebidaRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'CategoriaBebida' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveCategoriaBebida() {
        Assert.assertNotNull("Data on demand for 'CategoriaBebida' failed to initialize correctly", dod.getRandomCategoriaBebida());
        CategoriaBebida obj = dod.getNewTransientCategoriaBebida(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'CategoriaBebida' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'CategoriaBebida' identifier to be null", obj.getId());
        try {
            categoriaBebidaService.saveCategoriaBebida(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        categoriaBebidaRepository.flush();
        Assert.assertNotNull("Expected 'CategoriaBebida' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteCategoriaBebida() {
        CategoriaBebida obj = dod.getRandomCategoriaBebida();
        Assert.assertNotNull("Data on demand for 'CategoriaBebida' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CategoriaBebida' failed to provide an identifier", id);
        obj = categoriaBebidaService.findCategoriaBebida(id);
        categoriaBebidaService.deleteCategoriaBebida(obj);
        categoriaBebidaRepository.flush();
        Assert.assertNull("Failed to remove 'CategoriaBebida' with identifier '" + id + "'", categoriaBebidaService.findCategoriaBebida(id));
    }
}
