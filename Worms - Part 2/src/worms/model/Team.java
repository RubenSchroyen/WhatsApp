package worms.model;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;
import worms.model.Worm;

public class Team 
{
	
	private String name;
	private World world;
	private ArrayList<Worm> teamMembers;

	public Team(String name, World world) 
	{
		if (!isValidName(name))
			throw new IllegalArgumentException("Name not valid");
		this.name = name;
		this.world = world;
		this.teamMembers = new ArrayList<Worm>();
	}

	@Basic
	public String getName() 
	{
		return this.name;
	}

	@Basic
	public World getWorld() 
	{
		return this.world;
	}

	
	@Basic
	public Worm getWorm(int index) throws IndexOutOfBoundsException 
	{
		if (index < 1 || index > getAmountOfWorms())
			throw new IndexOutOfBoundsException();
		return teamMembers.get(index);
	}

	
	@Basic
	public int getAmountOfWorms() {
		return teamMembers.size();
	}

	public boolean wormExists(Worm worm) {
		return (worm != null);
	}

	public boolean wormExistsAtIndex(Worm worm, int index) 
	{
		if ((index < 1) || (index > getAmountOfWorms() + 1))
			return false;
		if (!wormExists(worm))
			return false;
		for (int pos = 1; pos <= getAmountOfWorms(); pos++)
			if ((pos != index) && (getWorm(pos) == worm))
				return false;
		return true;
	}

	public boolean isValidMembers() 
	{
		for (int index = 1; index <= getAmountOfWorms(); index++) 
		{
			if (!wormExistsAtIndex(getWorm(index), index))
				return false;
			if (getWorm(index).getTeam() != this)
				return false;
		}
		return true;
	}

	public boolean isMember(Worm worm) 
	{
		return teamMembers.contains(worm);
	}

	public int getIndexOfWorm(Worm worm) 
	{
		return teamMembers.indexOf(worm);
	}

	public ArrayList<Worm> getAllWorms() 
	{
		return new ArrayList<Worm>(teamMembers);
	}

	
	public void addWorm(Worm worm) 
	{
		assert (worm != null) && (worm.getTeam() == this);
		assert !isMember(worm);
		teamMembers.add(worm);
	}

	
	public void removeAsWorm(Worm worm) 
	{
		assert (worm != null) && (worm.getTeam() == null);
		assert (isMember(worm));
		teamMembers.remove(worm);
	}

	private boolean isValidName(String name) 
	{
		return name.matches("[A-Z][a-zA-Z0-9\\s'\"]+");
	}
	
	public boolean isActive()
	{
		if (teamMembers.size() != 0)
			return true;
		return false;
	}
	
	public void destroy()
	{
		if (!isActive())
		{
			world.removeTeam(this);
			this.world = null;
		}
	}
}