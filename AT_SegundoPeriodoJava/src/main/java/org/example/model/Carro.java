package org.example.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Entity
public class Carro {
    @Id
    @GeneratedValue
    private int id;
    private String modelo;
    private String placa;
    private String chassi;
}
