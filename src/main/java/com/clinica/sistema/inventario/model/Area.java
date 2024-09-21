package com.clinica.sistema.inventario.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "areas")
public class Area {

    @Id
    @Column(name = "idArea")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArea;


    @Column(name = "nombreArea")
    private String nombreArea;

    @OneToMany(mappedBy = "area")
    private Set<Usuario> usuarios;

}
