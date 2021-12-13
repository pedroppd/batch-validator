package br.com.oi.batchvalidation.models.dto;

import br.com.oi.batchvalidation.models.Batch;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class BatchDto {

    private String cep;
    private String name;
    private String cpf;
    private String telephone;

    public BatchDto(Batch batch){
        this.cep = batch.getCep();
        this.cpf = batch.getCpf();
        this.telephone = batch.getTelephone();
        this.name = batch.getName();
    }

    public BatchDto(){

    }
}
