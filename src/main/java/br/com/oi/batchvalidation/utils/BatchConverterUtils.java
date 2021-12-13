package br.com.oi.batchvalidation.utils;

import br.com.oi.batchvalidation.models.Batch;
import br.com.oi.batchvalidation.models.dto.BatchDto;

import java.util.ArrayList;
import java.util.List;

public class BatchConverterUtils {

    public static String[] getErrorToStringArray(String message) {
        List<String> listOut = new ArrayList<>();
        listOut.add(message);
        listOut.add(DateUtils.getNow());
        return listOut.stream().toArray(String[]::new);
    }

    public static String[] batchToStringArray(BatchDto batch) {
        List<String> listOut = new ArrayList<>();
        listOut.add(batch.getName());
        listOut.add(batch.getTelephone());
        listOut.add(batch.getCep());
        listOut.add(batch.getCpf());
        listOut.add(DateUtils.getNow());
        return listOut.stream().toArray(String[]::new);
    }

    public static Batch getCarro(BatchDto batchDto) {
        Batch batch = new Batch();
        return batch.builder()
                .cep(batchDto.getCep())
                .cpf(batchDto.getCpf())
                .cep(batchDto.getCep())
                .name(batchDto.getTelephone()).build();

    }
}
