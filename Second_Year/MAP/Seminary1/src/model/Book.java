package model;

public class Book extends entity{
    private int noPage;
    private String title;

    public Book(int noPage, String title)
    {
        this.noPage = noPage;
        this.title = title;
    }

    @Override
    public String toString()
    {
        return  "Book{" +
                "Number of pages ='" + this.noPage + '\'' + ", title = " + this.title +
                ", weight = " + weight +
                '}';
    }
}
