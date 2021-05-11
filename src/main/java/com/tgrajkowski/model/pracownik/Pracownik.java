package com.tgrajkowski.model.pracownik;

import com.tgrajkowski.model.zespol.Zespol;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "EMP_TYP")
@Entity
@Table(name = "pracownik")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Pracownik {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pracownik_id")
    private Long id;
    @Column(name = "name", length = 50)
    private String name;
    @ManyToMany(
            targetEntity = Zespol.class,
            mappedBy = "pracownikList",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    private List<Zespol> zespolList =new ArrayList<>();

    public void updatePacownik(PracownikDto pracownikDto) {
        this.name = pracownikDto.getName();
    }
}
