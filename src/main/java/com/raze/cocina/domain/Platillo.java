package com.raze.cocina.domain;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.raze.cocina.reference.CategoriaPlatillo;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import javax.persistence.Lob;
import org.springframework.roo.classpath.operations.jsr303.RooUploadedFile;
import org.springframework.roo.addon.json.RooJson;

@Entity
@RooJavaBean
@RooToString
@RooJpaEntity
@RooJson(deepSerialize = true)
public class Platillo {

    /**
     */
    @NotNull
    @Size(max = 50)
    private String nombre;

    /**
     */
    @NotNull
    @Size(max = 250)
    private String descripcion;

    /**
     */
    @ManyToOne
    private CategoriaPlatillo categoria;

    /**
     */
    private int disponibles;

    /**
     */
    private BigDecimal precio;

    /**
     */
    @RooUploadedFile(contentType = "image/jpeg", autoUpload = true)
    @Lob
    private byte[] foto;

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

	public String getNombre() {
        return this.nombre;
    }

	public void setNombre(String nombre) {
        this.nombre = nombre;
    }

	public String getDescripcion() {
        return this.descripcion;
    }

	public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

	public CategoriaPlatillo getCategoria() {
        return this.categoria;
    }

	public void setCategoria(CategoriaPlatillo categoria) {
        this.categoria = categoria;
    }

	public int getDisponibles() {
        return this.disponibles;
    }

	public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

	public BigDecimal getPrecio() {
        return this.precio;
    }

	public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

	public byte[] getFoto() {
        return this.foto;
    }

	public void setFoto(byte[] foto) {
        this.foto = foto;
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(this);
    }

	public static Platillo fromJsonToPlatillo(String json) {
        return new JSONDeserializer<Platillo>()
        .use(null, Platillo.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Platillo> collection) {
        return new JSONSerializer()
        .exclude("*.class").deepSerialize(collection);
    }

	public static String toJsonArray(Collection<Platillo> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").deepSerialize(collection);
    }

	public static Collection<Platillo> fromJsonArrayToPlatilloes(String json) {
        return new JSONDeserializer<List<Platillo>>()
        .use("values", Platillo.class).deserialize(json);
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
