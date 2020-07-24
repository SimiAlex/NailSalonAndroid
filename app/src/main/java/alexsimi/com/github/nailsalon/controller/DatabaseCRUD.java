package alexsimi.com.github.nailsalon.controller;


public interface DatabaseCRUD<T>
{
    boolean addRecord(T app);
    T readRecord(int position);
    void updateRecord(int position, T app);
    void deleteRecord(int position);
}
