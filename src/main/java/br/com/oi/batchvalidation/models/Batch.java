package br.com.oi.batchvalidation.models;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Batch {

    private String name;
    private String telephone;
    private String cep;
    private String cpf;
}
