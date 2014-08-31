package com.raze.cocina.domain;
import com.raze.cocina.reference.EstatusDataOnDemand;
import com.raze.cocina.repository.OrdenRepository;
import com.raze.cocina.service.OrdenService;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
@RooDataOnDemand(entity = Orden.class)
public class OrdenDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Orden> data;

	@Autowired
    EstatusDataOnDemand estatusDataOnDemand;

	@Autowired
    UsuarioDataOnDemand usuarioDataOnDemand;

	@Autowired
    OrdenService ordenService;

	@Autowired
    OrdenRepository ordenRepository;

	public Orden getNewTransientOrden(int index) {
        Orden obj = new Orden();
        setDescuento(obj, index);
        setFecha(obj, index);
        setObservaciones(obj, index);
        return obj;
    }

	public void setDescuento(Orden obj, int index) {
        int descuento = index;
        if (descuento > 100) {
            descuento = 100;
        }
        obj.setDescuento(descuento);
    }

	public void setFecha(Orden obj, int index) {
        Date fecha = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setFecha(fecha);
    }

	public void setObservaciones(Orden obj, int index) {
        String observaciones = "observaciones_" + index;
        if (observaciones.length() > 250) {
            observaciones = observaciones.substring(0, 250);
        }
        obj.setObservaciones(observaciones);
    }

	public Orden getSpecificOrden(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Orden obj = data.get(index);
        Long id = obj.getId();
        return ordenService.findOrden(id);
    }

	public Orden getRandomOrden() {
        init();
        Orden obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return ordenService.findOrden(id);
    }

	public boolean modifyOrden(Orden obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = ordenService.findOrdenEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Orden' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Orden>();
        for (int i = 0; i < 10; i++) {
            Orden obj = getNewTransientOrden(i);
            try {
                ordenService.saveOrden(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            ordenRepository.flush();
            data.add(obj);
        }
    }
}
