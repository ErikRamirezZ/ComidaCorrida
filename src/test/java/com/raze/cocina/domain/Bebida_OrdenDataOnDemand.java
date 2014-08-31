package com.raze.cocina.domain;
import com.raze.cocina.repository.Bebida_OrdenRepository;
import com.raze.cocina.service.Bebida_OrdenService;
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
@RooDataOnDemand(entity = Bebida_Orden.class)
public class Bebida_OrdenDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Bebida_Orden> data;

	@Autowired
    BebidaDataOnDemand bebidaDataOnDemand;

	@Autowired
    Bebida_OrdenService bebida_OrdenService;

	@Autowired
    Bebida_OrdenRepository bebida_OrdenRepository;

	public Bebida_Orden getNewTransientBebida_Orden(int index) {
        Bebida_Orden obj = new Bebida_Orden();
        setCantidad(obj, index);
        setPrecioUnitario(obj, index);
        return obj;
    }

	public void setCantidad(Bebida_Orden obj, int index) {
        int cantidad = index;
        obj.setCantidad(cantidad);
    }

	public void setPrecioUnitario(Bebida_Orden obj, int index) {
        BigDecimal precioUnitario = BigDecimal.valueOf(index);
        obj.setPrecioUnitario(precioUnitario);
    }

	public Bebida_Orden getSpecificBebida_Orden(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Bebida_Orden obj = data.get(index);
        Long id = obj.getId();
        return bebida_OrdenService.findBebida_Orden(id);
    }

	public Bebida_Orden getRandomBebida_Orden() {
        init();
        Bebida_Orden obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return bebida_OrdenService.findBebida_Orden(id);
    }

	public boolean modifyBebida_Orden(Bebida_Orden obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = bebida_OrdenService.findBebida_OrdenEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Bebida_Orden' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Bebida_Orden>();
        for (int i = 0; i < 10; i++) {
            Bebida_Orden obj = getNewTransientBebida_Orden(i);
            try {
                bebida_OrdenService.saveBebida_Orden(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            bebida_OrdenRepository.flush();
            data.add(obj);
        }
    }
}
