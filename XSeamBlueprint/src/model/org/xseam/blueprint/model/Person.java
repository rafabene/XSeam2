package org.xseam.blueprint.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;
import org.xseam.domain.City;
import org.xseam.domain.State;
import org.xseam.model.BaseEntity;
import org.xseam.validation.CPF;

@Entity
@Name("person")
public class Person extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String name;

	@CPF
	private String cpf;

	private Integer age;

	@Temporal(TemporalType.DATE)
	private Date birth;
	
	@AttributeOverride(name="id",column=@Column(name="uf"))
	private State uf;
	
	@AttributeOverride(name="id",column=@Column(name="cidade"))
	private City cidade;
	
	public City getCidade() {
        return cidade;
    }
	
	public void setCidade(City cidade) {
        this.cidade = cidade;
    }
	
	public State getUf() {
        return uf;
    }
	
	public void setUf(State uf) {
        this.uf = uf;
    }
	
	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

}
