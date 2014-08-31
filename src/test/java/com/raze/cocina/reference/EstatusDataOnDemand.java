package com.raze.cocina.reference;
import com.raze.cocina.repository.EstatusRepository;
import com.raze.cocina.service.EstatusService;
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
@RooDataOnDemand(entity = Estatus.class)
public class EstatusDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Estatus> data;

	@Autowired
    EstatusService estatusService;

	@Autowired
    EstatusRepository estatusRepository;

	public Estatus getNewTransientEstatus(int index) {
        Estatus obj = new Estatus();
        setDescripcion(obj, index);
        setEstatus(obj, index);
        return obj;
    }

	public void setDescripcion(Estatus obj, int index) {
        String descripcion = "descripcion_" + index;
        obj.setDescripcion(descripcion);
    }

	public void setEstatus(Estatus obj, int index) {
        String estatus = "estatus_" + index;
        obj.setEstatus(estatus);
    }

	public Estatus getSpecificEstatus(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Estatus obj = data.get(index);
        Long id = obj.getId();
        return estatusService.findEstatus(id);
    }

	public Estatus getRandomEstatus() {
        init();
        Estatus obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return estatusService.findEstatus(id);
    }

	public boolean modifyEstatus(Estatus obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = estatusService.findEstatusEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Estatus' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Estatus>();
        for (int i = 0; i < 10; i++) {
            Estatus obj = getNewTransientEstatus(i);
            try {
                estatusService.saveEstatus(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            estatusRepository.flush();
            data.add(obj);
        }
    }
}
