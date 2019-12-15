import java.util.Scanner;
import java.lang.Math;
import java.util.*;
import java.io.*;

public class Main
{
	public static void main(String[] args) throws FileNotFoundException
	{
		double TimeInterval = 86400;
		double RadialMagnitude2 = 0;
		double RadialMagnitude3 = 0;
		// Used to set the length of all the arrays containing the initial
		// conditions for each of the bodies in the system. So just by changing
		// this you can increase the number of bodies.
		int ArrayIndex = 7;

		//Creates arrays representing the initial conditions of the bodies in the
		//system as well as the arrays of objects representing the particles and
		//fields themselves.
		double[] Mass = new double[ArrayIndex];
		String[] PlanetNames = new String[ArrayIndex];
		double[] VectorComponent = new double[6];

		PhysicsVector[] PlanetInitialPosition = new PhysicsVector[ArrayIndex];
		PhysicsVector[] PlanetInitialVelocity = new PhysicsVector[ArrayIndex];

		Particle[] Planets = new Particle[ArrayIndex];
		GravField[] Fields = new GravField[ArrayIndex];

		//Reads the data from a text file into various arrays representing the
		//initial conditions and bodies of the simulation.
		FileIO File = new FileIO();
		File.readFromFile(Mass, PlanetNames, PlanetInitialPosition, PlanetInitialVelocity, VectorComponent);

		File OutputFile = new File("GravSimOutput.txt");
		PrintWriter filewriter = new PrintWriter(OutputFile);

		for (int i = 0; i < Mass.length; i++)
		{
			//Creates new particles according to how many are in the text file and
			//gives them the initial conditions.
			Planets[i] = new Particle(TimeInterval, Mass[i], PlanetInitialPosition[i], PlanetInitialVelocity[i]);
		}

		//Creates new gravitational fields for each of the particles in the system
		//according to how many are in the text file and gives them their mass's.
		for (int i = 0; i < Mass.length; i++)
		{
			Fields[i] = new GravField(Mass[i]);
			filewriter.write(PlanetNames[i]+"X "+PlanetNames[i]+"Y "+PlanetNames[i]+"Z ");
		}
		filewriter.write(System.getProperty("line.separator"));

		//Selects which algorithm the user wants to use.
		Scanner Input = new Scanner(System.in);
		System.out.println("Press 1 for Euler Algorithm, Press 2 for Euler-Cramer Algorithm");
		double UserChoice = Input.nextDouble();

		//How many times the user wants to calculate the positions of the particles.
		System.out.println("Enter Number of Iterations");
		int IterationNumber = Input.nextInt();

		for(int k = 1; k <= IterationNumber; k++)
		{

			for (int i = 0; i < Mass.length; i++)
			{

				Planets[i].resetPotentialEnergySum();
				Planets[i].resetAccelerationSum();

				for (int j = 0; j < Mass.length; j++)
				{
          //i!=j so it doesn't calculate a radial vector from a body to itself..
					if (i != j)
					{
            //Where i is the body which is having its acceleration calculated
						//due the fields of the other j bodies.
						Planets[i].calculateAccelerationSum(Fields[j], Planets[i], Planets[j]);
						Planets[i].calculatePotentialEnergySum(Fields[j]);
					}
				}
			}

			//Moves the planets after calculating their accelerations all at once because
			//if they were moved during the acceleration calculation, the radial vectors
			//would change in turn affecting the positions.
			for (int a = 0; a < Mass.length; a++)
			{
        //Calculates position, momentum and energy conseravtion according to the
				//users choice of algorithm.
				if (UserChoice == 1)
				{
					Planets[a].euler();
					Planets[a].calculateEnergyConservation();
					Planets[a].getMomentum();
				}

				else if (UserChoice == 2)
				{
					Planets[a].eulerCramer();
					Planets[a].calculateEnergyConservation();
					Planets[a].getMomentum();
				}

				  if (Planets[4].getPosition().magnitude() < 0)
					{
						//RadialMagnitude3 = -Planets[4].getPosition().magnitude();
					}

					else
					{
						//RadialMagnitude3 = Planets[4].getPosition().magnitude();
					}

				  if (RadialMagnitude3 > RadialMagnitude2)
					{
						//RadialMagnitude2 = RadialMagnitude3;
					}
					File.writeToFile(PlanetNames[a], Planets[a], filewriter);
					//Writes data from simulation to file.
			}
			//System.out.println(RadialMagnitude2);
			filewriter.write(System.getProperty("line.separator"));
		}
		filewriter.close();

	}

}
