package br.com.oi.batchvalidation.utils;

import br.com.oi.batchvalidation.models.Batch;
import br.com.oi.batchvalidation.models.dto.BatchDto;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvFileUtils {
    private static final String PATH_INPUT_FILE = "csv/fileInput/";
    private static final String PATH_OUTPUT_FILE = "src/main/resources/csv/fileOutput/";
    private CSVReader csvReader;
    private CSVWriter csvWriter;
    private String fileName;
    private File file;
    private FileReader fileReader;
    private FileWriter fileWriter;

    public CsvFileUtils(String fileName, boolean isInput) {
        if(isInput){
            this.fileName = PATH_INPUT_FILE + fileName + ".csv";
        }else {
            this.fileName = PATH_OUTPUT_FILE + fileName + "-" + DateUtils.getNow() + "-.csv";
        }
    }

    public BatchDto read() throws IOException {

        if(this.csvReader == null){
            getFileToRead();
            getFileReader();
            getCSVReader();
        }

        String[] fields = this.csvReader.readNext();

        if(fields == null){
            return null;
        }

        List<String> list = Arrays.asList(fields);

        if(list.size() >= 4){
            return new BatchDto(new Batch(list.get(0), list.get(1), list.get(2), list.get(3)));
        }

        return new BatchDto(list.toString());
    }

    private void getFileToRead() {

        ClassLoader classLoader = this.getClass().getClassLoader();

        if(this.file == null){
            String filePath = classLoader.getResource(this.fileName).getFile();
            this.file = new File(filePath);
        }
    }

    private void getFileReader() throws FileNotFoundException {
        if(this.fileReader == null){
            this.fileReader = new FileReader(this.file);
        }
    }

    private void getCSVReader() {
        if(this.csvReader == null){
            this.csvReader = new CSVReader(this.fileReader);
        }
    }

    public void closeReader() throws IOException {
        this.csvReader.close();
        this.fileReader.close();
    }

    public void writer(Batch batch) throws IOException {

        if(this.csvWriter == null){
            getFileToWrite();
            getFileWriter();
            getCSVWriter();
        }

        List<String> batchList = new ArrayList<>();

        if(batch.getName() != null){
            batchList.add(batch.getName());
        }

        if(batch.getCpf() != null){
            batchList.add(batch.getCpf());
        }

        if(batch.getCep() != null){
            batchList.add(batch.getCep());
        }

        if(batch.getTelephone() != null){
            batchList.add(batch.getTelephone());
        }

        batchList.add(DateUtils.getNow());

        this.csvWriter.writeNext(batchList.stream().toArray(String[]::new));
    }


    public void writerError(String[] carroError) throws IOException {

        if(this.csvWriter == null){
            getFileToWrite();
            getFileWriter();
            getCSVWriter();
        }

        this.csvWriter.writeNext(carroError);
    }

    private void getFileToWrite() throws IOException {
        if(this.file == null){
            this.file = new File(this.fileName);
            this.file.createNewFile();
        }
    }

    private void getFileWriter() throws IOException {
        if(this.fileWriter == null){
            this.fileWriter = new FileWriter(this.file, true);
        }
    }

    private void getCSVWriter() {
        if (this.csvWriter == null){
            this.csvWriter = new CSVWriter(this.fileWriter);
        }
    }

    public void closeWriter() throws IOException {
        this.csvWriter.close();
    }
}
