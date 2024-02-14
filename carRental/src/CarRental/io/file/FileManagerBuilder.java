package CarRental.io.file;

import CarRental.exception.NoSuchFileTypeException;
import CarRental.io.ConsolePrinter;
import CarRental.io.DataReader;

public class FileManagerBuilder {
    private ConsolePrinter printer;
    private DataReader dataReader;

    public FileManagerBuilder(ConsolePrinter printer, DataReader dataReader) {
        this.printer = printer;
        this.dataReader = dataReader;
    }

    public FileManager build() {
        printer.printer("Wybierz format danych");
        FileType fileType = getFileType();
        switch (fileType) {
            case SERIAL -> {
                return new SerializableFileManager();
            }
            case CSV -> {
                return new CsvFileManager();
            }
            default -> throw new NoSuchFileTypeException("Nieobsługiwany typ danych");
        }
    }

    public FileType getFileType() {
        boolean typeOk = false;
        FileType result = null;
        do {
            printTypes();
            String type = dataReader.stringInput().toUpperCase();
            try {
                result = FileType.valueOf(type);
                typeOk = true;
            } catch (IllegalArgumentException e) {
                printer.printer("Nieobsługiwany typ danych, wyierz ponownie");
            }
        } while (!typeOk);
        return result;
    }

    public void printTypes() {
        FileType[] values = FileType.values();
        for (FileType value : values) {
            printer.printer(value.toString());
        }
    }


}
