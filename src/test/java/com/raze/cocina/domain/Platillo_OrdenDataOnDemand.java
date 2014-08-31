package com.raze.cocina.domain;
import com.raze.cocina.repository.Platillo_OrdenRepository;
import com.raze.cocina.service.Platillo_OrdenService;
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
@RooDataOnDemand(entity = Platillo_Orden.class)
public class Platillo_OrdenDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Platillo_Orden> data;

	@Autowired
    PlatilloDataOnDemand platilloDataOnDemand;

	@Autowired
    Platillo_OrdenService platillo_OrdenService;

	@Autowired
    Platillo_OrdenRepository platillo_OrdenRepository;

	public Platillo_Orden getNewTransientPlatillo_Orden(int index) {
        Platillo_Orden obj = new Platillo_Orden();
        setCantidad(obj, index);
        setPlatillo(obj, index);
        setPrecioUnitario(obj, index);
        return obj;
    }

	public void setCantidad(Platillo_Orden obj, int index) {
        int cantidad = index;
        obj.setCantidad(cantidad);
    }

	public void setPlatillo(Platillo_Orden obj, int index) {
        Platillo platillo = platilloDataOnDemand.getRandomPlatillo();
        obj.setPlatillo(platillo);
    }

	public void setPrecioUnitario(Platillo_Orden obj, int index) {
        BigDecimal precioUnitario = BigDecimal.valueOf(index);
        obj.setPrecioUnitario(precioUnitario);
    }

	public Platillo_Orden getSpecificPlatillo_Orden(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Platillo_Orden obj = data.get(index);
        Long id = obj.getId();
        return platillo_OrdenService.findPlatillo_Orden(id);
    }

	public Platillo_Orden getRandomPlatillo_Orden() {
        init();
        Platillo_Orden obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return platillo_OrdenService.findPlatillo_Orden(id);
    }

	public boolean modifyPlatillo_Orden(Platillo_Orden obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = platillo_OrdenService.findPlatillo_OrdenEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Platillo_Orden' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Platillo_Orden>();
        for (int i = 0; i < 10; i++) {
            Platillo_Orden obj = getNewTransientPlatillo_Orden(i);
            try {
                platillo_OrdenService.savePlatillo_Orden(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            platillo_OrdenRepository.flush();
            data.add(obj);
        }
    }
}
