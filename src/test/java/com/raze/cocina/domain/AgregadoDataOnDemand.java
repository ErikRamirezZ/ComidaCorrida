package com.raze.cocina.domain;
import com.raze.cocina.repository.AgregadoRepository;
import com.raze.cocina.service.AgregadoService;
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
@RooDataOnDemand(entity = Agregado.class)
public class AgregadoDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Agregado> data;

	@Autowired
    AgregadoService agregadoService;

	@Autowired
    AgregadoRepository agregadoRepository;

	public Agregado getNewTransientAgregado(int index) {
        Agregado obj = new Agregado();
        setDescripcion(obj, index);
        setDisponibles(obj, index);
        setNombre(obj, index);
        return obj;
    }

	public void setDescripcion(Agregado obj, int index) {
        String descripcion = "descripcion_" + index;
        if (descripcion.length() > 100) {
            descripcion = descripcion.substring(0, 100);
        }
        obj.setDescripcion(descripcion);
    }

	public void setDisponibles(Agregado obj, int index) {
        int disponibles = index;
        obj.setDisponibles(disponibles);
    }

	public void setNombre(Agregado obj, int index) {
        String nombre = "nombre_" + index;
        if (nombre.length() > 50) {
            nombre = nombre.substring(0, 50);
        }
        obj.setNombre(nombre);
    }

	public Agregado getSpecificAgregado(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Agregado obj = data.get(index);
        Long id = obj.getId();
        return agregadoService.findAgregado(id);
    }

	public Agregado getRandomAgregado() {
        init();
        Agregado obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return agregadoService.findAgregado(id);
    }

	public boolean modifyAgregado(Agregado obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = agregadoService.findAgregadoEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Agregado' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Agregado>();
        for (int i = 0; i < 10; i++) {
            Agregado obj = getNewTransientAgregado(i);
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
            data.add(obj);
        }
    }
}
