package Week3;

public class Person implements Comparable<Person> {
	private String name;
	private int age;
	
	public Person(String n){
		this.name = n;
	}
	
	
	public String toString(){
		return name;
	}
	
	
	public static void main(String[] args){
		Person p = new Person("Tim");
		System.out.println(p);
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public int compareTo(Person o){
		return this.getName().compareTo(o.getName());
	}
	
}
