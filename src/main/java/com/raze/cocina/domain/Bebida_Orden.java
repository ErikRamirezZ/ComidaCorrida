package com.raze.cocina.domain;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import org.springframework.roo.addon.json.RooJson;

@Entity
@RooJavaBean
@RooToString
@RooJpaEntity
@RooJson(deepSerialize = true)
public class Bebida_Orden {

    /**
     */
    @ManyToOne
    private Bebida bebida;

    /**
     */
    @NotNull
    private int cantidad;

    /**
     */
    @NotNull
    private BigDecimal precioUnitario;

	public Bebida getBebida() {
        return this.bebida;
    }

	public void setBebida(Bebida bebida) {
        this.bebida = bebida;
    }

	public int getCantidad() {
        return this.cantidad;
    }

	public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

	public BigDecimal getPrecioUnitario() {
        return this.precioUnitario;
    }

	public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

	public static Bebida_Orden fromJsonToBebida_Orden(String json) {
        return new JSONDeserializer<Bebida_Orden>()
        .use(null, Bebida_Orden.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Bebida_Orden> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

	public static String toJsonArray(Collection<Bebida_Orden> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

	public static Collection<Bebida_Orden> fromJsonArrayToBebida_Ordens(String json) {
        return new JSONDeserializer<List<Bebida_Orden>>()
        .use("values", Bebida_Orden.class).deserialize(json);
    }
}
