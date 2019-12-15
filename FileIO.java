/**
* A class used to read the initial conditions for a gravitational n body
* simulation to arrays and also to write the end results of the
* simulation to file.
*
* @author Tehmoor Hussain
* @version 2.5
**/
import java.lang.Math;
import java.util.*;
import java.io.*;

public class FileIO
{
	/**
	* This method is used read the contents of a file which contain the initial
	* conditions for all the bodies in a system to multiple arrays, so they can
	* be used in the simulation.
	* @param MassArray Array of masses of all bodies in system.
	* @param NameArray Array of names of all bodies in system.
	* @param GenericPositions The starting positions of all bodies in the system.
	* @param GenericVelocities The starting velocities of all bodies in the system.
	* @param GenericComponents The components of the velocity and position vectors.
	*/
	public void readFromFile(double[] MassArray, String[] NameArray, PhysicsVector[] GenericPositions, PhysicsVector[] GenericVelocities, double[] GenericComponents) throws FileNotFoundException
	{

		File PlanetData = new File("PlanetData.txt");
		Scanner scanner = new Scanner(PlanetData);

		int index = 0;
		//loops while there still is data left in the text file PlanetData.
		while ((scanner.hasNext()))
		{
			if (index <= MassArray.length)
			{
				NameArray[index] = scanner.next();
				MassArray[index] = scanner.nextDouble();

        //Both of the initial position vector and velocity vector has three
				//components which is read through assigning the elements of an array
				//to read these components.
				for (int i = 0; i < GenericComponents.length; i++)
				{
					GenericComponents[i] = scanner.nextDouble();
				}

				GenericPositions[index] = new PhysicsVector(GenericComponents[0], GenericComponents[1], GenericComponents[2]);
				GenericVelocities[index] = new PhysicsVector(GenericComponents[3], GenericComponents[4], GenericComponents[5]);
				index += 1;
			}
		}
	}

	/**
	* This method is used to write the physical quantites calculated in the main
	* class to a file.
	* @param GenericNames Names of bodies in the system which are to be printed
	* to file.
	* @param GenericBody Generic body in the system which has data to be written
	* to file.
	* @param filewriter file writing tool which is required to write to file.
	*/
	public void writeToFile(String GenericNames, Particle GenericBody, PrintWriter filewriter) throws FileNotFoundException
	{
		filewriter.write(GenericBody.getPosition().getX()+" "+GenericBody.getPosition().getY()+" "+GenericBody.getPosition().getZ()+" ");
  }
}
