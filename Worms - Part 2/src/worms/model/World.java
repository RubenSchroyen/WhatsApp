package worms.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class World 
{
	private double worldHeight;
	private double worldWidth;
	private boolean[][] passableMap;
	public List<Worm> worms = new ArrayList<Worm>();
	public List<Food> fodder = new ArrayList<Food>();
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public List<Team> teams = new ArrayList<Team>();
	private int index = 0;
	private double cellWidth;
	private double cellHeight;

	
	public World(double worldHeight, double worldWidth, boolean[][] passableMap, Random random) 
	{
		worms = new ArrayList<Worm>();
		fodder = new ArrayList<Food>();
		projectiles = new ArrayList<Projectile>();
		teams = new ArrayList<Team>();
		this.worldHeight = worldHeight;
		this.worldWidth = worldWidth;
		this.setPassableMap(passableMap);
		this.cellWidth = (worldWidth / (passableMap[0].length + 1));
		this.cellHeight = (worldHeight / (passableMap.length + 1));
	}
	
	public Worm currentWorm()
	{
		Worm worm = worms.get(this.getIndex());
		if (worm.getCurrentAP() == 0)
			this.nextWorm();
		return worm;
	}
	
	public void nextWorm()
	{
		if (this.getIndex() == worms.size() - 1)
			this.setIndex(0);
		else
			this.setIndex(this.getIndex() + 1);
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public List<Worm> getWorms()
	{
		return worms;
	}
	
	public List<Food> getFodder()
	{
		return fodder;
	}
	
	public List<Projectile> getProjectiles()
	{
		return projectiles;
	}
	
	public List<Team> getTeams()
	{
		return teams;
	}
	
	public int amountOfWorms()
	{
		return worms.size();
	}
	
	public int amountOfTeams()
	{
		return teams.size();
	}
	
	public int amountOfProjectiles()
	{
		return projectiles.size();
	}
	
	public int amountOfFood()
	{
		return fodder.size();
	}
	
	public String getWinner()
	{
		if (isFinished())
			return teams.get(0).getName();
		return null;
	}

	public boolean isFinished()
	{
		if (teams.size() == 1)
			return true;
		return false;
	}
	
	public boolean wormExists(Worm worm)
	{
		return (worm != null);
	}

	public boolean wormInWorld(Worm worm)
	{
		return worms.contains(worm);
	}
	
	public void addWorm() throws IllegalArgumentException
	{
		Worm worm = new Worm(this, Math.random() * (this.getWorldWidth() + 1), Math.random() * (this.getWorldHeight() + 1), Math.random()*(0.75 + 0.25), Math.random()*2*Math.PI, "Eric");
		worm.setHP(worm.getMaxHP());
        worm.setCurrentAP(worm.getMaxAP());
		assert (wormExists(worm)) && (worm.getWorld() == this);
		assert (!wormInWorld(worm));
		worms.add(worm);
	}
	
	public void removeWorm(Worm worm) throws IllegalArgumentException
	{
		assert (wormExists(worm)) && (worm.getWorld() != null);
		assert (wormInWorld(worm));
		worms.remove(worm);
	}
	
	public boolean foodExists(Food food)
	{
		return (food != null);
	}

	public boolean foodInWorld(Food food)
	{
		return fodder.contains(food);
	}
	
	public void addFood() throws IllegalArgumentException
	{
		Food food = new Food(this, Math.random() * (this.getWorldWidth() + 1), Math.random() * (this.getWorldHeight() + 1));
		assert (foodExists(food)) && (food.getWorld() == null);
		assert (!foodInWorld(food));
		fodder.add(food);
	}
	
	public void removeFood(Food food) throws IllegalArgumentException
	{
		assert (foodExists(food)) && (food.getWorld() != null);
		assert (foodInWorld(food));
		fodder.remove(food);
	}
	
	public boolean projectileExists(Projectile projectile)
	{
		return (projectile != null);
	}

	public boolean projectileInWorld(Projectile projectile)
	{
		return projectiles.contains(projectile);
	}
	
	public void addProjectile(Projectile projectile) throws IllegalArgumentException
	{
		assert (projectileExists(projectile)) && (projectile.getWorld() == null);
		assert (!projectileInWorld(projectile));
		projectiles.add(projectile);
	}
	
	public void removeProjectile(Projectile projectile) throws IllegalArgumentException
	{
		assert (projectileExists(projectile)) && (projectile.getWorld() != null);
		assert (projectileInWorld(projectile));
		projectiles.remove(projectile);
	}
	
	public boolean teamExists(Team team)
	{
		return (team != null);
	}

	public boolean teamInWorld(Team team)
	{
		return teams.contains(team);
	}
	
	public void addTeam(Team team) throws IllegalArgumentException
	{
		assert (teamExists(team)) && (team.getWorld() == null);
		assert (!teamInWorld(team));
		assert (teams.size() < 10);
		teams.add(team);
	}
	
	public void removeTeam(Team team) throws IllegalArgumentException
	{
		assert (teamExists(team)) && (team.getWorld() != null);
		assert (teamInWorld(team));
		teams.remove(team);
	}
	

	
	public boolean wormInBounds(Worm worm)
	{
		if (worm.getPosX() < 0 || worm.getPosX() > this.getWorldWidth() || worm.getPosY() < 0 || worm.getPosY() > this.getWorldHeight())
			return false;
		else 
			return true;
	}
	
	public boolean foodInBounds(Food food)
	{
		if (food.getPosX() < 0 || food.getPosX() > this.getWorldWidth() || food.getPosY() < 0 || food.getPosY() > this.getWorldHeight())
			return false;
		else 
			return true;
	}
	
	public boolean projectileInBounds(Projectile projectile)
	{
		if (projectile.getPosX() < 0 || projectile.getPosX() > this.getWorldWidth() || projectile.getPosY() < 0 || projectile.getPosY() > this.getWorldHeight())
			return false;
		else 
			return true;
	}
	
	public double getWorldHeight() 
	{
		return worldHeight;
	}
	
	public void setWorldHeight(double worldHeight) 
	{
		if (isValidDimensions(this.getWorldWidth(), worldHeight))
			this.worldHeight = worldHeight;
	}
	
	public double getWorldWidth() 
	{
		return worldWidth;
	}
	
	public void setWorldWidth(double worldWidth) 
	{
		if (isValidDimensions(worldWidth, this.getWorldHeight()))
			this.worldWidth = worldWidth;
	}

	public boolean[][] getPassableMap() 
	{
		return passableMap;
	}

	private void setPassableMap(boolean[][] map) throws IllegalArgumentException{
		if (map.length == 0)
			throw new IllegalArgumentException("Empty map!");
		else if (map[0].length == 0)
			throw new IllegalArgumentException("Empty map!");
		this.passableMap = map;
	}
	
	public double getCellWidth() 
	{
		return this.cellWidth;
	}

	public double getCellHeight() 
	{
		return this.cellHeight;
	}
	
	public boolean isValidDimensions(double width, double height) throws IllegalArgumentException
	{
		if (width < 0 || width > Double.MAX_VALUE )
			throw new IllegalArgumentException("Not a valid dimension for World");
		if (height < 0 || height > Double.MAX_VALUE)
			throw new IllegalArgumentException("Not a valid dimension for World");
		return true;
	}

	public boolean isPassable(double randomizedX, double randomizedY, double testRadius) 
	{
		boolean passable = false;
		for (double percentage = 0; percentage < 1.01; percentage += 0.01)
		{
			if (this.getPassableMap()[(int) (randomizedX + percentage*testRadius)][(int) (randomizedY + percentage*testRadius)])
				passable = true;
			if (this.getPassableMap()[(int) (randomizedX + percentage*testRadius)][(int) (randomizedY - percentage*testRadius)])
				passable = true;
			if (this.getPassableMap()[(int) (randomizedX - percentage*testRadius)][(int) (randomizedY + percentage*testRadius)])
				passable = true;
			if (this.getPassableMap()[(int) (randomizedX - percentage*testRadius)][(int) (randomizedY - percentage*testRadius)])
				passable = true;
		}
		return passable;
	}

	public boolean isAdjacent(double randomizedX, double randomizedY, double testRadius) 
	{
		boolean adjacent = false;
		for (double percentage = 0; percentage < 1.01; percentage += 0.01)
		{
			if (this.getPassableMap()[(int) (randomizedX + percentage*testRadius)][(int) (randomizedY + percentage*testRadius)]
				&& (!this.getPassableMap()[(int) (randomizedX + percentage*1.1*testRadius)][(int) (randomizedY + percentage*1.1*testRadius)]))
				adjacent = true;
			if (this.getPassableMap()[(int) (randomizedX + percentage*testRadius)][(int) (randomizedY - percentage*testRadius)]
				&& (!this.getPassableMap()[(int) (randomizedX + percentage*1.1*testRadius)][(int) (randomizedY - percentage*1.1*testRadius)]))
				adjacent = true;
			if (this.getPassableMap()[(int) (randomizedX - percentage*testRadius)][(int) (randomizedY + percentage*testRadius)]
					&& (!this.getPassableMap()[(int) (randomizedX - percentage*1.1*testRadius)][(int) (randomizedY + percentage*1.1*testRadius)]))
				adjacent = true;
			if (this.getPassableMap()[(int) (randomizedX - percentage*testRadius)][(int) (randomizedY - percentage*testRadius)]
					&& (!this.getPassableMap()[(int) (randomizedX - percentage*1.1*testRadius)][(int) (randomizedY - percentage*1.1*testRadius)]))
				adjacent = true;
		}
		return adjacent;
	}

}
