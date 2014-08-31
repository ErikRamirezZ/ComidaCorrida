package com.raze.cocina.domain;
import com.raze.cocina.repository.AgregadoRepository;
import com.raze.cocina.service.AgregadoService;
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
@RooIntegrationTest(entity = Agregado.class)
public class AgregadoIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    AgregadoDataOnDemand dod;

	@Autowired
    AgregadoService agregadoService;

	@Autowired
    AgregadoRepository agregadoRepository;

	@Test
    public void testCountAllAgregadoes() {
        Assert.assertNotNull("Data on demand for 'Agregado' failed to initialize correctly", dod.getRandomAgregado());
        long count = agregadoService.countAllAgregadoes();
        Assert.assertTrue("Counter for 'Agregado' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindAgregado() {
        Agregado obj = dod.getRandomAgregado();
        Assert.assertNotNull("Data on demand for 'Agregado' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Agregado' failed to provide an identifier", id);
        obj = agregadoService.findAgregado(id);
        Assert.assertNotNull("Find method for 'Agregado' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Agregado' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllAgregadoes() {
        Assert.assertNotNull("Data on demand for 'Agregado' failed to initialize correctly", dod.getRandomAgregado());
        long count = agregadoService.countAllAgregadoes();
        Assert.assertTrue("Too expensive to perform a find all test for 'Agregado', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Agregado> result = agregadoService.findAllAgregadoes();
        Assert.assertNotNull("Find all method for 'Agregado' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Agregado' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindAgregadoEntries() {
        Assert.assertNotNull("Data on demand for 'Agregado' failed to initialize correctly", dod.getRandomAgregado());
        long count = agregadoService.countAllAgregadoes();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Agregado> result = agregadoService.findAgregadoEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Agregado' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Agregado' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Agregado obj = dod.getRandomAgregado();
        Assert.assertNotNull("Data on demand for 'Agregado' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Agregado' failed to provide an identifier", id);
        obj = agregadoService.findAgregado(id);
        Assert.assertNotNull("Find method for 'Agregado' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyAgregado(obj);
        Integer currentVersion = obj.getVersion();
        agregadoRepository.flush();
        Assert.assertTrue("Version for 'Agregado' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateAgregadoUpdate() {
        Agregado obj = dod.getRandomAgregado();
        Assert.assertNotNull("Data on demand for 'Agregado' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Agregado' failed to provide an identifier", id);
        obj = agregadoService.findAgregado(id);
        boolean modified =  dod.modifyAgregado(obj);
        Integer currentVersion = obj.getVersion();
        Agregado merged = agregadoService.updateAgregado(obj);
        agregadoRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Agregado' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveAgregado() {
        Assert.assertNotNull("Data on demand for 'Agregado' failed to initialize correctly", dod.getRandomAgregado());
        Agregado obj = dod.getNewTransientAgregado(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Agregado' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Agregado' identifier to be null", obj.getId());
        try {
            agregadoService.saveAgregado(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        agregadoRepository.flush();
        Assert.assertNotNull("Expected 'Agregado' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteAgregado() {
        Agregado obj = dod.getRandomAgregado();
        Assert.assertNotNull("Data on demand for 'Agregado' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Agregado' failed to provide an identifier", id);
        obj = agregadoService.findAgregado(id);
        agregadoService.deleteAgregado(obj);
        agregadoRepository.flush();
        Assert.assertNull("Failed to remove 'Agregado' with identifier '" + id + "'", agregadoService.findAgregado(id));
    }
}
