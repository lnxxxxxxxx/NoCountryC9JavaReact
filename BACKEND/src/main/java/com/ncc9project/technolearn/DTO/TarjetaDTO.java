package com.ncc9project.technolearn.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TarjetaDTO {

    private String nombreTitular;

    private String numeroTarjeta;

    private String fechaExpiracion;

    private Integer cvc;

}
