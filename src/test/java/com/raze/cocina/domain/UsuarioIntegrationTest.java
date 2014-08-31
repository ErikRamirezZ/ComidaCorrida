package com.raze.cocina.domain;
import com.raze.cocina.repository.UsuarioRepository;
import com.raze.cocina.service.UsuarioService;
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
@RooIntegrationTest(entity = Usuario.class)
public class UsuarioIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    UsuarioDataOnDemand dod;

	@Autowired
    UsuarioService usuarioService;

	@Autowired
    UsuarioRepository usuarioRepository;

	@Test
    public void testCountAllUsuarios() {
        Assert.assertNotNull("Data on demand for 'Usuario' failed to initialize correctly", dod.getRandomUsuario());
        long count = usuarioService.countAllUsuarios();
        Assert.assertTrue("Counter for 'Usuario' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindUsuario() {
        Usuario obj = dod.getRandomUsuario();
        Assert.assertNotNull("Data on demand for 'Usuario' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Usuario' failed to provide an identifier", id);
        obj = usuarioService.findUsuario(id);
        Assert.assertNotNull("Find method for 'Usuario' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Usuario' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllUsuarios() {
        Assert.assertNotNull("Data on demand for 'Usuario' failed to initialize correctly", dod.getRandomUsuario());
        long count = usuarioService.countAllUsuarios();
        Assert.assertTrue("Too expensive to perform a find all test for 'Usuario', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Usuario> result = usuarioService.findAllUsuarios();
        Assert.assertNotNull("Find all method for 'Usuario' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Usuario' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindUsuarioEntries() {
        Assert.assertNotNull("Data on demand for 'Usuario' failed to initialize correctly", dod.getRandomUsuario());
        long count = usuarioService.countAllUsuarios();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Usuario> result = usuarioService.findUsuarioEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Usuario' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Usuario' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Usuario obj = dod.getRandomUsuario();
        Assert.assertNotNull("Data on demand for 'Usuario' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Usuario' failed to provide an identifier", id);
        obj = usuarioService.findUsuario(id);
        Assert.assertNotNull("Find method for 'Usuario' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyUsuario(obj);
        Integer currentVersion = obj.getVersion();
        usuarioRepository.flush();
        Assert.assertTrue("Version for 'Usuario' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateUsuarioUpdate() {
        Usuario obj = dod.getRandomUsuario();
        Assert.assertNotNull("Data on demand for 'Usuario' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Usuario' failed to provide an identifier", id);
        obj = usuarioService.findUsuario(id);
        boolean modified =  dod.modifyUsuario(obj);
        Integer currentVersion = obj.getVersion();
        Usuario merged = usuarioService.updateUsuario(obj);
        usuarioRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Usuario' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveUsuario() {
        Assert.assertNotNull("Data on demand for 'Usuario' failed to initialize correctly", dod.getRandomUsuario());
        Usuario obj = dod.getNewTransientUsuario(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Usuario' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Usuario' identifier to be null", obj.getId());
        try {
            usuarioService.saveUsuario(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        usuarioRepository.flush();
        Assert.assertNotNull("Expected 'Usuario' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteUsuario() {
        Usuario obj = dod.getRandomUsuario();
        Assert.assertNotNull("Data on demand for 'Usuario' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Usuario' failed to provide an identifier", id);
        obj = usuarioService.findUsuario(id);
        usuarioService.deleteUsuario(obj);
        usuarioRepository.flush();
        Assert.assertNull("Failed to remove 'Usuario' with identifier '" + id + "'", usuarioService.findUsuario(id));
    }
}
