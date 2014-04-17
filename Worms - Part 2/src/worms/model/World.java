package worms.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//TODO: Adjacent 
//TODO: bug where worms won't spawn (because of 'not being in world')

public class World 
{
	private double worldHeight;
	private double worldWidth;
	private boolean[][] passableMap;
	public List<Worm> worms = new ArrayList<Worm>();
	public List<Food> fodder = new ArrayList<Food>();
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public List<Team> teams = new ArrayList<Team>();
	Projectile projectile;
	Worm worm;
	Food food;
	private int index;
	private double cellWidth;
	private double cellHeight;
	
	public World(double worldHeight, double worldWidth, boolean[][] passableMap, Random random) 
	{
		worms = new ArrayList<Worm>();
		fodder = new ArrayList<Food>();
		projectiles = new ArrayList<Projectile>();
		teams = new ArrayList<Team>();
		this.projectiles = null;
		this.worldHeight = worldHeight;
		this.worldWidth = worldWidth;
		this.setPassableMap(passableMap);
		this.cellWidth = (worldWidth / (passableMap[0].length + 1));
		this.cellHeight = (worldHeight / (passableMap.length + 1));
	}
	
	public Worm currentWorm()
	{
		return worms.get(this.getIndex());
	}
	
	public void nextWorm()
	{
		this.setIndex(this.getIndex() + 1);
		this.worm = currentWorm();
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
	
	public void addWorm(Worm worm) throws IllegalArgumentException
	{
		assert (wormExists(worm)) && (worm.getWorld() == this);
		assert !wormInWorld(worm);
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
	
	public void addFood(Food food) throws IllegalArgumentException
	{
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
	
	public boolean isPassable(double x, double y, double radius) 
	{
		boolean impassable = true;
		if (getPassableMap(x + radius, y + radius) != null)
			impassable = false;;
		if (getPassableMap(x + radius, y - radius) != null)
			impassable = false;
		if (getPassableMap(x - radius, y + radius) != null)
			impassable =  false;
		if (getPassableMap(x - radius, y - radius) != null)
			impassable = false;
		return impassable;
	}


	public boolean isAdjacent(double x, double y, double radius) 
	{
		double newRadius = radius * 0.1;
		return ( !isPassable(x, y, 0) && isPassable(x, y, newRadius) );
	}

	public boolean getNextCell(double x, double y) 
	{
		return passableMap[passableMap.length - (int) y][(int) x];
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
		this.worldHeight = worldHeight;
	}
	
	public double getWorldWidth() 
	{
		return worldWidth;
	}
	
	public void setWorldWidth(double worldWidth) 
	{
		this.worldWidth = worldWidth;
	}

	public boolean[][] getPassableMap(double x, double y) 
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

}
