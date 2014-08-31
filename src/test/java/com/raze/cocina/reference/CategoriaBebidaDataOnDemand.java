package com.raze.cocina.reference;
import com.raze.cocina.repository.CategoriaBebidaRepository;
import com.raze.cocina.service.CategoriaBebidaService;
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

@Configurable
@Component
@RooDataOnDemand(entity = CategoriaBebida.class)
public class CategoriaBebidaDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<CategoriaBebida> data;

	@Autowired
    CategoriaBebidaService categoriaBebidaService;

	@Autowired
    CategoriaBebidaRepository categoriaBebidaRepository;

	public CategoriaBebida getNewTransientCategoriaBebida(int index) {
        CategoriaBebida obj = new CategoriaBebida();
        setDescripcion(obj, index);
        setNombre(obj, index);
        return obj;
    }

	public void setDescripcion(CategoriaBebida obj, int index) {
        String descripcion = "descripcion_" + index;
        obj.setDescripcion(descripcion);
    }

	public void setNombre(CategoriaBebida obj, int index) {
        String nombre = "nombre_" + index;
        obj.setNombre(nombre);
    }

	public CategoriaBebida getSpecificCategoriaBebida(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        CategoriaBebida obj = data.get(index);
        Long id = obj.getId();
        return categoriaBebidaService.findCategoriaBebida(id);
    }

	public CategoriaBebida getRandomCategoriaBebida() {
        init();
        CategoriaBebida obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return categoriaBebidaService.findCategoriaBebida(id);
    }

	public boolean modifyCategoriaBebida(CategoriaBebida obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = categoriaBebidaService.findCategoriaBebidaEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'CategoriaBebida' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<CategoriaBebida>();
        for (int i = 0; i < 10; i++) {
            CategoriaBebida obj = getNewTransientCategoriaBebida(i);
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
            data.add(obj);
        }
    }
}
