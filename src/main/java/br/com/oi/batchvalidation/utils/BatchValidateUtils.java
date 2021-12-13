package br.com.oi.batchvalidation.utils;

import br.com.oi.batchvalidation.models.dto.BatchDto;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BatchValidateUtils {

    public BatchValidateUtils(){

    }

    public static List<BatchDto> validate(List<BatchDto> carroDtoList) throws IOException {

        List<BatchDto> validList = new ArrayList<>();
        List<BatchDto> invalidList = new ArrayList<>();

        carroDtoList.stream().forEach(dto -> {

            if(isValid(dto)){
                validList.add(dto);
            }else{
                invalidList.add(dto);
            }
        });

        createCsvToInvalidList(invalidList);
        return validList;
    }

    private static void createCsvToInvalidList(List<BatchDto> invalidList) throws IOException {

        if(!invalidList.isEmpty()){

            CsvFileUtils csvOutInvalid = new CsvFileUtils("invalid-import", false);

            invalidList.stream().forEach(batch -> {
                try {
                    csvOutInvalid.writerError(BatchConverterUtils.batchToStringArray(batch));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            csvOutInvalid.closeWriter();
        }
    }

    public void emptyFile(LocalDateTime localDateTime) throws IOException {

        CsvFileUtils emptyCSV = new CsvFileUtils("empty-file",false);

        try {
            emptyCSV.writerError(BatchConverterUtils.getErrorToStringArray("Tentativa de Import de arquivo vazio!"));
            emptyCSV.closeWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValid(BatchDto batchDto) {
        return StringUtils.isNotBlank(batchDto.getCep())
                && StringUtils.isNotBlank(batchDto.getName())
                && StringUtils.isNotBlank(batchDto.getTelephone())
                && StringUtils.isNotBlank(batchDto.getCep());
    }
}
