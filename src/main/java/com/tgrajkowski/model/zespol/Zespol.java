package com.tgrajkowski.model.zespol;

import com.tgrajkowski.model.pracownik.Pracownik;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Zespol {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @ManyToMany
    @JoinTable(
            joinColumns = {@JoinColumn(name = "pracownik_id")},
            inverseJoinColumns = {@JoinColumn(name = "zespol_id")}
    )
    private List<Pracownik> pracownikList =new ArrayList<>();
}
