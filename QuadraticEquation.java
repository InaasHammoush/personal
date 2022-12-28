/**
 * This program takes three numbers as user input and prints out the
 * answer. The program keeps running until the user enters "n" when
 * asked if they want to continue.
 * @author Inaas
 *
 */
public class QuadraticEquation {
	/**
	 * Returns the larger of the two roots of the quadratic equation
	 * A*x*x + B*x + C = 0, provided it has any roots.  If A == 0 or
	 * if the discriminant, B*B - 4*A*C, is negative, then an exception
	 * of type IllegalArgumentException is thrown.
	 */
	static public double root( double A, double B, double C )
			throws IllegalArgumentException {
		if (A == 0) {
			throw new IllegalArgumentException("A can't be zero.");
		}
		else {
			double disc = B*B - 4*A*C;
			if (disc < 0)
				throw new IllegalArgumentException("Discriminant < zero.");
			return  (-B + Math.sqrt(disc)) / (2*A);
		}
	}

	public static void main (String[] args) {
		System.out.println("Hello!");
		System.out.println("to get the root for your equation Ax^2 + Bx + C = 0"
				+ " please enter the constant values of A, b and C.");
		System.out.println();
		while (true) {
			System.out.println("What is the value of A?");
			double a = TextIO.getDouble();
			System.out.println("What is the value of B?");
			double b = TextIO.getDouble();
			System.out.println("What is the value of C?");
			double c = TextIO.getDouble();

			try {
				double answer = root(a,b,c);
				System.out.println("The solution for your equation is: "
						+ answer);
			}
			catch (IllegalArgumentException e){
				System.out.print("The following error occured: ");
				System.out.println(e.getMessage());
			}
			System.out.println("Do you want to get the solution for "
					+ "another equation? y/n");
			boolean reply = TextIO.getBoolean();

			if (!reply) {
				System.out.println("See you later!");
				break;
			}

		}

	}

}
