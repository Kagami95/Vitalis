package com.teamvitalis.vitalis.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ObjModel {

	private static final HashMap<String, ObjModel> MODELS = new HashMap<>();

	private List<Vector> vectors;

	private String name;

	private File file;

	public ObjModel(String name, File file) {
		if (!file.exists())
			throw new IllegalArgumentException(
					"The file " + file.getName() + " for the obj model '" + name + "' does not exist!");
		if (!file.getName().endsWith(".obj"))
			throw new IllegalArgumentException(
					"The file " + file.getName() + " for the obj model '" + name + "' is not an .obj file!");
		vectors = new ArrayList<>();
		BufferedReader br = null;
		FileReader fr = null;
		try {
			br = new BufferedReader(fr = new FileReader(file));
			boolean vFound = false;
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("v ")) {
					if (vFound)
						break;
					else
						continue;
				}
				line = line.substring(2);
				String[] split = line.split(" ");
				double x = Double.parseDouble(split[0]);
				double y = Double.parseDouble(split[1]);
				double z = Double.parseDouble(split[2]);
				Vector vector = new Vector(x, y, z);
				vectors.add(vector);
			}
			if (!vFound)
				throw new IllegalArgumentException(
						"the file " + file.getName() + " for the obj model '" + name + "' is not a valid .obj file!");

		} catch (IOException exc) {
			exc.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException exc2) {
					exc2.printStackTrace();
				}
			if (fr != null)
				try {
					fr.close();
				} catch (IOException exc2) {
					exc2.printStackTrace();
				}
		}
		this.name = name;
		this.file = file;
		MODELS.put(name, this);
	}

	/**
	 * A method to get the model's vectors.
	 * 
	 * @return A list containing the model's vectors.
	 */
	public List<Vector> getVectors() {
		return vectors;
	}

	/**
	 * Resizes the model.
	 * 
	 * @param size
	 *            The new size.
	 */
	public void resize(double size) {
		for (Vector vec : vectors) {
			vec.normalize();
			vec.setX(vec.getX() * size);
			vec.setY(vec.getY() * size);
			vec.setZ(vec.getZ() * size);
		}
	}

	/**
	 * Rotates the model around all axises with specified angles.
	 * 
	 * @param angleX
	 *            The angle to rotate around the X axis in degrees.
	 * @param angleY
	 *            The angle to rotate around the Y axis in degrees.
	 * @param angleZ
	 *            The angle to rotate around the Z axis in degrees.
	 */
	public void rotate(double angleX, double angleY, double angleZ) {
		for (Vector vec : vectors) {
			if (angleX != 0.0)
				VectorUtils.rotateAroundX(vec, angleX);
			if (angleY != 0.0)
				VectorUtils.rotateAroundY(vec, angleY);
			if (angleZ != 0.0)
				VectorUtils.rotateAroundZ(vec, angleZ);
		}
	}

	/**
	 * Displays the model with a specified ParticleEffect.
	 * 
	 * @param effect
	 *            The ParticleEffect used to display the model.
	 * @param loc
	 *            The base location of where to display the model.
	 */
	public void display(ParticleEffect effect, Location loc) {
		for (Vector vector : vectors) {
			loc.add(vector.getX(), vector.getY(), vector.getZ());
			effect.display(0F, 0F, 0F, 0F, 1, loc, 200D);
			loc.subtract(vector.getX(), vector.getY(), vector.getZ());
		}
	}

	/**
	 * A method to get the name of the model.
	 * 
	 * @return The model's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * A method to get the model's .obj file.
	 * 
	 * @return The model's .obj file.
	 */
	public File getFile() {
		return file;
	}
	
	public static ObjModel getModel(String name) {
		return MODELS.get(name);
	}
	
}