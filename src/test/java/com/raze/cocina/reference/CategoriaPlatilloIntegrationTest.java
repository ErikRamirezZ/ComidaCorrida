package com.raze.cocina.reference;
import com.raze.cocina.repository.CategoriaPlatilloRepository;
import com.raze.cocina.service.CategoriaPlatilloService;
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
@RooIntegrationTest(entity = CategoriaPlatillo.class)
public class CategoriaPlatilloIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    CategoriaPlatilloDataOnDemand dod;

	@Autowired
    CategoriaPlatilloService categoriaPlatilloService;

	@Autowired
    CategoriaPlatilloRepository categoriaPlatilloRepository;

	@Test
    public void testCountAllCategoriaPlatilloes() {
        Assert.assertNotNull("Data on demand for 'CategoriaPlatillo' failed to initialize correctly", dod.getRandomCategoriaPlatillo());
        long count = categoriaPlatilloService.countAllCategoriaPlatilloes();
        Assert.assertTrue("Counter for 'CategoriaPlatillo' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindCategoriaPlatillo() {
        CategoriaPlatillo obj = dod.getRandomCategoriaPlatillo();
        Assert.assertNotNull("Data on demand for 'CategoriaPlatillo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CategoriaPlatillo' failed to provide an identifier", id);
        obj = categoriaPlatilloService.findCategoriaPlatillo(id);
        Assert.assertNotNull("Find method for 'CategoriaPlatillo' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'CategoriaPlatillo' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllCategoriaPlatilloes() {
        Assert.assertNotNull("Data on demand for 'CategoriaPlatillo' failed to initialize correctly", dod.getRandomCategoriaPlatillo());
        long count = categoriaPlatilloService.countAllCategoriaPlatilloes();
        Assert.assertTrue("Too expensive to perform a find all test for 'CategoriaPlatillo', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<CategoriaPlatillo> result = categoriaPlatilloService.findAllCategoriaPlatilloes();
        Assert.assertNotNull("Find all method for 'CategoriaPlatillo' illegally returned null", result);
        Assert.assertTrue("Find all method for 'CategoriaPlatillo' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindCategoriaPlatilloEntries() {
        Assert.assertNotNull("Data on demand for 'CategoriaPlatillo' failed to initialize correctly", dod.getRandomCategoriaPlatillo());
        long count = categoriaPlatilloService.countAllCategoriaPlatilloes();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<CategoriaPlatillo> result = categoriaPlatilloService.findCategoriaPlatilloEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'CategoriaPlatillo' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'CategoriaPlatillo' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        CategoriaPlatillo obj = dod.getRandomCategoriaPlatillo();
        Assert.assertNotNull("Data on demand for 'CategoriaPlatillo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CategoriaPlatillo' failed to provide an identifier", id);
        obj = categoriaPlatilloService.findCategoriaPlatillo(id);
        Assert.assertNotNull("Find method for 'CategoriaPlatillo' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyCategoriaPlatillo(obj);
        Integer currentVersion = obj.getVersion();
        categoriaPlatilloRepository.flush();
        Assert.assertTrue("Version for 'CategoriaPlatillo' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateCategoriaPlatilloUpdate() {
        CategoriaPlatillo obj = dod.getRandomCategoriaPlatillo();
        Assert.assertNotNull("Data on demand for 'CategoriaPlatillo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CategoriaPlatillo' failed to provide an identifier", id);
        obj = categoriaPlatilloService.findCategoriaPlatillo(id);
        boolean modified =  dod.modifyCategoriaPlatillo(obj);
        Integer currentVersion = obj.getVersion();
        CategoriaPlatillo merged = categoriaPlatilloService.updateCategoriaPlatillo(obj);
        categoriaPlatilloRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'CategoriaPlatillo' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveCategoriaPlatillo() {
        Assert.assertNotNull("Data on demand for 'CategoriaPlatillo' failed to initialize correctly", dod.getRandomCategoriaPlatillo());
        CategoriaPlatillo obj = dod.getNewTransientCategoriaPlatillo(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'CategoriaPlatillo' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'CategoriaPlatillo' identifier to be null", obj.getId());
        try {
            categoriaPlatilloService.saveCategoriaPlatillo(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        categoriaPlatilloRepository.flush();
        Assert.assertNotNull("Expected 'CategoriaPlatillo' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteCategoriaPlatillo() {
        CategoriaPlatillo obj = dod.getRandomCategoriaPlatillo();
        Assert.assertNotNull("Data on demand for 'CategoriaPlatillo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CategoriaPlatillo' failed to provide an identifier", id);
        obj = categoriaPlatilloService.findCategoriaPlatillo(id);
        categoriaPlatilloService.deleteCategoriaPlatillo(obj);
        categoriaPlatilloRepository.flush();
        Assert.assertNull("Failed to remove 'CategoriaPlatillo' with identifier '" + id + "'", categoriaPlatilloService.findCategoriaPlatillo(id));
    }
}
