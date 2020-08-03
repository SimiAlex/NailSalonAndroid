package alexsimi.com.github.nailsalon.controller;

import java.io.File;

public interface DatabaseCRUD<T>
{
    boolean addRecord(T app);
    T readRecord(int position);
    void updateRecord(int position, T app);
    void deleteRecord(int position);
    void loadDb(File sourceFile);
    void saveDb(File sourceFile);
}
