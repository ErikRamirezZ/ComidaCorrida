package com.raze.cocina.domain;
import com.raze.cocina.reference.CategoriaPlatilloDataOnDemand;
import com.raze.cocina.repository.PlatilloRepository;
import com.raze.cocina.service.PlatilloService;
import java.math.BigDecimal;
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
@RooDataOnDemand(entity = Platillo.class)
public class PlatilloDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Platillo> data;

	@Autowired
    CategoriaPlatilloDataOnDemand categoriaPlatilloDataOnDemand;

	@Autowired
    PlatilloService platilloService;

	@Autowired
    PlatilloRepository platilloRepository;

	public Platillo getNewTransientPlatillo(int index) {
        Platillo obj = new Platillo();
        setDescripcion(obj, index);
        setDisponibles(obj, index);
        setFoto(obj, index);
        setNombre(obj, index);
        setPrecio(obj, index);
        return obj;
    }

	public void setDescripcion(Platillo obj, int index) {
        String descripcion = "descripcion_" + index;
        if (descripcion.length() > 250) {
            descripcion = descripcion.substring(0, 250);
        }
        obj.setDescripcion(descripcion);
    }

	public void setDisponibles(Platillo obj, int index) {
        int disponibles = index;
        obj.setDisponibles(disponibles);
    }

	public void setFoto(Platillo obj, int index) {
        byte[] foto = String.valueOf(index).getBytes();
        obj.setFoto(foto);
    }

	public void setNombre(Platillo obj, int index) {
        String nombre = "nombre_" + index;
        if (nombre.length() > 50) {
            nombre = nombre.substring(0, 50);
        }
        obj.setNombre(nombre);
    }

	public void setPrecio(Platillo obj, int index) {
        BigDecimal precio = BigDecimal.valueOf(index);
        obj.setPrecio(precio);
    }

	public Platillo getSpecificPlatillo(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Platillo obj = data.get(index);
        Long id = obj.getId();
        return platilloService.findPlatillo(id);
    }

	public Platillo getRandomPlatillo() {
        init();
        Platillo obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return platilloService.findPlatillo(id);
    }

	public boolean modifyPlatillo(Platillo obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = platilloService.findPlatilloEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Platillo' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Platillo>();
        for (int i = 0; i < 10; i++) {
            Platillo obj = getNewTransientPlatillo(i);
            try {
                platilloService.savePlatillo(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            platilloRepository.flush();
            data.add(obj);
        }
    }
}
