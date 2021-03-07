package lambdas;

public class Greeter {

	public void greet(Greeting greeting) {
		greeting.perform();
	}
	
	public static void main(String[] args) {
		
		Greeter greeter = new Greeter();
		
		Greeting helloWorldGreeting = new HelloWorldGreeting();
		helloWorldGreeting.perform();
		
		Greeting lambdaGreeting = () -> System.out.println("Hello World Lambda");
		lambdaGreeting.perform();
		
		//MyLambda lambdaGreeting = () -> System.out.println("Hello World Lambda");
		//greeter.greet(greeting);
		//MyAdd addFunction = (int a, int b) -> a + b;
		
		Greeting innerClassGreeting = new Greeting() {
			public void perform() {
				System.out.println("Hello anomynous inner class");
			}
		};
	}
}

interface MyLambda {
	void foo();
}
interface MyAdd {
	int add(int a, int b);
}
