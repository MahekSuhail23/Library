package lib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import lib.DConnection;
import lib.LibraryManagement;

public class LibraryManagement {

	void login(Connection conn, int sid) {

		Statement statement;

		Scanner obj = new Scanner(System.in);

		try {
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("select * from students where id=" + sid);
			while (rs.next()) {
				System.out.println("Your name is : " + rs.getString(2));
				System.out.println("Book Issued : " + rs.getString(3));
				System.out.println("1.Book Issue");
				System.out.println("2.Book Return");

				int ch = obj.nextInt();
				switch (ch) {

				case 1:

					try {
						statement = conn.createStatement();
						ResultSet es = statement.executeQuery("select * from students where id=" + sid);

						while (es.next()) {
							if (es.getString(3) == null) {

							} else {
								System.out.println("Please return the book issued");
								System.exit(0);
							}
						}

						ResultSet rss = statement.executeQuery("select * from books");
						while (rss.next()) {
							System.out.println(rss.getInt(1) + "." + rss.getString(2) + " = " + rss.getString(3));
						}
						System.out.println("Enter which book you want : ");
						int bid = obj.nextInt();
						ResultSet bs = statement.executeQuery("select * from books where id=" + bid);
						while (bs.next()) {
							String book_name = bs.getString(2);
							PreparedStatement pst;
							PreparedStatement qnt;
							try {
								pst = conn.prepareStatement(
										"update students set book_name='" + book_name + "' where id=" + sid);
								qnt = conn.prepareStatement(
										"update books set no_of_books = no_of_books - 1 where book_name= '" + book_name
												+ "' and no_of_books > 0");
								int i = pst.executeUpdate();
								int j = qnt.executeUpdate();

								if (i != 0) {
									System.out.println("Book successfully issued");
								}
								if (j == 0) {
									System.out.println("Book not available");
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;

				case 2:
					try {
						statement = conn.createStatement();
						ResultSet rss = statement.executeQuery("select * from students where id=" + sid);
						while (rss.next()) {
							String book_name = rss.getString(3);
							System.out.println("You have issued this book : " + rss.getString(3));
							PreparedStatement pst, qnt;
							try {
								pst = conn.prepareStatement("update students set book_name=NULL where id=" + sid);
								qnt = conn.prepareStatement(
										"update books set no_of_books = no_of_books + 1 where book_name= '" + book_name
												+ "'");
								int i = pst.executeUpdate();
								int j = qnt.executeUpdate();

								if (i != 0) {
									System.out.println("Thank you for returning the book");
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void NewReg(Connection conn, String name) {
		PreparedStatement nst;
		try {
			nst = conn.prepareStatement("INSERT INTO students (stud_name) VALUES ('" + name + "')");
			int i = nst.executeUpdate();

			if (i != 0) {
				System.out.println("Registration Completed");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		LibraryManagement lib = new LibraryManagement();
		Connection connection = DConnection.getConnection();

		System.out.println("-------Welcome To Our Library-------");
		System.out.println("Choose Option");
		System.out.println("1.New Registration");
		System.out.println("2.Already Registered");
		int choice = sc.nextInt();
		if (choice == 1) {
			System.out.println("Enter your name : ");
			sc.nextLine();
			String name = sc.nextLine();
			lib.NewReg(connection, name);
		} else {
			System.out.println("Enter Student ID : ");
			int stid = sc.nextInt();
			lib.login(connection, stid);
		}

	}

}