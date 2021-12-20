package br.com.oi.batchvalidation.models;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Batch {

    private UUID uuid;
    private String name;
    private String telephone;
    private String cep;
    private String cpf;

    public Batch(String name, String telephone, String cep, String cpf) {
        this.name = name;
        this.telephone = telephone;
        this.cep = cep;
        this.cpf = cpf;
    }
}
