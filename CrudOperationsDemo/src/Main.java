import java.sql.*;
import java.util.*;
class CreateBook {
    private final String pictureUrl;
    private final String title;
    private final String author;
    private final int isActive;
    private final String isbn;
    private final int pages;

    public CreateBook(String pictureUrl, String title, String author, int isActive, String isbn, int pages) {
        this.pictureUrl = pictureUrl;
        this.title = title;
        this.author = author;
        this.isActive = isActive;
        this.isbn = isbn;
        this.pages = pages;
    }


    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getIsActive() {
        return isActive;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return "Book{" +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isActive=" + isActive +
                ", isbn='" + isbn + '\'' +
                ", pages=" + pages +
                '}';
    }
}
class Book {
    private final int id;
    private final String pictureUrl;
    private final String title;
    private final String author;
    private final int isActive;
    private final String isbn;
    private final int pages;

    public Book(int id, String pictureUrl, String title, String author, int isActive, String isbn, int pages) {
        this.id = id;
        this.pictureUrl = pictureUrl;
        this.title = title;
        this.author = author;
        this.isActive = isActive;
        this.isbn = isbn;
        this.pages = pages;
    }

    public int getId() {
        return id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getIsActive() {
        return isActive;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isActive=" + isActive +
                ", isbn='" + isbn + '\'' +
                ", pages=" + pages +
                '}';
    }
}

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        try {
            String url= "Your SQl Connection String to be replaced" ;

            conn = DriverManager.getConnection(url);
            boolean exit = false;
            while (!exit) {
                System.out.println("\nWhat operation do you want to perform?");
                System.out.println("\n1. Read single Book from the database");
                System.out.println("\n2. Read all Books from the database");
                System.out.println("\n3. Insert new Book");
                System.out.println("\n4. Insert multiple Books into database");
                System.out.println("\n5. Delete a single Book");
                System.out.println("\n6. Delete multiple Books");
                System.out.println("\n7. Update single Book");
                System.out.println("\n8. Update multiple Books");
                System.out.println("\n9. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> readSingleBook(conn, scanner);
                    case 2 -> readAllBooks(conn);
                    case 3 -> insertNewBook(conn, scanner);
                    case 4 -> insertMultipleBooks(conn, scanner);
                    case 5 -> deleteSingleBook(conn, scanner);
                    case 6 -> deleteMultipleBooks(conn, scanner);
                    case 7 -> updateSingleBook(conn, scanner);
                    case 8 -> updateMultipleBooks(conn, scanner);
                    case 9 -> exit = true;
                    default -> System.out.println("Invalid choice.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    private static void updateSingleBook(Connection conn, Scanner scanner)throws SQLException {
        System.out.print("Enter the ID of the book to update: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("enter the pictureUrl of the book");
        String pictureUrl = scanner.nextLine();
        System.out.println("enter the title of the book");
        String title = scanner.nextLine();
        System.out.println("enter the author of the book");
        String author = scanner.nextLine();
        System.out.println("enter the Active Value of the book");
        int isActive = scanner.nextInt();
        System.out.println("enter the ISBN of the book");
        String ISBN = scanner.nextLine();
        scanner.nextLine();
        System.out.println("enter the no of pages of the book");
        int pages = scanner.nextInt();
        Book book = new Book(bookId, pictureUrl, title, author, isActive,ISBN, pages);

        PreparedStatement stmt = conn.prepareStatement("UPDATE Books SET pictureUrl=?, title=?, author=?, isActive=?, isbn=?, pages=? WHERE id=?");
        stmt.setString(1, book.getPictureUrl());
        stmt.setString(2, book.getTitle());
        stmt.setString(3, book.getAuthor());
        stmt.setInt(4, book.getIsActive());
        stmt.setString(5, book.getIsbn());
        stmt.setInt(6, book.getPages());
        stmt.setInt(7, book.getId());
        stmt.executeUpdate();

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected == 1) {
            System.out.println("Employee " + bookId + " updated.");
        } else {
            System.out.println("Failed to update employee.");
        }

    }
    private static void updateMultipleBooks(Connection conn, Scanner scanner)throws SQLException{

        System.out.print("How many Books do you want to update? ");
        int numBooks = scanner.nextInt();
        scanner.nextLine();

        List<Book> books=new ArrayList<>();
        for (int i = 0; i < numBooks; i++) {

            System.out.println("\nBook #" + (i + 1));
            System.out.print("Enter the ID: ");
            int bookId = scanner.nextInt();
            System.out.println("enter the pictureUrl of the book");
            String pictureUrl = scanner.nextLine();
            System.out.println("enter the title of the book");
            String title = scanner.nextLine();
            System.out.println("enter the author of the book");
            String author = scanner.nextLine();
            System.out.println("enter the Active Value of the book");
            int isActive = scanner.nextInt();
            System.out.println("enter the ISBN of the book");
            String ISBN = scanner.nextLine();
            System.out.println("enter the no of pages of the book");
            int pages = scanner.nextInt();

            Book book = new Book(bookId, pictureUrl, title, author, isActive,ISBN, pages);
            books.add(book);

        }
        PreparedStatement stmt = conn.prepareStatement("UPDATE Books SET pictureUrl=?, title=?, author=?, isActive=?, isbn=?, pages=? WHERE id=?");
        for (Book book:books) {

            stmt.setString(1, book.getPictureUrl());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setInt(4, book.getIsActive());
            stmt.setString(5, book.getIsbn());
            stmt.setInt(6, book.getPages());
            stmt.setInt(7, book.getId());
            stmt.addBatch();
        }

        int[] rowsAffected = stmt.executeBatch();
        int totalRowsAffected = Arrays.stream(rowsAffected).sum();

        if (totalRowsAffected == numBooks) {
            System.out.println(numBooks + " Books added.");
        } else {
            System.out.println("Failed to add Books.");
        }
    }
    private static void readAllBooks(Connection conn)throws SQLException {
        List<Book> books = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Books");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String pictureUrl = rs.getString("pictureUrl");
            String title = rs.getString("title");
            String author = rs.getString("author");
            int isActive = rs.getInt("isActive");
            String ISBN = rs.getString("ISBN");
            int pages = rs.getInt("pages");

            Book book = new Book(id, pictureUrl, title, author, isActive,ISBN, pages);
            books.add(book);
        }

        if (books.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("All employees:");
            for (Book book : books) {
                System.out.println(book);
            }
        }


    }
    public static void readSingleBook(Connection conn, Scanner scanner)throws SQLException {
        System.out.print("Enter the ID of the book to retrieve: ");
        int bookId = scanner.nextInt();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Books WHERE id = ?");
        stmt.setInt(1, bookId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String pictureUrl = rs.getString("pictureUrl");
            String title = rs.getString("title");
            String author = rs.getString("author");
            int isActive = rs.getInt("isActive");
            String ISBN = rs.getString("ISBN");
            int pages = rs.getInt("pages");

            System.out.println("\nID: " + id);
            System.out.println("Picture URL: " + pictureUrl);
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
            System.out.println("Is Active: " + isActive);
            System.out.println("ISBN: " + ISBN);
            System.out.println("Pages: " + pages);
        } else {
            System.out.println("Book not found");
        }

    }

    private static void insertNewBook(Connection conn, Scanner scanner)throws SQLException {
        readAllBooks(conn);
        System.out.println("enter the pictureUrl of the book");
        String pictureUrl = scanner.nextLine();
        scanner.nextLine();
        System.out.println("enter the title of the book");
        String title = scanner.nextLine();
        scanner.nextLine();
        System.out.println("enter the author of the book");
        String author = scanner.nextLine();
        scanner.nextLine();
        System.out.println("enter the Active Value of the book");
        int isActive = scanner.nextInt();
        scanner.nextLine();
        System.out.println("enter the ISBN of the book");
        String ISBN = scanner.nextLine();
        scanner.nextLine();
        System.out.println("enter the no of pages of the book");
        int pages = scanner.nextInt();
        scanner.nextLine();
        CreateBook book = new CreateBook(pictureUrl, title, author, isActive,ISBN, pages);
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Books (pictureUrl, title, author, isActive, isbn, pages) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setString(1, book.getPictureUrl());
        stmt.setString(2, book.getTitle());
        stmt.setString(3, book.getAuthor());
        stmt.setInt(4, book.getIsActive());
        stmt.setString(5, book.getIsbn());
        stmt.setInt(6, book.getPages());
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected == 1) {
            System.out.println("New Book added.");
        } else {
            System.out.println("Failed to add new Book.");
        }

    }
    private static void insertMultipleBooks(Connection conn, Scanner scanner)throws SQLException {
        readAllBooks(conn);
        System.out.print("How many books do you want to add? ");
        int numBooks = scanner.nextInt();
        scanner.nextLine();
        List<CreateBook> books = new ArrayList<>();
        for (int i = 0; i < numBooks; i++) {
            System.out.println("\nBook #" + (i + 1));
            System.out.println("enter the pictureUrl of the book");
            String pictureUrl = scanner.nextLine();
            System.out.println("enter the title of the book");
            String title = scanner.nextLine();
            System.out.println("enter the author of the book");
            String author = scanner.nextLine();
            System.out.println("enter the Active Value of the book");
            int isActive = scanner.nextInt();
            System.out.println("enter the ISBN of the book");
            String ISBN = scanner.nextLine();
            System.out.println("enter the no of pages of the book");
            int pages = scanner.nextInt();
            CreateBook book = new CreateBook(pictureUrl, title, author, isActive,ISBN, pages);
            books.add(book);
        }
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Books (pictureUrl, title, author, isActive, isbn, pages) VALUES (?, ?, ?, ?, ?, ?)");
        for (CreateBook book:books) {
            stmt.setString(1, book.getPictureUrl());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setInt(4, book.getIsActive());
            stmt.setString(5, book.getIsbn());
            stmt.setInt(6, book.getPages());
            stmt.addBatch();
        }

        int[] rowsAffected = stmt.executeBatch();
        int totalRowsAffected = Arrays.stream(rowsAffected).sum();

        if (totalRowsAffected == numBooks) {
            System.out.println(numBooks + " employees added.");
        } else {
            System.out.println("Failed to add employees.");
        }



    }

    private static void deleteSingleBook(Connection conn, Scanner scanner)throws SQLException {
        System.out.print("Enter the ID: ");
        int bookId = scanner.nextInt();

        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Books WHERE id = ?");
        stmt.setInt(1, bookId);
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected == 1) {
            System.out.println("Book " + bookId + " deleted.");
        } else {
            System.out.println("Failed to delete book.");
        }

    }
    private static void deleteMultipleBooks(Connection conn, Scanner scanner)throws SQLException {
        System.out.print("How many Books do you want to delete? ");
        int numBooks = scanner.nextInt();
        scanner.nextLine();

        List<Integer> bookIds=new ArrayList<>();
        for (int i = 0; i < numBooks; i++) {

            System.out.println("\nBook #" + (i + 1));
            System.out.print("Enter the ID: ");
            int bookId = scanner.nextInt();

            bookIds.add(bookId);


        }
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Books WHERE id = ?");
        for (Integer bookId:bookIds) {

            stmt.setInt(1, bookId);

            stmt.addBatch();
        }

        int[] rowsAffected = stmt.executeBatch();
        int totalRowsAffected = Arrays.stream(rowsAffected).sum();

        if (totalRowsAffected == numBooks) {
            System.out.println(numBooks + " Books Deleted.");
        } else {
            System.out.println("Failed to delete Books.");
        }
    }



}
