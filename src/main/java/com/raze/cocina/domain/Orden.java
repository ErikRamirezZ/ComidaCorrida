package com.raze.cocina.domain;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.ManyToOne;
import com.raze.cocina.reference.Estatus;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import org.springframework.roo.addon.json.RooJson;

@Entity
@RooJavaBean
@RooToString
@RooJpaEntity
@RooJson(deepSerialize = true)
public class Orden {

    /**
     */
    @ManyToOne
    private Usuario usuario;

    /**
     */
    @ManyToOne
    private Estatus estatus;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date fecha;

    /**
     */
    @Size(max = 250)
    private String observaciones;

    /**
     */
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Platillo_Orden> platillosOrden = new HashSet<Platillo_Orden>();

    /**
     */
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Bebida_Orden> bebidasOrden = new HashSet<Bebida_Orden>();

    /**
     */
    @Max(100L)
    private int descuento;

	public Usuario getUsuario() {
        return this.usuario;
    }

	public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

	public Estatus getEstatus() {
        return this.estatus;
    }

	public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

	public Date getFecha() {
        return this.fecha;
    }

	public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

	public String getObservaciones() {
        return this.observaciones;
    }

	public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

	public Set<Platillo_Orden> getPlatillosOrden() {
        return this.platillosOrden;
    }

	public void setPlatillosOrden(Set<Platillo_Orden> platillosOrden) {
        this.platillosOrden = platillosOrden;
    }

	public Set<Bebida_Orden> getBebidasOrden() {
        return this.bebidasOrden;
    }

	public void setBebidasOrden(Set<Bebida_Orden> bebidasOrden) {
        this.bebidasOrden = bebidasOrden;
    }

	public int getDescuento() {
        return this.descuento;
    }

	public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(this);
    }

	public static Orden fromJsonToOrden(String json) {
        return new JSONDeserializer<Orden>()
        .use(null, Orden.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Orden> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

	public static String toJsonArray(Collection<Orden> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

	public static Collection<Orden> fromJsonArrayToOrdens(String json) {
        return new JSONDeserializer<List<Orden>>()
        .use("values", Orden.class).deserialize(json);
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
