package com.raze.cocina.domain;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import java.math.BigDecimal;
import org.springframework.roo.addon.json.RooJson;

@Entity
@RooJavaBean
@RooToString
@RooJpaEntity
@RooJson(deepSerialize = true)
public class Platillo_Orden {

    /**
     */
    @NotNull
    @ManyToOne
    private Platillo platillo;

    /**
     */
    @NotNull
    private int cantidad;

    /**
     */
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Agregado> agregados = new HashSet<Agregado>();

    /**
     */
    @NotNull
    private BigDecimal precioUnitario;

	public Platillo getPlatillo() {
        return this.platillo;
    }

	public void setPlatillo(Platillo platillo) {
        this.platillo = platillo;
    }

	public int getCantidad() {
        return this.cantidad;
    }

	public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

	public Set<Agregado> getAgregados() {
        return this.agregados;
    }

	public void setAgregados(Set<Agregado> agregados) {
        this.agregados = agregados;
    }

	public BigDecimal getPrecioUnitario() {
        return this.precioUnitario;
    }

	public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(this);
    }

	public static Platillo_Orden fromJsonToPlatillo_Orden(String json) {
        return new JSONDeserializer<Platillo_Orden>()
        .use(null, Platillo_Orden.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Platillo_Orden> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

	public static String toJsonArray(Collection<Platillo_Orden> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

	public static Collection<Platillo_Orden> fromJsonArrayToPlatillo_Ordens(String json) {
        return new JSONDeserializer<List<Platillo_Orden>>()
        .use("values", Platillo_Orden.class).deserialize(json);
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

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
