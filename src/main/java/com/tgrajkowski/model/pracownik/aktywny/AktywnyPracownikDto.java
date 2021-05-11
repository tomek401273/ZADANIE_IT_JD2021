package com.tgrajkowski.model.pracownik.aktywny;

import com.tgrajkowski.model.pracownik.PracownikDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AktywnyPracownikDto extends PracownikDto {
    @NotNull
    @Min(1)
    private Double salary;
    @PastOrPresent
    private LocalDate dataZatrudnienia;
}
