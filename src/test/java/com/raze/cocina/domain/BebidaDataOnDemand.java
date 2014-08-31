package com.raze.cocina.domain;
import com.raze.cocina.reference.CategoriaBebidaDataOnDemand;
import com.raze.cocina.repository.BebidaRepository;
import com.raze.cocina.service.BebidaService;
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
@RooDataOnDemand(entity = Bebida.class)
public class BebidaDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Bebida> data;

	@Autowired
    CategoriaBebidaDataOnDemand categoriaBebidaDataOnDemand;

	@Autowired
    BebidaService bebidaService;

	@Autowired
    BebidaRepository bebidaRepository;

	public Bebida getNewTransientBebida(int index) {
        Bebida obj = new Bebida();
        setDescripcion(obj, index);
        setDisponibles(obj, index);
        setFoto(obj, index);
        setNombre(obj, index);
        setPrecio(obj, index);
        return obj;
    }

	public void setDescripcion(Bebida obj, int index) {
        String descripcion = "descripcion_" + index;
        if (descripcion.length() > 250) {
            descripcion = descripcion.substring(0, 250);
        }
        obj.setDescripcion(descripcion);
    }

	public void setDisponibles(Bebida obj, int index) {
        int disponibles = index;
        obj.setDisponibles(disponibles);
    }

	public void setFoto(Bebida obj, int index) {
        byte[] foto = String.valueOf(index).getBytes();
        obj.setFoto(foto);
    }

	public void setNombre(Bebida obj, int index) {
        String nombre = "nombre_" + index;
        if (nombre.length() > 50) {
            nombre = nombre.substring(0, 50);
        }
        obj.setNombre(nombre);
    }

	public void setPrecio(Bebida obj, int index) {
        BigDecimal precio = BigDecimal.valueOf(index);
        obj.setPrecio(precio);
    }

	public Bebida getSpecificBebida(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Bebida obj = data.get(index);
        Long id = obj.getId();
        return bebidaService.findBebida(id);
    }

	public Bebida getRandomBebida() {
        init();
        Bebida obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return bebidaService.findBebida(id);
    }

	public boolean modifyBebida(Bebida obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = bebidaService.findBebidaEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Bebida' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Bebida>();
        for (int i = 0; i < 10; i++) {
            Bebida obj = getNewTransientBebida(i);
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
            data.add(obj);
        }
    }
}
