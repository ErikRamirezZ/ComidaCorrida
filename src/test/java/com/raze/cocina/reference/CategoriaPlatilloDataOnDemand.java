package com.raze.cocina.reference;
import com.raze.cocina.repository.CategoriaPlatilloRepository;
import com.raze.cocina.service.CategoriaPlatilloService;
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
@RooDataOnDemand(entity = CategoriaPlatillo.class)
public class CategoriaPlatilloDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<CategoriaPlatillo> data;

	@Autowired
    CategoriaPlatilloService categoriaPlatilloService;

	@Autowired
    CategoriaPlatilloRepository categoriaPlatilloRepository;

	public CategoriaPlatillo getNewTransientCategoriaPlatillo(int index) {
        CategoriaPlatillo obj = new CategoriaPlatillo();
        setDescripcion(obj, index);
        setNombre(obj, index);
        return obj;
    }

	public void setDescripcion(CategoriaPlatillo obj, int index) {
        String descripcion = "descripcion_" + index;
        obj.setDescripcion(descripcion);
    }

	public void setNombre(CategoriaPlatillo obj, int index) {
        String nombre = "nombre_" + index;
        obj.setNombre(nombre);
    }

	public CategoriaPlatillo getSpecificCategoriaPlatillo(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        CategoriaPlatillo obj = data.get(index);
        Long id = obj.getId();
        return categoriaPlatilloService.findCategoriaPlatillo(id);
    }

	public CategoriaPlatillo getRandomCategoriaPlatillo() {
        init();
        CategoriaPlatillo obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return categoriaPlatilloService.findCategoriaPlatillo(id);
    }

	public boolean modifyCategoriaPlatillo(CategoriaPlatillo obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = categoriaPlatilloService.findCategoriaPlatilloEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'CategoriaPlatillo' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<CategoriaPlatillo>();
        for (int i = 0; i < 10; i++) {
            CategoriaPlatillo obj = getNewTransientCategoriaPlatillo(i);
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
            data.add(obj);
        }
    }
}
