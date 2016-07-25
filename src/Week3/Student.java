package Week3;

public class Student extends Person{
	private int id;
	
	public int getID(){
		return id;
	}
	
	public Student(String n, int id){
		super(n);
		this.id = id;
	}
	
	public String toString(){
		return id + " " + super.toString();
	}
		
	public static void main(String[] args){
		Person s = new Student("Hannan", 1323);
		Person p = new Person("Yo");
		
		System.out.println(((Student)s).getID());
	
		if (p instanceof Student){
			System.out.println(((Student)p).getID());
		}
		else {
			System.out.println("p is not an instanceof Student");
		}
	}
}
