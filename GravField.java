/**
* A 3-dimensional gravitational field class which is used to represent the
* gravitational field of a particle in order to calculate the effects of it's
* field on other particles in the system.
*
* @author Tehmoor Hussain
* @version 2.5
**/
import java.lang.Math;

public class GravField
{
	private static final double GravConstant = 6.67408e-11;
	private double PlanetMass = 0;
	private double RadialMagnitude = 0;
	private double PotentialEnergy = 0;

	private PhysicsVector RadialVector = new PhysicsVector();
	private PhysicsVector RadialUnit = new PhysicsVector();
	private PhysicsVector Acceleration = new PhysicsVector();

	/**
	* This constructor allows the class to access the mass of the planet
	* it will be calculating the field of from the main class.
	* @param InputMass Mass of a planet used to calculate the gravitational
	* acceleration produced by it.
	*/
	public GravField(double InputMass)
	{
	  PlanetMass = InputMass;
	}

	/**
	* This method is used to calculate the acceleration due to the field of
	* one body on another, by calculating the radial vector from one body to
	* another then using Newton's law of gravitation;
	* a = GMr/|r|^2
	* where G is the gravitational constant, M is the mass of the body, r is radial
	* vector from BodyA to BodyB, a is acceleration and |r| is the magnitude of
	* the radial vector.
	* @param BodyA A body which is used to calculate the radial vector to another
	* body.
	* @param BodyB A body which is used to calculate the radial vector to another
	* body.
	*/
	public PhysicsVector calculateAcceleration(Particle BodyA, Particle BodyB)
	{
		RadialVector.setVector(BodyB.getPosition());
		RadialVector.decreaseBy(BodyA.getPosition());
		RadialUnit.setVector(RadialVector.getUnitVector());

		//RadialMagnitude is the magnitude of the radial vector from one planet
		//to another.
		RadialMagnitude = PhysicsVector.dot(RadialVector, RadialVector);
	  Acceleration.setVector(PhysicsVector.scale(GravConstant*PlanetMass/RadialMagnitude, RadialUnit));
		return Acceleration;
	}

	/**
	* This method is used to get the potential energy for one body due to another's
	* gravitational field. It uses the following equation;
  * U = GMm/|r|
	* Where U is the gravitational potential energy of a particle due to another,
	* |r| is the magnitude of the distance between them, G is the gravitational
	* constant, M is the mass of the planet producing the field and m is the mass
	* of the planet placed in the field.
	* @param MassA A mass which is placed in a gravitational field and its
	* potential energy obtained.
	*/
	public double getPotentialEnergy(double MassA)
	{
		PotentialEnergy = GravConstant*PlanetMass*MassA/Math.pow(RadialMagnitude, 0.5);

		return PotentialEnergy;
	}

}
