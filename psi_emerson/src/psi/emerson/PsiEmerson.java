package psi.emerson;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.util.Iterator; 
import java.util.Set; 
import java.util.HashSet; 
import java.util.TreeSet; 
import java.util.Map;
import java.util.Properties;
import java.util.HashMap; 
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ClassNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;


public class PsiEmerson {

	private static Logger logger = LogManager.getLogger(PsiEmerson.class);

	static Properties prop = new Properties();

	public static void main(String[] args) throws IOException, CsvException {
		System.out.println("Hello World!");
		logger.debug("this is debug");
		System.out.println("System.getProperty(user.dir): " + System.getProperty("user.dir"));
		System.out.println("==========");
		User user = new User(1, "José");
		User user2 = new User(2, "Maria");
		User user3 = new User(3, "Pedro");
		User user4 = new User(4, "Joana");
		User user5 = new User(5, "Joaquim");
		User user6 = new User(6, "Mariana");
		User user7 = new User(7, "Carlos");
		User user8 = new User(8, "Priscila");
		User user9 = new User(9, "João");
		User user10 = new User(10, "Patrícia");

		System.out.println("===== List =====");
		List<User> users = new ArrayList<>();
		users.add(user);
		users.add(user2);

		//System.out.println(Arrays.toString(users.toArray()));
		System.out.println("for(User u : users");
		for(User u : users){
			System.out.println(u.getName());
		}
		System.out.println("==========");
		System.out.println("users.forEach");
		users.forEach(User -> {
			System.out.println(User.getName());
		});
		System.out.println("==========");
		System.out.println("Iterator");
		Iterator<User> it = users.iterator(); 
		while (it.hasNext()) {  
			User u = (User)it.next(); 
			System.out.println(u.getName()); 
		} 
		System.out.println("==========");
		System.out.println("stream");
		users.stream().forEach(System.out::println);
		users.stream().map(Object::toString).collect(Collectors.joining("\n"));
		System.out.println("==========");

		System.out.println("==== HashSet ======");
		Set<User> usr = new HashSet<>();
		usr.add(user);
		usr.add(user2);
		usr.add(user3);
		usr.add(user4);
		usr.add(user5);
		usr.add(user6);
		System.out.println("===== 1 =====");
		for(User u : usr){
			System.out.println(u.getName());
		}
		System.out.println("===== 2 =====");
		for(User u : usr){
			System.out.println(u.getName());
		}
		System.out.println("==== TreeSet ======");
		Set<User> tree = new HashSet<>();
		tree.add(user);
		tree.add(user2);
		tree.add(user3);
		tree.add(user4);
		tree.add(user5);
		tree.add(user6);
		System.out.println("===== 1 =====");
		for(User u : tree){
			System.out.println(u.getName());
		}
		System.out.println("===== 2 =====");
		for(User u : tree){
			System.out.println(u.getName());
		}
		try {
			System.out.println("==== Serialize ======");
			String serialized = serializeObjectToString(user10);
			System.out.println(serialized);
			User deserialized = (User)deSerializeObjectFromString(serialized);
			System.out.println(deserialized.getName());

		} catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("==== Map ====");
		//Map map<Integer, User> = new HashMap<>();

		try {
			System.out.println("==== Properties ====");
			getProperties();
			setProperties();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String fileName = "src/Sacramentorealestatetransactions.csv";
        Path myPath = Paths.get(fileName);
		CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		try ( BufferedReader br = Files.newBufferedReader(myPath,  StandardCharsets.UTF_8);
				CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).build(); ) {
			
			List<String[]> rows = reader.readAll();
			for (String[] row : rows) {
				for (String e : row) {
					System.out.format("%s ", e);
				}
				System.out.println();
			}
		}

	}

	public static void getProperties() throws IOException {
		InputStream input = new FileInputStream(System.getProperty("user.dir") + "/src/config/config.properties");
		prop.load(input);
		System.out.println(prop.getProperty("browser"));
		System.out.println(prop.getProperty("environment"));
	}

	public static void setProperties() throws IOException {
		OutputStream output = new FileOutputStream(System.getProperty("user.dir") + "/src/config/config.properties");
		prop.setProperty("java", "11");
		prop.store(output, "java version");
	}

	public static String serializeObjectToString(Serializable o) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		oos.close();

		return Base64.getEncoder().encodeToString(baos.toByteArray());
	}

	public static Object deSerializeObjectFromString(String s)
			throws IOException, ClassNotFoundException {

		byte[] data = Base64.getDecoder().decode(s);
		ObjectInputStream ois = new ObjectInputStream(
				new ByteArrayInputStream(data));
		Object o = ois.readObject();
		ois.close();
		return o;
	}
}
