package alexsimi.com.github.nailsalon.model;
import java.time.LocalDateTime;

public class Appointment
{
    //fields
    private int id;
    private String name;
    private LocalDateTime time;
    private String procedure;
    private double price;

    //constructor
    public Appointment(int id, String name, LocalDateTime time, String procedure, double price)
    {
        this.id = id;
        this.name = name;
        this.time = time;
        this.price = price;
        this.procedure = procedure;
    }

    //methods

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public LocalDateTime getTime()
    {
        return time;
    }

    public double getPrice()
    {
        return price;
    }

    public String getProcedure()
    {
        return procedure;
    }

    @Override
    public String toString()
    {
        return String.format("%d\t%s\t%s\t%s\t%.2f",id, name, time.toString(), procedure, price);
    }
}
