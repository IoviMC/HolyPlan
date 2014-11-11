package es.iovanamartinez.holyplan.dominio.vo;

import es.iovanamartinez.holyplan.dominio.Usuario;

public class UsuarioVo {
	//ATRIBUTOS
	private Integer id;
	private String nombreUsuario;
	private String contrasena;
	private String email;
	private String emailTemp;
	private boolean activo;
	private String hash;
	private boolean cuentaCerrada;
	
	//CONSTRUCTORES
	public UsuarioVo(){
		super();
	}
	
	public UsuarioVo(String nombre, String contrasena, String email){
		super();
		this.nombreUsuario = nombre;
		this.contrasena = contrasena;
		this.email = email;
		this.activo = false;
	}
	
	public UsuarioVo(Usuario usuario){
		this.id = usuario.getId();
		this.nombreUsuario = usuario.getNombreUsuario();
		this.contrasena = usuario.getContrasena();
		this.email = usuario.getEmail();
		this.emailTemp = usuario.getEmailTemp();
		this.activo = usuario.isActivo();
		this.hash = usuario.getHash();
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
}
