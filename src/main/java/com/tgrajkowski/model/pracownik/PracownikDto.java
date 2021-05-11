package com.tgrajkowski.model.pracownik;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@SuperBuilder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PracownikDto {
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String name;
}
