package com.raze.cocina.domain;
import com.raze.cocina.repository.UsuarioRepository;
import com.raze.cocina.service.UsuarioService;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Component
@Configurable
@RooDataOnDemand(entity = Usuario.class)
public class UsuarioDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Usuario> data;

	@Autowired
    UsuarioService usuarioService;

	@Autowired
    UsuarioRepository usuarioRepository;

	public Usuario getNewTransientUsuario(int index) {
        Usuario obj = new Usuario();
        setDomicilio(obj, index);
        setNombre(obj, index);
        setObservaciones(obj, index);
        setTelefono(obj, index);
        return obj;
    }

	public void setDomicilio(Usuario obj, int index) {
        String domicilio = "domicilio_" + index;
        if (domicilio.length() > 250) {
            domicilio = domicilio.substring(0, 250);
        }
        obj.setDomicilio(domicilio);
    }

	public void setNombre(Usuario obj, int index) {
        String nombre = "nombre_" + index;
        if (nombre.length() > 250) {
            nombre = nombre.substring(0, 250);
        }
        obj.setNombre(nombre);
    }

	public void setObservaciones(Usuario obj, int index) {
        String observaciones = "observaciones_" + index;
        if (observaciones.length() > 250) {
            observaciones = observaciones.substring(0, 250);
        }
        obj.setObservaciones(observaciones);
    }

	public void setTelefono(Usuario obj, int index) {
        String telefono = "telefono_" + index;
        if (telefono.length() > 20) {
            telefono = telefono.substring(0, 20);
        }
        obj.setTelefono(telefono);
    }

	public Usuario getSpecificUsuario(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Usuario obj = data.get(index);
        Long id = obj.getId();
        return usuarioService.findUsuario(id);
    }

	public Usuario getRandomUsuario() {
        init();
        Usuario obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return usuarioService.findUsuario(id);
    }

	public boolean modifyUsuario(Usuario obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = usuarioService.findUsuarioEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Usuario' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Usuario>();
        for (int i = 0; i < 10; i++) {
            Usuario obj = getNewTransientUsuario(i);
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
            data.add(obj);
        }
    }
}
