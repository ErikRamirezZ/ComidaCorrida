package com.raze.cocina.web;

import com.raze.cocina.domain.Agregado;
import com.raze.cocina.domain.Bebida;
import com.raze.cocina.domain.Bebida_Orden;
import com.raze.cocina.domain.Orden;
import com.raze.cocina.domain.Platillo;
import com.raze.cocina.domain.Platillo_Orden;
import com.raze.cocina.domain.Usuario;
import com.raze.cocina.reference.CategoriaBebida;
import com.raze.cocina.reference.CategoriaPlatillo;
import com.raze.cocina.reference.Estatus;
import com.raze.cocina.service.AgregadoService;
import com.raze.cocina.service.BebidaService;
import com.raze.cocina.service.Bebida_OrdenService;
import com.raze.cocina.service.CategoriaBebidaService;
import com.raze.cocina.service.CategoriaPlatilloService;
import com.raze.cocina.service.EstatusService;
import com.raze.cocina.service.OrdenService;
import com.raze.cocina.service.PlatilloService;
import com.raze.cocina.service.Platillo_OrdenService;
import com.raze.cocina.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	@Autowired
    AgregadoService agregadoService;

	@Autowired
    BebidaService bebidaService;

	@Autowired
    Bebida_OrdenService bebida_OrdenService;

	@Autowired
    OrdenService ordenService;

	@Autowired
    PlatilloService platilloService;

	@Autowired
    Platillo_OrdenService platillo_OrdenService;

	@Autowired
    UsuarioService usuarioService;

	@Autowired
    CategoriaBebidaService categoriaBebidaService;

	@Autowired
    CategoriaPlatilloService categoriaPlatilloService;

	@Autowired
    EstatusService estatusService;

	public Converter<Agregado, String> getAgregadoToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.raze.cocina.domain.Agregado, java.lang.String>() {
            public String convert(Agregado agregado) {
                return new StringBuilder().append(agregado.getNombre()).append(' ').append(agregado.getDescripcion()).append(' ').append(agregado.getDisponibles()).toString();
            }
        };
    }

	public Converter<Long, Agregado> getIdToAgregadoConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.raze.cocina.domain.Agregado>() {
            public com.raze.cocina.domain.Agregado convert(java.lang.Long id) {
                return agregadoService.findAgregado(id);
            }
        };
    }

	public Converter<String, Agregado> getStringToAgregadoConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.raze.cocina.domain.Agregado>() {
            public com.raze.cocina.domain.Agregado convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Agregado.class);
            }
        };
    }

	public Converter<Bebida, String> getBebidaToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.raze.cocina.domain.Bebida, java.lang.String>() {
            public String convert(Bebida bebida) {
                return new StringBuilder().append(bebida.getNombre()).append(' ').append(bebida.getDescripcion()).append(' ').append(bebida.getDisponibles()).append(' ').append(bebida.getPrecio()).toString();
            }
        };
    }

	public Converter<Long, Bebida> getIdToBebidaConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.raze.cocina.domain.Bebida>() {
            public com.raze.cocina.domain.Bebida convert(java.lang.Long id) {
                return bebidaService.findBebida(id);
            }
        };
    }

	public Converter<String, Bebida> getStringToBebidaConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.raze.cocina.domain.Bebida>() {
            public com.raze.cocina.domain.Bebida convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Bebida.class);
            }
        };
    }

	public Converter<Bebida_Orden, String> getBebida_OrdenToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.raze.cocina.domain.Bebida_Orden, java.lang.String>() {
            public String convert(Bebida_Orden bebida_Orden) {
                return new StringBuilder().append(bebida_Orden.getCantidad()).append(' ').append(bebida_Orden.getPrecioUnitario()).toString();
            }
        };
    }

	public Converter<Long, Bebida_Orden> getIdToBebida_OrdenConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.raze.cocina.domain.Bebida_Orden>() {
            public com.raze.cocina.domain.Bebida_Orden convert(java.lang.Long id) {
                return bebida_OrdenService.findBebida_Orden(id);
            }
        };
    }

	public Converter<String, Bebida_Orden> getStringToBebida_OrdenConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.raze.cocina.domain.Bebida_Orden>() {
            public com.raze.cocina.domain.Bebida_Orden convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Bebida_Orden.class);
            }
        };
    }

	public Converter<Orden, String> getOrdenToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.raze.cocina.domain.Orden, java.lang.String>() {
            public String convert(Orden orden) {
                return new StringBuilder().append(orden.getFecha()).append(' ').append(orden.getObservaciones()).append(' ').append(orden.getDescuento()).toString();
            }
        };
    }

	public Converter<Long, Orden> getIdToOrdenConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.raze.cocina.domain.Orden>() {
            public com.raze.cocina.domain.Orden convert(java.lang.Long id) {
                return ordenService.findOrden(id);
            }
        };
    }

	public Converter<String, Orden> getStringToOrdenConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.raze.cocina.domain.Orden>() {
            public com.raze.cocina.domain.Orden convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Orden.class);
            }
        };
    }

	public Converter<Platillo, String> getPlatilloToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.raze.cocina.domain.Platillo, java.lang.String>() {
            public String convert(Platillo platillo) {
                return new StringBuilder().append(platillo.getNombre()).append(' ').append(platillo.getDescripcion()).append(' ').append(platillo.getDisponibles()).append(' ').append(platillo.getPrecio()).toString();
            }
        };
    }

	public Converter<Long, Platillo> getIdToPlatilloConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.raze.cocina.domain.Platillo>() {
            public com.raze.cocina.domain.Platillo convert(java.lang.Long id) {
                return platilloService.findPlatillo(id);
            }
        };
    }

	public Converter<String, Platillo> getStringToPlatilloConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.raze.cocina.domain.Platillo>() {
            public com.raze.cocina.domain.Platillo convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Platillo.class);
            }
        };
    }

	public Converter<Platillo_Orden, String> getPlatillo_OrdenToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.raze.cocina.domain.Platillo_Orden, java.lang.String>() {
            public String convert(Platillo_Orden platillo_Orden) {
                return new StringBuilder().append(platillo_Orden.getCantidad()).append(' ').append(platillo_Orden.getPrecioUnitario()).toString();
            }
        };
    }

	public Converter<Long, Platillo_Orden> getIdToPlatillo_OrdenConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.raze.cocina.domain.Platillo_Orden>() {
            public com.raze.cocina.domain.Platillo_Orden convert(java.lang.Long id) {
                return platillo_OrdenService.findPlatillo_Orden(id);
            }
        };
    }

	public Converter<String, Platillo_Orden> getStringToPlatillo_OrdenConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.raze.cocina.domain.Platillo_Orden>() {
            public com.raze.cocina.domain.Platillo_Orden convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Platillo_Orden.class);
            }
        };
    }

	public Converter<Usuario, String> getUsuarioToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.raze.cocina.domain.Usuario, java.lang.String>() {
            public String convert(Usuario usuario) {
                return new StringBuilder().append(usuario.getNombre()).append(' ').append(usuario.getDomicilio()).append(' ').append(usuario.getTelefono()).append(' ').append(usuario.getObservaciones()).toString();
            }
        };
    }

	public Converter<Long, Usuario> getIdToUsuarioConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.raze.cocina.domain.Usuario>() {
            public com.raze.cocina.domain.Usuario convert(java.lang.Long id) {
                return usuarioService.findUsuario(id);
            }
        };
    }

	public Converter<String, Usuario> getStringToUsuarioConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.raze.cocina.domain.Usuario>() {
            public com.raze.cocina.domain.Usuario convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Usuario.class);
            }
        };
    }

	public Converter<CategoriaBebida, String> getCategoriaBebidaToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.raze.cocina.reference.CategoriaBebida, java.lang.String>() {
            public String convert(CategoriaBebida categoriaBebida) {
                return new StringBuilder().append(categoriaBebida.getNombre()).append(' ').append(categoriaBebida.getDescripcion()).toString();
            }
        };
    }

	public Converter<Long, CategoriaBebida> getIdToCategoriaBebidaConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.raze.cocina.reference.CategoriaBebida>() {
            public com.raze.cocina.reference.CategoriaBebida convert(java.lang.Long id) {
                return categoriaBebidaService.findCategoriaBebida(id);
            }
        };
    }

	public Converter<String, CategoriaBebida> getStringToCategoriaBebidaConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.raze.cocina.reference.CategoriaBebida>() {
            public com.raze.cocina.reference.CategoriaBebida convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), CategoriaBebida.class);
            }
        };
    }

	public Converter<CategoriaPlatillo, String> getCategoriaPlatilloToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.raze.cocina.reference.CategoriaPlatillo, java.lang.String>() {
            public String convert(CategoriaPlatillo categoriaPlatillo) {
                return new StringBuilder().append(categoriaPlatillo.getNombre()).append(' ').append(categoriaPlatillo.getDescripcion()).toString();
            }
        };
    }

	public Converter<Long, CategoriaPlatillo> getIdToCategoriaPlatilloConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.raze.cocina.reference.CategoriaPlatillo>() {
            public com.raze.cocina.reference.CategoriaPlatillo convert(java.lang.Long id) {
                return categoriaPlatilloService.findCategoriaPlatillo(id);
            }
        };
    }

	public Converter<String, CategoriaPlatillo> getStringToCategoriaPlatilloConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.raze.cocina.reference.CategoriaPlatillo>() {
            public com.raze.cocina.reference.CategoriaPlatillo convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), CategoriaPlatillo.class);
            }
        };
    }

	public Converter<Estatus, String> getEstatusToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.raze.cocina.reference.Estatus, java.lang.String>() {
            public String convert(Estatus estatus) {
                return new StringBuilder().append(estatus.getEstatus()).append(' ').append(estatus.getDescripcion()).toString();
            }
        };
    }

	public Converter<Long, Estatus> getIdToEstatusConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.raze.cocina.reference.Estatus>() {
            public com.raze.cocina.reference.Estatus convert(java.lang.Long id) {
                return estatusService.findEstatus(id);
            }
        };
    }

	public Converter<String, Estatus> getStringToEstatusConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.raze.cocina.reference.Estatus>() {
            public com.raze.cocina.reference.Estatus convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Estatus.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getAgregadoToStringConverter());
        registry.addConverter(getIdToAgregadoConverter());
        registry.addConverter(getStringToAgregadoConverter());
        registry.addConverter(getBebidaToStringConverter());
        registry.addConverter(getIdToBebidaConverter());
        registry.addConverter(getStringToBebidaConverter());
        registry.addConverter(getBebida_OrdenToStringConverter());
        registry.addConverter(getIdToBebida_OrdenConverter());
        registry.addConverter(getStringToBebida_OrdenConverter());
        registry.addConverter(getOrdenToStringConverter());
        registry.addConverter(getIdToOrdenConverter());
        registry.addConverter(getStringToOrdenConverter());
        registry.addConverter(getPlatilloToStringConverter());
        registry.addConverter(getIdToPlatilloConverter());
        registry.addConverter(getStringToPlatilloConverter());
        registry.addConverter(getPlatillo_OrdenToStringConverter());
        registry.addConverter(getIdToPlatillo_OrdenConverter());
        registry.addConverter(getStringToPlatillo_OrdenConverter());
        registry.addConverter(getUsuarioToStringConverter());
        registry.addConverter(getIdToUsuarioConverter());
        registry.addConverter(getStringToUsuarioConverter());
        registry.addConverter(getCategoriaBebidaToStringConverter());
        registry.addConverter(getIdToCategoriaBebidaConverter());
        registry.addConverter(getStringToCategoriaBebidaConverter());
        registry.addConverter(getCategoriaPlatilloToStringConverter());
        registry.addConverter(getIdToCategoriaPlatilloConverter());
        registry.addConverter(getStringToCategoriaPlatilloConverter());
        registry.addConverter(getEstatusToStringConverter());
        registry.addConverter(getIdToEstatusConverter());
        registry.addConverter(getStringToEstatusConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
