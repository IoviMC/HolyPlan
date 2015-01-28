package es.iovanamartinez.holyplan.dominio;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;

@Entity
@Table(name = "usuario")
public class Usuario {
	//ATRIBUTOS
	@Id
	@Column(name = "idusuario")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 30, nullable = false)
	private String nombreUsuario;
	@Column(length = 45, nullable = false)
	private String contrasena;
	@Column(length = 245, nullable = false)
	private String email;
	@Column(length = 245)
	private String emailTemp;
	@Column(nullable = false)
	private boolean activo;
	@Column(length = 32, nullable = false)
	private String hash;
	@Column(nullable = false)
	private boolean cuentaCerrada;
	
	// RELACIONES
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<ViajeUsuario> viajes = new HashSet<ViajeUsuario>();
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<UsuarioAmigo> amigos = new HashSet<UsuarioAmigo>();
	
	
	//CONSTRUCTORES
	public Usuario(){
		super();
	}
	
	public Usuario(String nombre, String contrasena, String email){
		super();
		this.nombreUsuario = nombre;
		this.contrasena = contrasena;
		//TODO crear hash contraseña
		this.email = email;
		this.hash = generarHashUsuario();
	}
	
	public Usuario(UsuarioVo usuarioVo){
		this.nombreUsuario = usuarioVo.getNombreUsuario();
		this.contrasena = usuarioVo.getContrasena();
		this.email = usuarioVo.getEmail();
		this.hash = generarHashUsuario();
	}

	//GETTERS AND SETTERS
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getNombreUsuario(){
		return nombreUsuario;
	}
	
	public void setNombreUsuario(String nombreUsuario){
		this.nombreUsuario = nombreUsuario;
	}
	
	public String getContrasena(){
		return contrasena;
	}
	
	public void setContrasena(String contrasena){
		this.contrasena = contrasena;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmailTemp(){
		return emailTemp;
	}
	
	public void setEmailTemp(String emailTemp){
		this.emailTemp = emailTemp;
	}
	
	public boolean isActivo(){
		return activo;
	}
	
	public void setActivo(boolean activo){
		this.activo = activo;
	}
	
	public String getHash(){
		return hash;
	}
	
	public void setHash(String hash){
		this.hash = hash;
	}
	
	public boolean isCuentaCerrada(){
		return cuentaCerrada;
	}
	
	public void setCuentaCerrada(Boolean cuentaCerrada){
		this.cuentaCerrada = cuentaCerrada;
	}
	
	public Set<ViajeUsuario> getViajes(){
		return viajes;
	}
	
	public void setViajes(Set<ViajeUsuario> viajes){
		this.viajes = viajes;
	}
	
	public Set<UsuarioAmigo> getAmigos(){
		return amigos;
	}
	
	public void setAmigos(Set<UsuarioAmigo> amigos){
		this.amigos = amigos;
	}
	
	//METODOS
	public void anadirViaje(ViajeUsuario viaje){
		this.viajes.add(viaje);
	}

	public void anadirAmigo(UsuarioAmigo usuarioAmigo) {
		this.amigos.add(usuarioAmigo);		
	}
	
	public String generarHashUsuario() {
		try {
			MessageDigest codificador = MessageDigest.getInstance("MD5");
			
			String hashPrev = new String(this.getNombreUsuario() + Calendar.getInstance().hashCode() + this.getId() + "hash");
			byte[] hashPrevEnBytes = hashPrev.getBytes();
			
			byte[] hashEnBytes = codificador.digest(hashPrevEnBytes);
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < hashEnBytes.length; i++) {
				sb.append(Integer.toString((hashEnBytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			return (sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombreUsuario == null) ? 0 : nombreUsuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) obj;

		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (nombreUsuario == null) {
			if (other.nombreUsuario != null) {
				return false;
			}
		} else if (!nombreUsuario.equals(other.nombreUsuario)) {
			return false;
		}
		return true;
	}
}
